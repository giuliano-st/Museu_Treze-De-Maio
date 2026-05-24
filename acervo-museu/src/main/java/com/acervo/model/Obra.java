package com.acervo.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "obras")
public class Obra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private String tipo;

    @Column(nullable = false)
    private String titulo;

    private String autor;
    private String categoria;
    private String descricao;
    private String capa;


    private String editora;
    private String localPublicacao;
    private String dataPublicacao;
    private String edicao;
    private String serie;
    private String colecao;
    private String assuntos;
    private String paginas;


    private String isbn;
    private String issn;
    private String chamada;
    private String chamadaLocal;
    private String exemplar;
    private String tituloOriginal;
    private String colaboradores;
    private String periodicidade;
    private String descricaoFisica;
    private String numeroEdicao;
    private String mes;


    private String localizacaoFisica;
    private String notasInternas;


    private Integer quantidadeSaidas = 0;


    private String status = "DISPONIVEL";


    private String doador;


    private Integer contadorBuscas = 0;
}
