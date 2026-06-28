package inf.laboratorio.museutreze.repository;

import inf.laboratorio.museutreze.model.Autor;
import inf.laboratorio.museutreze.model.Obra;
import inf.laboratorio.museutreze.model.Secundario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SecundarioRepository extends JpaRepository<Secundario, Long> {
    Secundario findById(long id);

    List<Secundario> findAllByAutorId(Autor autorId);

    List<Secundario> findAllByObraId(Obra obraId);
}
