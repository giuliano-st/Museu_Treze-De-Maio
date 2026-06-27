package inf.laboratorio.museutreze.mapper;

import inf.laboratorio.museutreze.dto.AssuntoDTORequest;
import inf.laboratorio.museutreze.dto.AssuntoDTOResponse;
import inf.laboratorio.museutreze.model.Assunto;
import org.springframework.stereotype.Component;

@Component
public class AssuntoMapper {

    public Assunto toEntity(AssuntoDTORequest request) {
        if (request == null) return null;
        Assunto assunto = new Assunto();
        assunto.setDescricao(request.descricao());
        return assunto;
    }

    public AssuntoDTOResponse toResponse(Assunto assunto) {
        if (assunto == null) return null;
        return new AssuntoDTOResponse(
                assunto.getId(),
                assunto.getDescricao()
        );
    }
}