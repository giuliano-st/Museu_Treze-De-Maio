package inf.laboratorio.museutreze.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "assuntos")
public class Assunto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String descricao;
}