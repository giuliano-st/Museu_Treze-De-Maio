package com.acervo.service;

import com.acervo.model.LogAcao;
import com.acervo.model.Usuario;
import com.acervo.repository.LogAcaoRepository;
import com.acervo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LogAcaoRepository logAcaoRepository;

    public Usuario cadastrar(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("E-mail já cadastrado.");
        }
        return usuarioRepository.save(usuario);
    }


    public Optional<Usuario> login(String email, String senha) {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
        if (usuario.isPresent() && usuario.get().getSenha().equals(senha)) {
            // Registra o login no log de ações
            LogAcao log = new LogAcao();
            log.setEmailUsuario(email);
            log.setAcao("LOGIN");
            log.setDetalhes("Login realizado com sucesso");
            logAcaoRepository.save(log);
            return usuario;
        }
        return Optional.empty();
    }
}
