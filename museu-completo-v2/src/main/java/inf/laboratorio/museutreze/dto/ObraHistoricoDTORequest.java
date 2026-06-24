package inf.laboratorio.museutreze.dto;

public record ObraHistoricoDTORequest(
        String operacao,
        Long usuarioId,
        Long obraId
) {}