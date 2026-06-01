package com.acervo.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    private String nacionalidade;
}
