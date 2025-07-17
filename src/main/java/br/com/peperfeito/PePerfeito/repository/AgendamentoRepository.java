package br.com.peperfeito.PePerfeito.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.peperfeito.PePerfeito.model.Agendamento;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    // Aqui você pode adicionar métodos personalizados de consulta, se necessário

}