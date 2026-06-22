package inf.laboratorio.museutreze.controller;

import inf.laboratorio.museutreze.dto.EditoraDTORequest;
import inf.laboratorio.museutreze.dto.EditoraDTOResponse;
import inf.laboratorio.museutreze.service.EditoraService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/editoras")
public class EditoraController {

    private final EditoraService editoraService;

    public EditoraController(EditoraService editoraService) {
        this.editoraService = editoraService;
    }

    @PostMapping
    public ResponseEntity<EditoraDTOResponse> salvar(@RequestBody EditoraDTORequest editoraDTO) {
        return ResponseEntity.ok(editoraService.salvar(editoraDTO));
    }

    @GetMapping
    public ResponseEntity<List<EditoraDTOResponse>> listar() {
        return ResponseEntity.ok(editoraService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EditoraDTOResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(editoraService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EditoraDTOResponse> atualizar(@PathVariable Long id, @RequestBody EditoraDTORequest editoraDTO) {
        return ResponseEntity.ok(editoraService.atualizar(id, editoraDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        editoraService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
