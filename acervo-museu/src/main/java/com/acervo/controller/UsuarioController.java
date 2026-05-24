package com.acervo.controller;

import com.acervo.model.Usuario;
import com.acervo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/usuario")
@CrossOrigin(origins = "http://localhost:5173")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // POST /usuario/cadastrar
    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrar(@RequestBody Usuario usuario) {
        try {
            Usuario salvo = usuarioService.cadastrar(usuario);
            return ResponseEntity.ok(salvo);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }

    // POST /usuario/login — recebe { email, senha }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String senha = body.get("senha");
        Optional<Usuario> usuario = usuarioService.login(email, senha);
        if (usuario.isPresent()) {
            return ResponseEntity.ok(usuario.get());
        }
        return ResponseEntity.status(401).body(Map.of("erro", "E-mail ou senha inválidos"));
    }
}
