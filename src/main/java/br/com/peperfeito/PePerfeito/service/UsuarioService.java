package br.com.peperfeito.PePerfeito.service;

import br.com.peperfeito.PePerfeito.model.Usuario;
import br.com.peperfeito.PePerfeito.repository.UsuarioRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    // Injeção de dependência via construtor (recomendado)
    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Método para criar/salvar um novo usuário ou atualizar um existente
    @Transactional
    public Usuario salvarUsuario(Usuario usuario) {
        // Aqui você poderia adicionar lógicas de negócio antes de salvar:
        // Ex: Criptografar a senha (usando Spring Security PasswordEncoder)
        String senhaCriptografada = passwordEncoder.encode(usuario.getPassword());
        usuario.setPassword(senhaCriptografada);
        // Ex: Validar campos específicos
        return usuarioRepository.save(usuario);
    }

    // Método para buscar todos os usuários
    @Transactional(readOnly = true) // readOnly = true otimiza transações de leitura
    public List<Usuario> buscarTodosUsuarios() {
        return usuarioRepository.findAll();
    }

    // Método para buscar um usuário pelo ID
    @Transactional(readOnly = true)
    public Optional<Usuario> buscarUsuarioPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    // Método para buscar um usuário pelo email (exemplo de método personalizado)
    @Transactional(readOnly = true)
    public Optional<Usuario> buscarUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    // Método para deletar um usuário pelo ID
    @Transactional
    public void deletarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    // Método para atualizar um usuário existente
    @Transactional
    public Usuario atualizarUsuario(Long id, Usuario usuarioAtualizado) {
        // Verifica se o usuário existe antes de tentar atualizar
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setName(usuarioAtualizado.getName());
            usuario.setEmail(usuarioAtualizado.getEmail());
            usuario.setPassword(passwordEncoder.encode(usuarioAtualizado.getPassword()));
            usuario.setPhone(usuarioAtualizado.getPhone());
            usuario.setTipoUsuario(usuarioAtualizado.getTipoUsuario());
            return usuarioRepository.save(usuario);
        }).orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + id)); // Lançar uma exceção adequada
    }
}