package inf.laboratorio.museutreze.mapper;

import inf.laboratorio.museutreze.dto.SecundarioDTORequest;
import inf.laboratorio.museutreze.dto.SecundarioDTOResponse;
import inf.laboratorio.museutreze.model.Autor;
import inf.laboratorio.museutreze.model.Obra;
import inf.laboratorio.museutreze.model.Secundario;
import org.springframework.stereotype.Component;

@Component
public class SecundarioMapper {

    public Secundario toEntity(SecundarioDTORequest request, Obra obra, Autor autor) {
        if (request == null) return null;
        Secundario secundario = new Secundario();
        secundario.setObraId(obra);
        secundario.setAutorId(autor);
        return secundario;
    }

    public SecundarioDTOResponse toResponse(Secundario secundario) {
        if (secundario == null) return null;

        Long obraId = secundario.getObraId() != null ? secundario.getObraId().getId() : null;
        String obraTitulo = secundario.getObraId() != null ? secundario.getObraId().getTitulo_Principal() : null;

        Long autorId = secundario.getAutorId() != null ? secundario.getAutorId().getId() : null;
        String autorNome = secundario.getAutorId() != null ? secundario.getAutorId().getNome() : null;

        return new SecundarioDTOResponse(
                secundario.getId(),
                obraId,
                obraTitulo,
                autorId,
                autorNome
        );
    }
}