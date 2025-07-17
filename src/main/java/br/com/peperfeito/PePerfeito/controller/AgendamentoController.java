package br.com.peperfeito.PePerfeito.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.peperfeito.PePerfeito.model.Agendamento;
import br.com.peperfeito.PePerfeito.service.AgendamentoService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/agendamentos")
public class AgendamentoController {

    private final AgendamentoService agendamentoService;

    public AgendamentoController(AgendamentoService agendamentoService) {
        this.agendamentoService = agendamentoService;
    }

    @PostMapping
    public ResponseEntity<Agendamento> criarAgendamento(@RequestBody Agendamento agendamento) {
        Agendamento novoAgendamento = agendamentoService.saveAgendamento(agendamento);
        return new ResponseEntity<>(novoAgendamento, HttpStatus.CREATED); // Retorna 201 Created
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Agendamento> buscarAgendamentoPorId(@PathVariable Long id) {
        Agendamento agendamento = agendamentoService.findAgendamentoById(id);
        return new ResponseEntity<>(agendamento, HttpStatus.OK); // Retorna 200 OK
    }

    @GetMapping
    public ResponseEntity<List<Agendamento>> listarTodosAgendamentos() {
        List<Agendamento> agendamentos = agendamentoService.findAllAgendamentos();
        return new ResponseEntity<>(agendamentos, HttpStatus.OK); // Retorna 200 OK
    }

    @PutMapping("/{id}")
    public ResponseEntity<Agendamento> atualizarAgendamento(@PathVariable Long id, @RequestBody Agendamento agendamentoAtualizado) {
        Agendamento agendamento = agendamentoService.updateAgendamento(id, agendamentoAtualizado);
        return new ResponseEntity<>(agendamento, HttpStatus.OK); // Retorna 200 OK
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAgendamento(@PathVariable Long id) {
        agendamentoService.deleteAgendamento(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Retorna 204 No Content
    }
    
}
