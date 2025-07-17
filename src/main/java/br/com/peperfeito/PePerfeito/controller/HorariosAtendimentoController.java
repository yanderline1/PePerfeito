package br.com.peperfeito.PePerfeito.controller;

import br.com.peperfeito.PePerfeito.model.HorariosAtendimento;
import br.com.peperfeito.PePerfeito.service.HorariosAtendimentoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/horarios-atendimento") // Define o path base para este controller
public class HorariosAtendimentoController {

    private final HorariosAtendimentoService horariosAtendimentoService;

    public HorariosAtendimentoController(HorariosAtendimentoService horariosAtendimentoService) {
        this.horariosAtendimentoService = horariosAtendimentoService;
    }

    // Endpoint para criar um novo horário de atendimento
    @PostMapping
    public ResponseEntity<HorariosAtendimento> criarHorarioAtendimento(@RequestBody HorariosAtendimento horariosAtendimento) {
        HorariosAtendimento novoHorario = horariosAtendimentoService.saveHorarioAtendimento(horariosAtendimento);
        return new ResponseEntity<>(novoHorario, HttpStatus.CREATED); // Retorna 201 Created
    }

    // Endpoint para buscar todos os horários de atendimento
    @GetMapping
    public ResponseEntity<List<HorariosAtendimento>> buscarTodosHorariosAtendimento() {
        List<HorariosAtendimento> horarios = horariosAtendimentoService.buscarTodosHorariosAtendimento();
        return new ResponseEntity<>(horarios, HttpStatus.OK); // Retorna 200 OK
    }

    // Endpoint para buscar um horário de atendimento pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<HorariosAtendimento> buscarHorarioAtendimentoPorId(@PathVariable Long id) {
        return horariosAtendimentoService.buscarHorarioAtendimentoPorId(id)
                .map(horario -> new ResponseEntity<>(horario, HttpStatus.OK)) // Retorna 200 OK se encontrado
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND)); // Retorna 404 Not Found se não encontrado
    }

    // Endpoint para atualizar um horário de atendimento existente
    @PutMapping("/{id}")
    public ResponseEntity<HorariosAtendimento> atualizarHorarioAtendimento(@PathVariable Long id, @RequestBody HorariosAtendimento horariosAtualizado) {
        try {
            HorariosAtendimento horarioEditado = horariosAtendimentoService.atualizarHorarioAtendimento(id, horariosAtualizado);
            return new ResponseEntity<>(horarioEditado, HttpStatus.OK); // Retorna 200 OK
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Retorna 404 Not Found se não encontrado
        }
    }

    // Endpoint para deletar um horário de atendimento
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarHorarioAtendimento(@PathVariable Long id) {
        horariosAtendimentoService.deletarHorarioAtendimento(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Retorna 204 No Content
    }
}