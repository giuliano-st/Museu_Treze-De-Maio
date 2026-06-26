package inf.laboratorio.museutreze.dto;

import java.time.LocalDate;
import java.util.List;

public record ObraDTOResponse(
        Long id,
        String obra_tipo,
        String titulo_Principal,
        String capa,
        String local,
        LocalDate data,
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
        AutorDTOResponse autor,
        EditoraDTOResponse editora,
        List<AssuntoDTOResponse> assuntos
) {}