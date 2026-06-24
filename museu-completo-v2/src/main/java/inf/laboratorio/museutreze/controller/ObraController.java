package inf.laboratorio.museutreze.controller;

import inf.laboratorio.museutreze.dto.ObraDTORequest;
import inf.laboratorio.museutreze.dto.ObraDTOResponse;
import inf.laboratorio.museutreze.service.ObraService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/obras")
public class ObraController {

    private final ObraService obraService;

    public ObraController(ObraService obraService) {
        this.obraService = obraService;
    }

    @PostMapping
    public ResponseEntity<ObraDTOResponse> salvar(@RequestBody ObraDTORequest obraDTO) {
        return ResponseEntity.ok(obraService.salvar(obraDTO));
    }

    @GetMapping
    public ResponseEntity<List<ObraDTOResponse>> listar() {
        return ResponseEntity.ok(obraService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ObraDTOResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(obraService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ObraDTOResponse> atualizar(@PathVariable Long id, @RequestBody ObraDTORequest obraDTO) {
        return ResponseEntity.ok(obraService.atualizar(id, obraDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        obraService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
