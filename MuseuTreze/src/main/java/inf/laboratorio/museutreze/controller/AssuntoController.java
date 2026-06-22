package inf.laboratorio.museutreze.controller;

import inf.laboratorio.museutreze.dto.AssuntoDTORequest;
import inf.laboratorio.museutreze.dto.AssuntoDTOResponse;
import inf.laboratorio.museutreze.service.AssuntoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assuntos")
public class AssuntoController {

    private final AssuntoService assuntoService;

    public AssuntoController(AssuntoService assuntoService) {
        this.assuntoService = assuntoService;
    }

    @PostMapping
    public ResponseEntity<AssuntoDTOResponse> salvar(@RequestBody AssuntoDTORequest assuntoDTO) {
        return ResponseEntity.ok(assuntoService.salvar(assuntoDTO));
    }

    @GetMapping
    public ResponseEntity<List<AssuntoDTOResponse>> listar() {
        return ResponseEntity.ok(assuntoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssuntoDTOResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(assuntoService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssuntoDTOResponse> atualizar(@PathVariable Long id, @RequestBody AssuntoDTORequest assuntoDTO) {
        return ResponseEntity.ok(assuntoService.atualizar(id, assuntoDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        assuntoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
