package inf.laboratorio.museutreze.repository;

import inf.laboratorio.museutreze.model.ObraHistorico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface ObraHistoricoRepository extends JpaRepository<ObraHistorico, Long> {
    ObraHistorico findByObraId(Long obraId);

    List<ObraHistorico> findByDataContains( Date date);
    List<ObraHistorico> findByOperacao(String operacao);

}
