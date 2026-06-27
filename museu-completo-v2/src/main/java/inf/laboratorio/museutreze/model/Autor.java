package inf.laboratorio.museutreze.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "autorId")
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String nacionalidade;
}
