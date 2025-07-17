package br.com.peperfeito.PePerfeito.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.peperfeito.PePerfeito.model.HorariosAtendimento;

@Repository
public interface HorariosAtendimentoRepository extends JpaRepository<HorariosAtendimento, Long> {

    // void save(br.com.peperfeito.PePerfeito.service.HorariosAtendimento horariosAtendimento);
    // Aqui você pode adicionar métodos personalizados de consulta, se necessário
    
}
