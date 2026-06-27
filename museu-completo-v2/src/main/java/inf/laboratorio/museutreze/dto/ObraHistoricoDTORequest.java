package inf.laboratorio.museutreze.dto;

public record ObraHistoricoDTORequest(
        String operacao,
        Long usuarioId,
        String nomeObra
) {}