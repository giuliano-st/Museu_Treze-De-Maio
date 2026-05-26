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


    private String ip;

    private String pagina;

    private LocalDateTime dataHora = LocalDateTime.now();


    private String emailUsuario;
}
