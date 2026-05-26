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

    // Quem fez a ação
    private String emailUsuario;

    // Ex: "CADASTROU_OBRA", "EDITOU_OBRA", "EXCLUIU_OBRA", "LOGIN", "SAIDA_OBRA"
    private String acao;

    // Detalhes livres, ex: "Obra: Título X (id=5)"
    private String detalhes;

    private LocalDateTime dataHora = LocalDateTime.now();
}
