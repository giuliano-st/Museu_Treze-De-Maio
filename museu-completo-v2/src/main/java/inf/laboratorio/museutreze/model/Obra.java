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
    private Long id; // CHECK 1

    @Column(nullable = false)
    private String obra_tipo; // CHECK 1

    @Column(nullable = false)
    private String titulo_Principal; // CHECK 1

    private String capa; // CHECK 1
    private String local;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate data; // CHECK 1

    private String descFisica;
    private String nome;
    private String numeroChamada; // CHECK 1
    private String chamadaLocal; // CHECK 1
    private String tituloUniforme;
    private String isbn; // CHECK 1
    private String serie; // CHECK 1
    private String edicao; // CHECK 1
    private String colecao; // CHECK 1
    private String notasGerais; // CHECK 1
    private String issn; // CHECK 1
    private Integer volume; // CHECK 1
    private String periodicidade; // CHECK 1

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
