package inf.laboratorio.museutreze.controller;

import inf.laboratorio.museutreze.dto.AutorDTORequest;
import inf.laboratorio.museutreze.dto.AutorDTOResponse;
import inf.laboratorio.museutreze.service.AutorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/autores")
public class AutorController {

    private final AutorService autorService;

    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }

    @PostMapping
    public ResponseEntity<AutorDTOResponse> salvar(@RequestBody AutorDTORequest autorDTO) {
        return ResponseEntity.ok(autorService.salvar(autorDTO));
    }

    @GetMapping
    public ResponseEntity<List<AutorDTOResponse>> listar() {
        return ResponseEntity.ok(autorService.listar());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AutorDTOResponse> atualizar(@PathVariable Long id, @RequestBody AutorDTORequest autorDTO) {
        return ResponseEntity.ok(autorService.atualizar(id, autorDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        autorService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
