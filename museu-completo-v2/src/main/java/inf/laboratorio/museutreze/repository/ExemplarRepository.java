package inf.laboratorio.museutreze.repository;

import inf.laboratorio.museutreze.model.Exemplar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExemplarRepository extends JpaRepository<Exemplar, Long> {
    Exemplar findByid(Long id);
    Exemplar findByDisponibilidade(Boolean disponibilidade);
    List<Exemplar> findByObraIdAndDisponibilidade(Long obraId, Boolean disponibilidade);

    List<Exemplar> findByObraId(Long obraId);
}
