package inf.laboratorio.museutreze.repository;

import inf.laboratorio.museutreze.model.Assunto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssuntoRepository extends JpaRepository<Assunto, Integer> {
    Assunto findById(int id);
    Assunto findByDescricao(String descricao);

    List<Assunto> findByDescricaoContainingIgnoreCase(String descricao);

}
