package com.acervo.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "exemplares")
public class Exemplar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean disponibilidade = true;

    private Integer numero;

    // Cada exemplar pertence a uma obra
    @ManyToOne
    @JoinColumn(name = "obra_id", nullable = false)
    private Obra obra;
}
