package br.com.peperfeito.PePerfeito.controller;

import br.com.peperfeito.PePerfeito.model.Usuario;
import br.com.peperfeito.PePerfeito.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController 
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    // Injeção de dependência via construtor
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Endpoint para criar um novo usuário
    @PostMapping
    public ResponseEntity<Usuario> criarUsuario(@RequestBody Usuario usuario) {
        // Você pode adicionar validações de entrada aqui antes de chamar o serviço
        Usuario novoUsuario = usuarioService.salvarUsuario(usuario);
        return new ResponseEntity<>(novoUsuario, HttpStatus.CREATED); // Retorna 201 Created
    }

    // Endpoint para buscar todos os usuários
    @GetMapping
    public ResponseEntity<List<Usuario>> buscarTodosUsuarios() {
        List<Usuario> usuarios = usuarioService.buscarTodosUsuarios();
        return new ResponseEntity<>(usuarios, HttpStatus.OK); // Retorna 200 OK
    }

    // Endpoint para buscar um usuário pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarUsuarioPorId(@PathVariable Long id) {
        return usuarioService.buscarUsuarioPorId(id)
                .map(usuario -> new ResponseEntity<>(usuario, HttpStatus.OK)) // Retorna 200 OK se encontrado
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND)); // Retorna 404 Not Found se não encontrado
    }

    // Endpoint para atualizar um usuário existente
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuarioAtualizado) {
        try {
            Usuario usuarioEditado = usuarioService.atualizarUsuario(id, usuarioAtualizado);
            return new ResponseEntity<>(usuarioEditado, HttpStatus.OK); // Retorna 200 OK
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Retorna 404 Not Found se não encontrado
        }
    }

    // Endpoint para deletar um usuário
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        usuarioService.deletarUsuario(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Retorna 204 No Content
    }
}