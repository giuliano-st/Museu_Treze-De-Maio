package com.acervo.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "obra_historico")
public class ObraHistorico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Operação realizada: "CADASTROU", "EDITOU", "EXCLUIU", "CONSULTOU"
    @Column(nullable = false)
    private String operacao;

    private LocalDateTime data = LocalDateTime.now();

    // Quem fez a operação
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    // Sobre qual obra
    @ManyToOne
    @JoinColumn(name = "obra_id", nullable = false)
    private Obra obra;
}
