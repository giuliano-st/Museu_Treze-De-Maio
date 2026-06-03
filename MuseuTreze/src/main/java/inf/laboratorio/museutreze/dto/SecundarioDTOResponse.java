package inf.laboratorio.museutreze.dto;

public record SecundarioDTOResponse(
        Long id,
        Long obraId,
        String obraTitulo,
        Long autorId,
        String autorNome
) {}