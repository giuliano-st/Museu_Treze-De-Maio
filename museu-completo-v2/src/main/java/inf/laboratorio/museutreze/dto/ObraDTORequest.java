package inf.laboratorio.museutreze.dto;

import java.util.Date;
import java.util.List;

public record ObraDTORequest(
        String obra_tipo,
        String titulo_Principal,
        String capa,
        String local,
        Date data,
        String descFisica,
        String nome,
        String numeroChamada,
        String chamadaLocal,
        String tituloUniforme,
        String isbn,
        String serie,
        String edicao,
        String colecao,
        String notasGerais,
        String issn,
        Integer volume,
        String periodicidade,
        Long autorId,
        Long editoraId,
        List<Long> assuntosIds
) {}