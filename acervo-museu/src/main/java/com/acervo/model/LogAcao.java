package com.acervo.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "logs_acao")
public class LogAcao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String emailUsuario;

    private String acao;

    private String detalhes;

    private LocalDateTime dataHora = LocalDateTime.now();
}
