package inf.laboratorio.museutreze.mapper;

import inf.laboratorio.museutreze.dto.UsuarioDTORequest;
import inf.laboratorio.museutreze.dto.UsuarioDTOResponse;
import inf.laboratorio.museutreze.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public Usuario toEntity(UsuarioDTORequest request) {
        if (request == null) return null;
        Usuario usuario = new Usuario();
        usuario.setNomeUsuario(request.nomeUsuario());
        usuario.setRole(request.role());
        usuario.setSenha(request.senha());
        usuario.setEmail(request.email());
        return usuario;
    }

    public UsuarioDTOResponse toResponse(Usuario usuario) {
        if (usuario == null) return null;
        return new UsuarioDTOResponse(
                usuario.getId(),
                usuario.getNomeUsuario(),
                usuario.getRole(),
                usuario.getEmail()
        );
    }
}