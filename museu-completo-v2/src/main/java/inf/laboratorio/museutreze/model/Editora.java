package inf.laboratorio.museutreze.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "editoras")
public class Editora {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;
}
