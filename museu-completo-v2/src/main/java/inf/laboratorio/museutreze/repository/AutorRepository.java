package inf.laboratorio.museutreze.repository;

import inf.laboratorio.museutreze.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    Autor findByNacionalidade(String nacionalidade);
    Optional<Autor> findByNome(String nome);
    List<Autor> findByNomeContains(String nome);
    Optional<Autor> findByNomeIgnoreCase(String nome);

}
