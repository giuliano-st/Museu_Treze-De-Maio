package inf.laboratorio.museutreze.mapper;

import inf.laboratorio.museutreze.dto.EditoraDTORequest;
import inf.laboratorio.museutreze.dto.EditoraDTOResponse;
import inf.laboratorio.museutreze.model.Editora;
import org.springframework.stereotype.Component;

@Component
public class EditoraMapper {

    public Editora toEntity(EditoraDTORequest request) {
        if (request == null) return null;
        Editora editora = new Editora();
        editora.setNome(request.nome());
        return editora;
    }

    public EditoraDTOResponse toResponse(Editora editora) {
        if (editora == null) return null;
        return new EditoraDTOResponse(
                editora.getId(),
                editora.getNome()
        );
    }
}