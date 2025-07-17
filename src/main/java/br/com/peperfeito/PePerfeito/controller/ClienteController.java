package br.com.peperfeito.PePerfeito.controller;

import br.com.peperfeito.PePerfeito.model.Cliente;
import br.com.peperfeito.PePerfeito.service.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes") // Define o path base para este controller
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    // Endpoint para criar um novo cliente
    @PostMapping
    public ResponseEntity<Cliente> criarCliente(@RequestBody Cliente cliente) {
        Cliente novoCliente = clienteService.saveCliente(cliente);
        return new ResponseEntity<>(novoCliente, HttpStatus.CREATED); // Retorna 201 Created
    }

    // Endpoint para buscar um cliente por ID
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarClientePorId(@PathVariable Long id) {
        Cliente cliente = clienteService.findClienteById(id);
        return new ResponseEntity<>(cliente, HttpStatus.OK); // Retorna 200 OK
    }

    // Endpoint para listar todos os clientes
    @GetMapping
    public ResponseEntity<List<Cliente>> listarTodosClientes() {
        List<Cliente> clientes = clienteService.findAllClientes();
        return new ResponseEntity<>(clientes, HttpStatus.OK); // Retorna 200 OK
    }

    // Endpoint para atualizar um cliente
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualizarCliente(@PathVariable Long id, @RequestBody Cliente clienteAtualizado) {
        Cliente cliente = clienteService.updateCliente(id, clienteAtualizado);
        return new ResponseEntity<>(cliente, HttpStatus.OK); // Retorna 200 OK
    }

    // Endpoint para deletar um cliente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCliente(@PathVariable Long id) {
        clienteService.deleteCliente(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Retorna 204 No Content
    }

}