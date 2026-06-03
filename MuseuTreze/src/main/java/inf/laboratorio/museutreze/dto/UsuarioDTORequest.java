package inf.laboratorio.museutreze.dto;

public record UsuarioDTORequest(
        String nomeUsuario,
        String role,
        String senha,
        String email
) {}