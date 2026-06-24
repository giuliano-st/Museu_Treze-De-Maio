package inf.laboratorio.museutreze.repository;

import inf.laboratorio.museutreze.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    Autor findByNome(String nome);
    Autor findByNacionalidade(String nacionalidade);

    List<Autor> findByNomeContains(String nome);

}
