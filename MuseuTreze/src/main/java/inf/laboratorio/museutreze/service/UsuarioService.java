package inf.laboratorio.museutreze.service;

import inf.laboratorio.museutreze.dto.UsuarioDTORequest;
import inf.laboratorio.museutreze.dto.UsuarioDTOResponse;
import inf.laboratorio.museutreze.model.Usuario;
import inf.laboratorio.museutreze.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public UsuarioDTOResponse salvar(UsuarioDTORequest usuarioDTO){
        Usuario usuario = new Usuario();
        usuario.setNomeUsuario(usuarioDTO.nomeUsuario());
        usuario.setSenha(usuarioDTO.senha());
        usuario.setRole(usuarioDTO.role());
        usuario.setEmail(usuarioDTO.email());
        usuarioRepository.save(usuario);

        return new UsuarioDTOResponse(
                usuario.getId(),
                usuario.getNomeUsuario(),
                usuario.getRole(),
                usuario.getEmail()
        );
    }

    public UsuarioDTOResponse buscarPorId(Long id){
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));
        return new UsuarioDTOResponse(
                usuario.getId(),
                usuario.getNomeUsuario(),
                usuario.getRole(),
                usuario.getEmail()
        );
    }

    public List<UsuarioDTOResponse> listar(){
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream().map(usuario -> new UsuarioDTOResponse(
                usuario.getId(),
                usuario.getNomeUsuario(),
                usuario.getRole(),
                usuario.getEmail()
        )).toList();
    }

    public UsuarioDTOResponse atualizar(Long id, UsuarioDTORequest usuarioDTO){
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));
        usuario.setNomeUsuario(usuarioDTO.nomeUsuario());
        usuario.setSenha(usuarioDTO.senha());
        usuario.setRole(usuarioDTO.role());
        usuario.setEmail(usuarioDTO.email());
        usuarioRepository.save(usuario);

        return new UsuarioDTOResponse(
                usuario.getId(),
                usuario.getNomeUsuario(),
                usuario.getRole(),
                usuario.getEmail()
        );
    }

    public void deletar(Long id){
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));
        usuarioRepository.delete(usuario);
    }
}
