package br.com.peperfeito.PePerfeito.model;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails; // Importação correta

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;
    private String name;
    private enum TipoUsuario {
        ADMINISTRATIVO, PROFISSIONAL, MESTRE
    };

    
    @Column(unique = true, nullable = false)
    private String email;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoUsuario tipoUsuario;
    
    @Column(nullable = false)
    private String password;
    private String phone;
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Mapeia o TipoUsuario para um papel Spring Security (ROLE_...)
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + tipoUsuario.name()));
    }
    
    // O nome de usuário para autenticação (usaremos o email)
    @Override
    public String getUsername() {
        return this.email; 
    }

    // Retorna a senha
    @Override
    public String getPassword() {
        return this.password;
    }

    // Métodos de status da conta (retornar true por padrão)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}