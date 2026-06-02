package inf.laboratorio.museutreze.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Secundario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "obraId")
    private Obra obraId;

    @ManyToOne
    @JoinColumn(name = "autorId")
    private Autor autorId;


}
