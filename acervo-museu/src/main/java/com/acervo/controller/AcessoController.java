package com.acervo.controller;

import com.acervo.model.LogAcao;
import com.acervo.model.RegistroAcesso;
import com.acervo.repository.LogAcaoRepository;
import com.acervo.service.AcessoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class AcessoController {

    @Autowired
    private AcessoService acessoService;

    @Autowired
    private LogAcaoRepository logAcaoRepository;


    @PostMapping("/acesso/registrar")
    public void registrar(@RequestBody Map<String, String> body,
                          @RequestHeader(value = "X-Forwarded-For", required = false) String ip) {
        String ipFinal = (ip != null) ? ip : "desconhecido";
        acessoService.registrar(ipFinal, body.get("pagina"), body.get("emailUsuario"));
    }


    @GetMapping("/acesso/historico")
    public List<RegistroAcesso> historico() {
        return acessoService.listarTodos();
    }


    @GetMapping("/log/acoes")
    public List<LogAcao> logs() {
        return logAcaoRepository.findAllByOrderByDataHoraDesc();
    }
}
