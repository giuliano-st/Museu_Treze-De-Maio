package inf.laboratorio.museutreze.dto;

import inf.laboratorio.museutreze.model.Role;

public record UsuarioDTOResponse(
        Long id,
        String nomeUsuario,
        Role role,
        String email
) {}