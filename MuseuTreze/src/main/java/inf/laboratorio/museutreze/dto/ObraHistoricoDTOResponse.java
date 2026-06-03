package inf.laboratorio.museutreze.dto;

import java.time.LocalDateTime;

public record ObraHistoricoDTOResponse(
        Long id,
        String operacao,
        LocalDateTime data,
        Long usuarioId,
        String nomeUsuario,
        Long obraId,
        String obraTitulo
) {}