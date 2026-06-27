package inf.laboratorio.museutreze.controller;

import inf.laboratorio.museutreze.dto.SecundarioDTORequest;
import inf.laboratorio.museutreze.dto.SecundarioDTOResponse;
import inf.laboratorio.museutreze.service.SecundarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/secundarios")
public class SecundarioController {

    private final SecundarioService secundarioService;

    public SecundarioController(SecundarioService secundarioService) {
        this.secundarioService = secundarioService;
    }

    @PostMapping
    public ResponseEntity<SecundarioDTOResponse> salvar(@RequestBody SecundarioDTORequest secundarioDTO) {
        return ResponseEntity.ok(secundarioService.salvar(secundarioDTO));
    }

    @PostMapping("/lista")
    public ResponseEntity<List<SecundarioDTOResponse>> salvarLista(@RequestBody List<SecundarioDTORequest> secundariosDTO) {
        return ResponseEntity.ok(secundarioService.salvarLista(secundariosDTO));
    }

    @GetMapping
    public ResponseEntity<List<SecundarioDTOResponse>> listar() {
        return ResponseEntity.ok(secundarioService.listar());
    }

    @PutMapping("/{id}")
    public ResponseEntity<SecundarioDTOResponse> atualizar(@PathVariable Long id, @RequestBody SecundarioDTORequest secundarioDTO) {
        return ResponseEntity.ok(secundarioService.atualizar(id, secundarioDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        secundarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
