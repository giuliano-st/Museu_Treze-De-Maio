package inf.laboratorio.museutreze.controller;

import inf.laboratorio.museutreze.dto.ObraHistoricoDTORequest;
import inf.laboratorio.museutreze.dto.ObraHistoricoDTOResponse;
import inf.laboratorio.museutreze.service.ObraHistoricoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/historico")
public class ObraHistoricoController {

    private final ObraHistoricoService obraHistoricoService;

    public ObraHistoricoController(ObraHistoricoService obraHistoricoService) {
        this.obraHistoricoService = obraHistoricoService;
    }

    @PostMapping
    public ResponseEntity<ObraHistoricoDTOResponse> salvar(@RequestBody ObraHistoricoDTORequest obraHistoricoDTO) {
        return ResponseEntity.ok(obraHistoricoService.salvar(obraHistoricoDTO));
    }

    @PostMapping("/lista")
    public ResponseEntity<List<ObraHistoricoDTOResponse>> salvarLista(@RequestBody List<ObraHistoricoDTORequest> historicosDTO) {
        return ResponseEntity.ok(obraHistoricoService.salvarLista(historicosDTO));
    }

    @GetMapping
    public ResponseEntity<List<ObraHistoricoDTOResponse>> listar() {
        return ResponseEntity.ok(obraHistoricoService.listar());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ObraHistoricoDTOResponse> atualizar(@PathVariable Long id, @RequestBody ObraHistoricoDTORequest obraHistoricoDTO) {
        return ResponseEntity.ok(obraHistoricoService.atualizar(id, obraHistoricoDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        obraHistoricoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}