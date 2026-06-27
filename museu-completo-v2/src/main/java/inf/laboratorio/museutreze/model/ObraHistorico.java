package inf.laboratorio.museutreze.model;

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

        @Column(nullable = false)
        private String operacao;

        @Column(name = "data")
        private LocalDateTime data = LocalDateTime.now();

        @ManyToOne
        @JoinColumn(name = "usuario_id", nullable = false)
        private Usuario usuario;

        @Column(name = "nome_obra", nullable = false)
        private String nomeObra;
    }
