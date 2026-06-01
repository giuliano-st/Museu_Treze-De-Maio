package inf.laboratorio.museutreze.repository;
import inf.laboratorio.museutreze.model.Editora;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EditoraRepository extends JpaRepository<Editora, Long> {
    Editora findById(long id);
    Editora findByNomeIgnoreCase(String nome);

    List<Editora> findByNomeContainingIgnoreCase(String nome);

}
