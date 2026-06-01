package inf.laboratorio.museutreze.repository;

import inf.laboratorio.museutreze.model.Obra;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ObraRepository extends JpaRepository<Obra, Long> {

    Optional<Obra> findByObraId(Long obraId);

    List<Obra> findByObra_tipoEqualsIgnoreCase(String obra_tipo);
    List<Obra> findByAutorId(Long autor_id);
    List<Obra> findByEditoraId(Long editora_id);
    List<Obra> findByNumeroChamada(String numeroChamada);
    List<Obra> findByIsbn(String isbn);
    List<Obra> findByIssn(String issn);
    List<Obra> findByTituloUniforme(String tituloUniforme);

}
