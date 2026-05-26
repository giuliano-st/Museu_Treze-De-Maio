package com.acervo.controller;

import com.acervo.model.LogAcao;
import com.acervo.repository.LogAcaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdminController {

    @Autowired
    private LogAcaoRepository logAcaoRepository;

    @GetMapping("/log/acoes")
    public List<LogAcao> logs() {
        return logAcaoRepository.findAllByOrderByDataHoraDesc();
    }
}
