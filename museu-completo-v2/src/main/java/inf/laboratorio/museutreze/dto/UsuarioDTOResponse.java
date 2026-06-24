package inf.laboratorio.museutreze.dto;

public record UsuarioDTOResponse(
        Long id,
        String nomeUsuario,
        String role,
        String email
) {}