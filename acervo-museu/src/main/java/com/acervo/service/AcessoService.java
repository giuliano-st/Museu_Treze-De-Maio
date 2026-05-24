package com.acervo.service;

import com.acervo.model.RegistroAcesso;
import com.acervo.repository.RegistroAcessoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AcessoService {

    @Autowired
    private RegistroAcessoRepository registroAcessoRepository;


    public void registrar(String ip, String pagina, String emailUsuario) {
        RegistroAcesso acesso = new RegistroAcesso();
        acesso.setIp(ip);
        acesso.setPagina(pagina);
        acesso.setEmailUsuario(emailUsuario); // null se não logado
        registroAcessoRepository.save(acesso);
    }

    public List<RegistroAcesso> listarTodos() {
        return registroAcessoRepository.findAllByOrderByDataHoraDesc();
    }
}
