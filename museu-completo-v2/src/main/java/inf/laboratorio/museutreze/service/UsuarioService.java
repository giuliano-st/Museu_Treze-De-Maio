package inf.laboratorio.museutreze.service;

import inf.laboratorio.museutreze.dto.UsuarioDTORequest;

import inf.laboratorio.museutreze.dto.UsuarioDTOResponse;

import inf.laboratorio.museutreze.model.Usuario;

import inf.laboratorio.museutreze.repository.UsuarioRepository;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

import java.util.ArrayList;

import java.util.List;

import java.util.Optional;

@Service

public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    private final PasswordEncoder passwordEncoder;

    public UsuarioService(

            UsuarioRepository usuarioRepository,

            PasswordEncoder passwordEncoder

    ) {

        this.usuarioRepository = usuarioRepository;

        this.passwordEncoder = passwordEncoder;

    }

    public UsuarioDTOResponse salvar(UsuarioDTORequest usuarioDTO) {

// Verifica se o e-mail já existe

        Optional<Usuario> existente = usuarioRepository.findByEmail(usuarioDTO.email());

        if (existente.isPresent()) {

            throw new RuntimeException("Já existe um usuário com este e-mail!");

        }

        Usuario usuario = new Usuario();

        usuario.setNomeUsuario(usuarioDTO.nomeUsuario());

        usuario.setRole(usuarioDTO.role());

        usuario.setEmail(usuarioDTO.email());

// Criptografa a senha ANTES de salvar

        usuario.setSenha(passwordEncoder.encode(usuarioDTO.senha()));

        usuarioRepository.save(usuario);

        return new UsuarioDTOResponse(

                usuario.getId(),

                usuario.getNomeUsuario(),

                usuario.getRole(),

                usuario.getEmail()

        );

    }

    public List<UsuarioDTOResponse> salvarLista(List<UsuarioDTORequest> usuariosDTO) {

        List<UsuarioDTOResponse> responses = new ArrayList<>();

        for (UsuarioDTORequest dto : usuariosDTO) {

            responses.add(salvar(dto));

        }

        return responses;

    }

    public UsuarioDTOResponse buscarPorId(Long id) {

        Usuario usuario = usuarioRepository.findById(id)

                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        return new UsuarioDTOResponse(

                usuario.getId(),

                usuario.getNomeUsuario(),

                usuario.getRole(),

                usuario.getEmail()

        );

    }

    public List<UsuarioDTOResponse> listar() {

        List<Usuario> usuarios = usuarioRepository.findAll();

        return usuarios.stream()

                .map(usuario -> new UsuarioDTOResponse(

                        usuario.getId(),

                        usuario.getNomeUsuario(),

                        usuario.getRole(),

                        usuario.getEmail()

                ))

                .toList();

    }

    public UsuarioDTOResponse atualizar(Long id, UsuarioDTORequest usuarioDTO) {

        Usuario usuario = usuarioRepository.findById(id)

                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

// Verifica se o novo e-mail já pertence a outro usuário

        usuarioRepository.findByEmail(usuarioDTO.email())

                .ifPresent(u -> {

                    if (!u.getId().equals(id)) {

                        throw new RuntimeException("Este e-mail já está em uso!");

                    }

                });

        usuario.setNomeUsuario(usuarioDTO.nomeUsuario());

        usuario.setRole(usuarioDTO.role());

        usuario.setEmail(usuarioDTO.email());

// Criptografa a nova senha

        usuario.setSenha(passwordEncoder.encode(usuarioDTO.senha()));

        usuarioRepository.save(usuario);

        return new UsuarioDTOResponse(

                usuario.getId(),

                usuario.getNomeUsuario(),

                usuario.getRole(),

                usuario.getEmail()

        );

    }

    public void deletar(Long id) {

        Usuario usuario = usuarioRepository.findById(id)

                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        usuarioRepository.delete(usuario);

    }

}