package br.com.peperfeito.PePerfeito.config; // Ou outro pacote de sua escolha

import br.com.peperfeito.PePerfeito.security.CustomUserDetailsService; // Importar o serviço que você criou

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration // Marca a classe como uma fonte de definição de beans
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService); // Define o serviço de detalhes do usuário
        authProvider.setPasswordEncoder(passwordEncoder()); // Define o codificador de senha
        return authProvider;
    }

    @Bean // Marca o método como um produtor de um bean gerenciado pelo Spring
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(Customizer.withDefaults()) // Habilita CORS
            .csrf(AbstractHttpConfigurer::disable) // Desabilita CSRF, que é comum para APIs REST stateless
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/", "/login").permitAll()
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/usuarios").hasRole("MESTRE")
                .requestMatchers(HttpMethod.PUT, "/usuarios/**").hasRole("MESTRE")
                .requestMatchers(HttpMethod.DELETE, "/usuarios/**").hasRole("MESTRE")
                .anyRequest().authenticated()
            )
            .httpBasic(Customizer.withDefaults()) // Configura autenticação básica
            .formLogin(formLogin -> formLogin
                .successHandler((request, response, authentication) -> {
                    // Lógica para sucesso de login (para requisições REST)
                    response.setStatus(200);
                    response.setContentType("aplication/json");
                    response.getWriter().write("{\\\"status\\\": 200, \\\"message\\\": \\\"Login successful!\\\"}"); // Ou um JSON com informações do usuário/token
                    response.getWriter().flush();
                })
                .failureHandler((request, response, exception) -> {
                    // Lógica para falha de login (para requisições REST)
                    int statusCode = 401;
                    String errorMessage = "Login failed: " + exception.getMessage();
                    if(exception instanceof BadCredentialsException) {
                        errorMessage = "Credenciais inválidas.";
                    } else {
                        statusCode = 500;
                        errorMessage = "Erro interno do servidor: " + exception.getMessage();
                    }
                    response.setStatus(statusCode);
                    response.setContentType("application/json");
                    response.getWriter().write("{\\\"status\\\": " + statusCode + ", \\\"message\\\": \\\"" + errorMessage + "\\\"}");
                    response.getWriter().flush();
                })
            )
            .logout(logout -> logout .permitAll()); // Permite que todos acessem o logout
        return http.build();
    }

    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:8081"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true); // se usar cookies/sessões

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}