package com.acervo.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nomeUsuario;

    // "ADMIN", "BIBLIOTECARIO"
    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false, unique = true)
    private String email;
}
