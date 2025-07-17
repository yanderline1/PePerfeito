package br.com.peperfeito.PePerfeito.security; // Ou .security

import br.com.peperfeito.PePerfeito.model.Usuario;
import br.com.peperfeito.PePerfeito.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service // Marca a classe como um serviço Spring
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     Este é o método principal que o Spring Security chama para carregar o usuário.
     * @param email O email do usuário (que estamos usando como nome de usuário).
     * @return UserDetails (nossa entidade Usuario, que agora implementa UserDetails).
     * @throws UsernameNotFoundException se o usuário não for encontrado.
    */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Usamos o repositório para buscar o usuário pelo email
        Usuario usuario = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o email: " + email));

        // Retornamos a entidade Usuario, pois ela já implementa UserDetails
        return usuario;
    }
}