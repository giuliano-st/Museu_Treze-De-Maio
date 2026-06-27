package inf.laboratorio.museutreze.mapper;

import inf.laboratorio.museutreze.dto.AutorDTORequest;
import inf.laboratorio.museutreze.dto.AutorDTOResponse;
import inf.laboratorio.museutreze.model.Autor;
import org.springframework.stereotype.Component;

@Component
public class AutorMapper {

    public Autor toEntity(AutorDTORequest request) {
        if (request == null) return null;
        Autor autor = new Autor();
        autor.setNome(request.nome());
        autor.setNacionalidade(request.nacionalidade());
        return autor;
    }

    public AutorDTOResponse toResponse(Autor autor) {
        if (autor == null) return null;
        return new AutorDTOResponse(
                autor.getId(),
                autor.getNome(),
                autor.getNacionalidade()
        );
    }
}