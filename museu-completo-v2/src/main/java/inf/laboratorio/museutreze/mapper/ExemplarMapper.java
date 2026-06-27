package inf.laboratorio.museutreze.mapper;

import inf.laboratorio.museutreze.dto.ExemplarDTORequest;
import inf.laboratorio.museutreze.dto.ExemplarDTOResponse;
import inf.laboratorio.museutreze.model.Exemplar;
import inf.laboratorio.museutreze.model.Obra;
import org.springframework.stereotype.Component;

@Component
public class ExemplarMapper {

    public Exemplar toEntity(ExemplarDTORequest request, Obra obra) {
        if (request == null) return null;
        Exemplar exemplar = new Exemplar();
        exemplar.setDisponibilidade(request.disponibilidade());
        exemplar.setNumero(request.numero());
        exemplar.setObra(obra);
        return exemplar;
    }

    public ExemplarDTOResponse toResponse(Exemplar exemplar) {
        if (exemplar == null) return null;

        Long obraId = exemplar.getObra() != null ? exemplar.getObra().getId() : null;
        String obraTitulo = exemplar.getObra() != null ? exemplar.getObra().getTitulo_Principal() : null;

        return new ExemplarDTOResponse(
                exemplar.getId(),
                exemplar.getDisponibilidade(),
                exemplar.getNumero(),
                obraId,
                obraTitulo
        );
    }
}