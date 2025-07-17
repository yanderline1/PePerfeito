package br.com.peperfeito.PePerfeito.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "id",unique = true, nullable = false)
    private Long id;
    private String nome;
    private LocalDate dataNascimento;
    private String email;
    private String phone;
    private String address;

    @Column(name = "cpf", unique = true, nullable = false)
    private String CPF;
    
}
