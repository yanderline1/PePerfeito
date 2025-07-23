package br.com.peperfeito.PePerfeito.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    // Este método é chamado quando um usuário não autenticado tenta acessar um recurso protegido
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        // Define o status HTTP para 401 Unauthorized
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        // Define o tipo de conteúdo da resposta como JSON
        response.setContentType("application/json");
        // Escreve uma mensagem de erro JSON no corpo da resposta
        response.getWriter().write("{\"status\": 401, \"message\": \"Você não está autorizado a acessar este recurso. Por favor, faça login.\"}");
        response.getWriter().flush(); // Envia a resposta imediatamente
    }
}