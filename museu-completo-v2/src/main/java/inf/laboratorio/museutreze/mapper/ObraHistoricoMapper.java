package inf.laboratorio.museutreze.mapper;

import inf.laboratorio.museutreze.dto.ObraHistoricoDTORequest;
import inf.laboratorio.museutreze.dto.ObraHistoricoDTOResponse;
import inf.laboratorio.museutreze.model.Obra;
import inf.laboratorio.museutreze.model.ObraHistorico;
import inf.laboratorio.museutreze.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class ObraHistoricoMapper {

    public ObraHistorico toEntity(ObraHistoricoDTORequest request, Usuario usuario, Obra obra) {
        if (request == null) return null;
        ObraHistorico historico = new ObraHistorico();
        historico.setOperacao(request.operacao());
        historico.setUsuario(usuario);
        historico.setObra(obra);
        return historico;
    }

    public ObraHistoricoDTOResponse toResponse(ObraHistorico historico) {
        if (historico == null) return null;

        Long usuarioId = historico.getUsuario() != null ? historico.getUsuario().getId() : null;
        String nomeUsuario = historico.getUsuario() != null ? historico.getUsuario().getNomeUsuario() : null;

        Long obraId = historico.getObra() != null ? historico.getObra().getId() : null;
        String obraTitulo = historico.getObra() != null ? historico.getObra().getTitulo_Principal() : null;

        return new ObraHistoricoDTOResponse(
                historico.getId(),
                historico.getOperacao(),
                historico.getData(),
                usuarioId,
                nomeUsuario,
                obraId,
                obraTitulo
        );
    }
}