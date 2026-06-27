package inf.laboratorio.museutreze.config;

import lombok.Data;

import java.util.List;

@Data
public class ObraJsonDTO {

    private String tipo;

    private String titulo;

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

    private String autorPrincipal;

    private String nacionalidadeAutor;

    private String editora;

    private List<String> assuntos;

    private List<String> autoresSecundarios;

    private Integer quantidadeExemplares;
}