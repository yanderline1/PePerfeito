package br.com.peperfeito.PePerfeito.util;

import io.jsonwebtoken.Claims; 
import io.jsonwebtoken.Jwts; 
import io.jsonwebtoken.SignatureAlgorithm; 
import io.jsonwebtoken.io.Decoders; 
import io.jsonwebtoken.security.Keys; 

import org.springframework.beans.factory.annotation.Value; 
import org.springframework.security.core.userdetails.UserDetails; 
import org.springframework.stereotype.Component; 

import java.security.Key; 
import java.util.Date; 
import java.util.HashMap; 
import java.util.Map; 
import java.util.function.Function; 

@Component 
public class JwtUtil {

    // @Value injeta o valor da propriedade 'jwt.secret' do application.properties
    // Esta é a chave secreta que será usada para assinar e verificar os tokens.
    // Mantenha-a segura e complexa!
    @Value("${jwt.secret}")
    private String SECRET_KEY;

    // Tempo de validade do token em milissegundos (ex: 10 horas)
    @Value("${jwt.expiration}")
    private long EXPIRATION_TIME; // Definiremos isso no application.properties

    // --- Métodos para Extrair Informações do Token ---

    // Extrai o nome de usuário (subject) do token JWT
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extrai a data de expiração do token JWT
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Método genérico para extrair uma claim específica do token
    // <T> é um tipo genérico, Function<Claims, T> é uma função que recebe Claims e retorna T
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token); // Primeiro, extrai todas as claims
        return claimsResolver.apply(claims); // Depois, aplica a função para obter a claim desejada
    }

    // Extrai todas as claims (corpo) do token JWT
    private Claims extractAllClaims(String token) {
        // Jwts.parserBuilder() cria um parser para o JWT
        // .setSigningKey(getSigningKey()) define a chave usada para verificar a assinatura do token
        // .build() constrói o parser
        // .parseClaimsJws(token) parseia o token e verifica a assinatura
        // .getBody() obtém o corpo do token, que contém as claims
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
    }

    // Verifica se o token expirou
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date()); // Retorna true se a data de expiração for anterior à data atual
    }

    // --- Métodos para Gerar e Validar o Token ---

    // Gera um token JWT para um UserDetails (usuário autenticado)
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>(); // Você pode adicionar informações extras aqui se quiser
        // Por exemplo: claims.put("roles", userDetails.getAuthorities());
        return createToken(claims, userDetails.getUsername()); // Chama o método para criar o token
    }

    // Cria o token JWT com claims, subject (nome de usuário), data de emissão e expiração
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims) // Adiciona as claims
                .setSubject(subject) // Define o assunto (geralmente o nome de usuário)
                .setIssuedAt(new Date(System.currentTimeMillis())) // Define a data de emissão
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Define a data de expiração
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // Assina o token com a chave secreta e algoritmo HS256
                .compact(); // Compacta o token em uma string URL-safe
    }

    // Valida um token JWT
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token); // Extrai o nome de usuário do token
        // Verifica se o nome de usuário do token é o mesmo do UserDetails e se o token não expirou
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // Obtém a chave de assinatura decodificada
    private Key getSigningKey() {
        // Decoders.BASE64.decode(SECRET_KEY) decodifica a chave secreta que está em Base64
        // Keys.hmacShaKeyFor(keyBytes) cria uma chave segura para assinatura HMAC-SHA
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}