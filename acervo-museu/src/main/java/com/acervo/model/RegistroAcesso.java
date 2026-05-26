package com.acervo.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "registros_acesso")
public class RegistroAcesso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // IP do visitante — gravado mesmo sem login
    private String ip;

    private String pagina;

    private LocalDateTime dataHora = LocalDateTime.now();

    // Null se não estiver logado
    private String emailUsuario;
}
