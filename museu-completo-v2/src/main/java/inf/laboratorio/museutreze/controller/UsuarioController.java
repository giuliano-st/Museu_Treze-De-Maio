package inf.laboratorio.museutreze.controller;

import inf.laboratorio.museutreze.dto.UsuarioDTORequest;
import inf.laboratorio.museutreze.dto.UsuarioDTOResponse;
import inf.laboratorio.museutreze.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<UsuarioDTOResponse> salvar(@RequestBody UsuarioDTORequest usuarioDTO) {
        return ResponseEntity.ok(usuarioService.salvar(usuarioDTO));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTOResponse>> listar() {
        return ResponseEntity.ok(usuarioService.listar());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTOResponse> atualizar(@PathVariable Long id, @RequestBody UsuarioDTORequest usuarioDTO) {
        return ResponseEntity.ok(usuarioService.atualizar(id, usuarioDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        usuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
