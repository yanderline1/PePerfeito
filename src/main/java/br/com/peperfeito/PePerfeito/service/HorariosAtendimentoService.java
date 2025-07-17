package br.com.peperfeito.PePerfeito.service;

import br.com.peperfeito.PePerfeito.model.HorariosAtendimento; // Importa a entidade
import br.com.peperfeito.PePerfeito.repository.HorariosAtendimentoRepository; // Importa o repositório
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class HorariosAtendimentoService {

    private final HorariosAtendimentoRepository horariosAtendimentoRepository;

    // Injeção de dependência via construtor
    public HorariosAtendimentoService(HorariosAtendimentoRepository horariosAtendimentoRepository) {
        this.horariosAtendimentoRepository = horariosAtendimentoRepository;
    }

    // Método para salvar um novo horário de atendimento
    @Transactional
    public HorariosAtendimento saveHorarioAtendimento(HorariosAtendimento horariosAtendimento) {
        // Você pode adicionar lógicas de negócio aqui antes de salvar
        return horariosAtendimentoRepository.save(horariosAtendimento);
    }

    // Método para buscar todos os horários de atendimento
    @Transactional(readOnly = true)
    public List<HorariosAtendimento> buscarTodosHorariosAtendimento() {
        return horariosAtendimentoRepository.findAll();
    }

    // Método para buscar um horário de atendimento pelo ID
    @Transactional(readOnly = true)
    public Optional<HorariosAtendimento> buscarHorarioAtendimentoPorId(Long id) {
        return horariosAtendimentoRepository.findById(id);
    }

    // Método para deletar um horário de atendimento pelo ID
    @Transactional
    public void deletarHorarioAtendimento(Long id) {
        horariosAtendimentoRepository.deleteById(id);
    }

    // Método para atualizar um horário de atendimento existente
    @Transactional
    public HorariosAtendimento atualizarHorarioAtendimento(Long id, HorariosAtendimento horariosAtualizado) {
        return horariosAtendimentoRepository.findById(id).map(horario -> {
            horario.setDiaSemana(horariosAtualizado.getDiaSemana());
            horario.setHoraInicio(horariosAtualizado.getHoraInicio());
            horario.setHoraFim(horariosAtualizado.getHoraFim());
            horario.setIdProfissional(horariosAtualizado.getIdProfissional());
            return horariosAtendimentoRepository.save(horario);
        }).orElseThrow(() -> new RuntimeException("Horário de Atendimento não encontrado com ID: " + id));
    }
}