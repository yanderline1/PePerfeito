package br.com.peperfeito.PePerfeito.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Agendamento {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate dataConsulta;

    private LocalTime inicioAtendimento;
    private LocalTime fimAtendimento;

    public enum StatusAgendamento {
        AGENDADA, REALIZADA, CANCELADA, CONFIRMADA, NAO_COMPARECEU
    }

    @Enumerated(EnumType.STRING)
    private StatusAgendamento statusConsulta;

    private String observacao;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @UpdateTimestamp
    private LocalDateTime dataHoraUltimaAtualizacao;

    @ManyToOne
    @JoinColumn(name = "id_profissional", nullable = false)
    private Usuario idProfissional;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente idCliente;

}
