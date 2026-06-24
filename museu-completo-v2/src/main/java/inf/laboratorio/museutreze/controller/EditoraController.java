package inf.laboratorio.museutreze.controller;

import inf.laboratorio.museutreze.dto.EditoraDTORequest;
import inf.laboratorio.museutreze.dto.EditoraDTOResponse;
import inf.laboratorio.museutreze.service.EditoraService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/editoras")
public class EditoraController {

    private final EditoraService editoraService;

    public EditoraController(EditoraService editoraService) {
        this.editoraService = editoraService;
    }

    @PostMapping
    public ResponseEntity<EditoraDTOResponse> salvar(@RequestBody EditoraDTORequest editoraDTO) {
        return ResponseEntity.ok(editoraService.salvar(editoraDTO));
    }

    @PostMapping("/lista")
    public ResponseEntity<List<EditoraDTOResponse>> salvarLista(@RequestBody List<EditoraDTORequest> editorasDTO) {
        return ResponseEntity.ok(editoraService.salvarLista(editorasDTO));
    }

    @GetMapping
    public ResponseEntity<List<EditoraDTOResponse>> listar() {
        return ResponseEntity.ok(editoraService.listar());
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
