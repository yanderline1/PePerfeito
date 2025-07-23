package br.com.peperfeito.PePerfeito.config;

import br.com.peperfeito.PePerfeito.security.CustomUserDetailsService;
import br.com.peperfeito.PePerfeito.security.JwtAuthenticationEntryPoint; // NOVO
import br.com.peperfeito.PePerfeito.security.JwtAuthenticationFilter; // NOVO

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager; // NOVO
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration; // NOVO
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy; // NOVO: Para configurar o gerenciamento de sessão
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter; // NOVO: Para adicionar nosso filtro JWT antes
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter; // Injetar nosso filtro JWT
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint; // Injetar nosso ponto de entrada JWT

    // Construtor para injeção de dependências
    public SecurityConfig(CustomUserDetailsService customUserDetailsService,
                          JwtAuthenticationFilter jwtAuthenticationFilter,
                          JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    // NOVO: Bean para o AuthenticationManager, necessário para autenticar usuários
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.addFilterBefore(
            jwtAuthenticationFilter,
             UsernamePasswordAuthenticationFilter.class
        );

        http
            .cors(Customizer.withDefaults())
            .csrf(AbstractHttpConfigurer::disable)
            .exceptionHandling(exceptions -> exceptions
                .authenticationEntryPoint(jwtAuthenticationEntryPoint) // Define nosso ponto de entrada para 401 Unauthorized
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // MUITO IMPORTANTE: Diz ao Spring para não criar sessões HTTP
            )
            .authorizeHttpRequests(authorize -> authorize
                // O endpoint /login agora será um endpoint REST que retorna o JWT.
                // Ele deve ser publicamente acessível para que o usuário possa obter o token.
                .requestMatchers("/", "/login").permitAll()
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/usuarios").hasRole("MESTRE")
                .requestMatchers(HttpMethod.PUT, "/usuarios/**").hasRole("MESTRE")
                .requestMatchers(HttpMethod.DELETE, "/usuarios/**").hasRole("MESTRE")
                .anyRequest().authenticated() // Todas as outras requisições exigem autenticação (via JWT)
            );

        // Adiciona nosso filtro JWT antes do filtro padrão de autenticação de usuário e senha do Spring Security.
        // Isso garante que nosso filtro intercepte e processe o JWT primeiro.

        return http.build();
    }

    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:8081"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}