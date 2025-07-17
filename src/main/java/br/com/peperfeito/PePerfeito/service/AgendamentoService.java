package br.com.peperfeito.PePerfeito.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.peperfeito.PePerfeito.model.Agendamento;
import br.com.peperfeito.PePerfeito.repository.AgendamentoRepository;

@Service
public class AgendamentoService {
    
    private final AgendamentoRepository agendamentoRepository;

    public AgendamentoService(AgendamentoRepository agendamentoRepository) {
        this.agendamentoRepository = agendamentoRepository;
    }

    public Agendamento findAgendamentoById(Long id) {
        return agendamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado com o ID: " + id));
    }

    public List<Agendamento> findAllAgendamentos() {
        return agendamentoRepository.findAll();
    }

    public Agendamento saveAgendamento(Agendamento agendamento) {
        return agendamentoRepository.save(agendamento);
    }

    public void deleteAgendamento(Long id) {
        agendamentoRepository.deleteById(id);
    }

    public Agendamento updateAgendamento(Long id, Agendamento agendamentoAtualizado) {
        return agendamentoRepository.findById(id)
                .map(agendamento -> {
                    agendamento.setDataCriacao(agendamentoAtualizado.getDataCriacao());
                    agendamento.setInicioAtendimento(agendamentoAtualizado.getInicioAtendimento());
                    agendamento.setFimAtendimento(agendamentoAtualizado.getFimAtendimento());
                    agendamento.setStatusConsulta(agendamentoAtualizado.getStatusConsulta());
                    return agendamentoRepository.save(agendamento);
                })
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado com o ID: " + id));
    }

    
}
