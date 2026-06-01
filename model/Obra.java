package com.acervo.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "obras")
public class Obra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // "LIVRO", "JORNAL", "REVISTA"
    @Column(nullable = false)
    private String obraTipo;

    @Column(nullable = false)
    private String tituloPrincipal;

    private String capa;
    private String local;
    private String data;
    private String descFisica;
    private String nome;
    private String numeroChamada;
    private String chamadaLocal;
    private String tituloUniforme;
    private String isbn;
    private String serie;
    private String edicao;
    private String colecao;
    private String notasGerais;
    private String issn;
    private Integer volume;
    private String periodicidade;

    // Autor — muitos para um (uma obra tem um autor principal)
    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;

    // Editora — muitos para um
    @ManyToOne
    @JoinColumn(name = "editora_id")
    private Editora editora;

    // Assuntos — muitos para muitos
    @ManyToMany
    @JoinTable(
        name = "obra_assunto",
        joinColumns = @JoinColumn(name = "obra_id"),
        inverseJoinColumns = @JoinColumn(name = "assunto_id")
    )
    private List<Assunto> assuntos;
}
