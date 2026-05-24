package com.acervo.controller;

import com.acervo.model.Obra;
import com.acervo.service.ObraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/obra")
@CrossOrigin(origins = "http://localhost:5173")
public class ObraController {

    @Autowired
    private ObraService obraService;


    @GetMapping("/listar")
    public List<Obra> listar() {
        return obraService.listarTodas();
    }


    @GetMapping("/buscar")
    public List<Obra> buscar(
            @RequestParam(required = false) String termo,
            @RequestParam(required = false) String tipo,
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) String dataInicio,
            @RequestParam(required = false) String dataFim
    ) {
        return obraService.buscar(termo, tipo, categoria, dataInicio, dataFim);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Obra> buscarPorId(@PathVariable Long id) {
        return obraService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping("/salvar")
    public ResponseEntity<Obra> salvar(@RequestBody Obra obra,
                                       @RequestHeader("email-admin") String emailAdmin) {
        return ResponseEntity.ok(obraService.salvar(obra, emailAdmin));
    }


    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id,
                                        @RequestHeader("email-admin") String emailAdmin) {
        obraService.excluir(id, emailAdmin);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/saida/{id}")
    public ResponseEntity<Obra> registrarSaida(@PathVariable Long id,
                                               @RequestHeader("email-admin") String emailAdmin) {
        return ResponseEntity.ok(obraService.registrarSaida(id, emailAdmin));
    }


    @PostMapping("/devolucao/{id}")
    public ResponseEntity<Obra> registrarDevolucao(@PathVariable Long id,
                                                   @RequestHeader("email-admin") String emailAdmin) {
        return ResponseEntity.ok(obraService.registrarDevolucao(id, emailAdmin));
    }


    @GetMapping("/mais-acessadas")
    public List<Obra> maisAcessadas() {
        return obraService.maisAcessadas();
    }
}
