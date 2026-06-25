package inf.laboratorio.museutreze.dto;

import inf.laboratorio.museutreze.model.Role;

public record UsuarioDTORequest(
        String nomeUsuario,
        Role role,
        String senha,
        String email
) {}