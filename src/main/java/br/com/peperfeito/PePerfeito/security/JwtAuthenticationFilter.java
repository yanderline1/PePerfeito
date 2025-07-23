package br.com.peperfeito.PePerfeito.security;

import br.com.peperfeito.PePerfeito.util.JwtUtil; // Importa nossa classe utilitária de JWT
import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain; // Para encadear filtros
import jakarta.servlet.ServletException; // Para exceções de servlet
import jakarta.servlet.http.HttpServletRequest; // Requisição HTTP
import jakarta.servlet.http.HttpServletResponse; // Resposta HTTP

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken; // Token de autenticação do Spring Security
import org.springframework.security.core.context.SecurityContextHolder; // Contém o contexto de segurança atual
import org.springframework.security.core.userdetails.UserDetails; // Detalhes do usuário
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource; // Para detalhes da autenticação
import org.springframework.stereotype.Component; // Marca como componente Spring
import org.springframework.web.filter.OncePerRequestFilter; // Garante que o filtro seja executado apenas uma vez por requisição

import java.io.IOException; // Para exceções de I/O

@Component // Marca esta classe como um componente Spring
public class JwtAuthenticationFilter extends OncePerRequestFilter { // Garante que o filtro seja executado apenas uma vez por requisição HTTP

    private final JwtUtil jwtUtil; // Injeção da nossa classe utilitária JWT
    private final CustomUserDetailsService userDetailsService; // Injeção do nosso UserDetailsService

    // Construtor para injeção de dependências
    public JwtAuthenticationFilter(JwtUtil jwtUtil, CustomUserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Tenta obter o cabeçalho "Authorization" da requisição
        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        // 2. Verifica se o cabeçalho existe e começa com "Bearer "
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7); // Extrai o token JWT (remove "Bearer ")
            try {
                username = jwtUtil.extractUsername(jwt); // Tenta extrair o nome de usuário do token
            } catch (Exception e) {
                // Logar a exceção para depuração, mas não expor ao cliente
                System.err.println("Erro ao extrair username do JWT ou token inválido: " + e.getMessage());
                // Poderíamos definir um status 401 aqui se quiséssemos um tratamento mais específico para tokens inválidos
                // response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                // return; // Interrompe a cadeia de filtros
            }
        }

        // 3. Se o nome de usuário foi extraído e não há autenticação no contexto atual do Spring Security
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Carrega os detalhes do usuário usando o nome de usuário do token
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // 4. Valida o token com base nos detalhes do usuário carregados
            if (jwtUtil.validateToken(jwt, userDetails)) {
                // Se o token for válido, cria um objeto de autenticação do Spring Security
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                // Define detalhes da autenticação (endereço IP, sessão ID, etc.)
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // Define o objeto de autenticação no contexto de segurança do Spring
                // Isso significa que o usuário está autenticado para esta requisição
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        // 5. Continua a cadeia de filtros (passa a requisição para o próximo filtro ou para o controlador)
        filterChain.doFilter(request, response);
    }
}