package br.com.peperfeito.PePerfeito.config; // Ou outro pacote de sua escolha

import br.com.peperfeito.PePerfeito.security.CustomUserDetailsService; // Importar o serviço que você criou

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/", "/login").permitAll()
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/usuarios").hasRole("MESTRE")
                .requestMatchers(HttpMethod.PUT, "/usuarios/**").hasRole("MESTRE")
                .requestMatchers(HttpMethod.DELETE, "/usuarios/**").hasRole("MESTRE")
                .anyRequest().authenticated() // Permite todas as requisições HTTP
            )
            .httpBasic(Customizer.withDefaults()) // Configura autenticação básica
            .formLogin(Customizer.withDefaults())
            .cors(Customizer.withDefaults()) // Habilita CORS
            .csrf(csrf -> csrf.disable()); // Desabilita CSRF, que é comum para APIs REST stateless
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