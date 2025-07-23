package br.com.peperfeito.PePerfeito.controller;

import br.com.peperfeito.PePerfeito.security.CustomUserDetailsService;
import br.com.peperfeito.PePerfeito.util.JwtUtil;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager; // Para autenticar o usuário
import org.springframework.security.authentication.BadCredentialsException; // Para tratar credenciais inválidas
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken; // Token de autenticação
import org.springframework.security.core.userdetails.UserDetails; // Detalhes do usuário
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody; // Para receber o corpo da requisição JSON
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthController {

    private final AuthenticationManager authenticationManager; // Injeção do AuthenticationManager
    private final CustomUserDetailsService userDetailsService; // Injeção do nosso UserDetailsService
    private final JwtUtil jwtUtil; // Injeção da nossa classe utilitária JWT

    // Construtor para injeção de dependências
    public AuthController(AuthenticationManager authenticationManager,
                          CustomUserDetailsService userDetailsService,
                          JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @Data
    static class AuthenticationRequest {
        private String username; // Nome de usuário (email)
        private String password; // Senha
    }

    @Data
    static class AuthenticationResponse {
        private String jwt; // O token JWT gerado
        private String message; // Mensagem de sucesso
    }

    @PostMapping
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            // Tenta autenticar o usuário usando o AuthenticationManager
            // Isso acionará o CustomUserDetailsService e o PasswordEncoder
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            // Se as credenciais forem inválidas, retorna 401 Unauthorized
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"status\": 401, \"message\": \"Credenciais inválidas.\"}");
        } catch (Exception e) {
            // Para qualquer outro erro durante a autenticação, retorna 500 Internal Server Error
            // Em produção, evite expor e.getMessage() diretamente.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"status\": 500, \"message\": \"Erro interno do servidor durante a autenticação.\"}");
        }

        // Se a autenticação for bem-sucedida, carrega os detalhes do usuário
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        // Gera o token JWT
        final String jwt = jwtUtil.generateToken(userDetails);

        // Retorna o token JWT na resposta
        AuthenticationResponse response = new AuthenticationResponse();
        response.setJwt(jwt);
        response.setMessage("Login bem-sucedido!");

        return ResponseEntity.ok(response); // Retorna 200 OK com o JWT no corpo
    }
}