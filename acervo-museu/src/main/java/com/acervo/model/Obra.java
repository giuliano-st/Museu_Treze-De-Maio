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

    // "LIVRO", "JORNAL" ou "REVISTA"
    @Column(nullable = false)
    private String tipo;

    @Column(nullable = false)
    private String titulo;

    private String autor;
    private String categoria;
    private String descricao;
    private String capa;

    // Publicação
    private String editora;
    private String localPublicacao;
    private String dataPublicacao;
    private String edicao;
    private String serie;
    private String colecao;
    private String assuntos;
    private String paginas;

    // Identificadores — ISBN não é obrigatório
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

    // Informações administrativas (visíveis só para admins)
    private String localizacaoFisica;
    private String notasInternas;

    // Controle de saída (empréstimo)
    private Integer quantidadeSaidas = 0;

    // Status do exemplar
    private String status = "DISPONIVEL";

    // Doador (novo campo)
    private String doador;

    // Contador de buscas — incrementado a cada busca que retorna essa obra
    private Integer contadorBuscas = 0;
}
