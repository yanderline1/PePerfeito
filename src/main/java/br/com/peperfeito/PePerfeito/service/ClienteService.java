package br.com.peperfeito.PePerfeito.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.peperfeito.PePerfeito.model.Cliente;
import br.com.peperfeito.PePerfeito.repository.ClienteRepository;

@Service
public class ClienteService {
     private final ClienteRepository clienteRepository;

     public ClienteService(ClienteRepository clienteRepository) {
         this.clienteRepository = clienteRepository;
     }

     public Cliente findClienteById(Long id) {
         return clienteRepository.findById(id)
                 .orElseThrow(() -> new RuntimeException("Cliente não encontrado com o ID: " + id));
     }

     public List<Cliente> findAllClientes() {
        return clienteRepository.findAll();
    }

     public Cliente saveCliente(Cliente cliente) {
         return clienteRepository.save(cliente);
     }

    public void deleteCliente(Long id) {
        clienteRepository.deleteById(id);
    }
    
    public Cliente updateCliente(Long id, Cliente clienteAtualizado) {
        return clienteRepository.findById(id)
                .map(cliente -> {
                    cliente.setNome(clienteAtualizado.getNome());
                    cliente.setEmail(clienteAtualizado.getEmail());
                    // Adicione outros campos que você deseja atualizar
                    return clienteRepository.save(cliente);
                })
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com o ID: " + id));
    }

}
