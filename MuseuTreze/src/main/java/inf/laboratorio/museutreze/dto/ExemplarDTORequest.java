package inf.laboratorio.museutreze.dto;

public record ExemplarDTORequest(
        Boolean disponibilidade,
        Integer numero,
        Long obraId
) {}