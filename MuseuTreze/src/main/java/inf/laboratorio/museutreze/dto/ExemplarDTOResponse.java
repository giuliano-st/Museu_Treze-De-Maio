package inf.laboratorio.museutreze.dto;

public record ExemplarDTOResponse(
        Long id,
        Boolean disponibilidade,
        Integer numero,
        Long obraId,
        String obraTitulo
) {}