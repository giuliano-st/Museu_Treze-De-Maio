package inf.laboratorio.museutreze.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "obras")
public class Obra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "obraId")
    private Long id;

    @Column(nullable = false)
    private String obra_tipo;

    @Column(nullable = false)
    private String titulo_Principal;

    private String capa;
    private String local;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate data;

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

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;

    @ManyToOne
    @JoinColumn(name = "editora_id")
    private Editora editora;

    @ManyToMany
    @JoinTable(
            name = "obra_assunto",
            joinColumns = @JoinColumn(name = "obra_id"),
            inverseJoinColumns = @JoinColumn(name = "assunto_id")
    )
    private List<Assunto> assuntos;
}
