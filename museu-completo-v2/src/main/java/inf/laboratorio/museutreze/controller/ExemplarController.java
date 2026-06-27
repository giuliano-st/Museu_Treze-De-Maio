package inf.laboratorio.museutreze.controller;

import inf.laboratorio.museutreze.dto.ExemplarDTORequest;
import inf.laboratorio.museutreze.dto.ExemplarDTOResponse;
import inf.laboratorio.museutreze.service.ExemplarService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exemplares")
public class ExemplarController {

    private final ExemplarService exemplarService;

    public ExemplarController(ExemplarService exemplarService) {
        this.exemplarService = exemplarService;
    }

    @PostMapping
    public ResponseEntity<ExemplarDTOResponse> salvar(@RequestBody ExemplarDTORequest exemplarDTO) {
        return ResponseEntity.ok(exemplarService.salvar(exemplarDTO));
    }

    @PostMapping("/lista")
    public ResponseEntity<List<ExemplarDTOResponse>> salvarLista(@RequestBody List<ExemplarDTORequest> exemplaresDTO) {
        return ResponseEntity.ok(exemplarService.salvarLista(exemplaresDTO));
    }

    @GetMapping
    public ResponseEntity<List<ExemplarDTOResponse>> listar() {
        return ResponseEntity.ok(exemplarService.listar());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExemplarDTOResponse> atualizar(@PathVariable Long id, @RequestBody ExemplarDTORequest exemplarDTO) {
        return ResponseEntity.ok(exemplarService.atualizar(id, exemplarDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        exemplarService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}