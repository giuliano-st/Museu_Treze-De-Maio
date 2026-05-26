package com.acervo.service;

import com.acervo.model.LogAcao;
import com.acervo.model.Obra;
import com.acervo.repository.LogAcaoRepository;
import com.acervo.repository.ObraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ObraService {

    @Autowired
    private ObraRepository obraRepository;

    @Autowired
    private LogAcaoRepository logAcaoRepository;

    public Obra salvar(Obra obra, String emailAdmin) {
        Obra salva = obraRepository.save(obra);


        LogAcao log = new LogAcao();
        log.setEmailUsuario(emailAdmin);
        log.setAcao(obra.getId() == null ? "CADASTROU_OBRA" : "EDITOU_OBRA");
        log.setDetalhes("Obra: " + obra.getTitulo() + " | Tipo: " + obra.getTipo());
        logAcaoRepository.save(log);

        return salva;
    }

    public List<Obra> buscar(String termo, String tipo, String categoria, String dataInicio, String dataFim) {
        List<Obra> resultados = obraRepository.buscarComFiltros(
                (termo != null && !termo.isBlank()) ? termo : null,
                (tipo != null && !tipo.isBlank()) ? tipo : null,
                (categoria != null && !categoria.isBlank()) ? categoria : null,
                (dataInicio != null && !dataInicio.isBlank()) ? dataInicio : null,
                (dataFim != null && !dataFim.isBlank()) ? dataFim : null
        );


        for (Obra o : resultados) {
            o.setContadorBuscas(o.getContadorBuscas() + 1);
            obraRepository.save(o);
        }

        return resultados;
    }

    public List<Obra> listarTodas() {
        return obraRepository.findAll();
    }

    public Optional<Obra> buscarPorId(Long id) {
        return obraRepository.findById(id);
    }

    public void excluir(Long id, String emailAdmin) {
        Optional<Obra> obra = obraRepository.findById(id);
        obra.ifPresent(o -> {
            LogAcao log = new LogAcao();
            log.setEmailUsuario(emailAdmin);
            log.setAcao("EXCLUIU_OBRA");
            log.setDetalhes("Obra: " + o.getTitulo() + " (id=" + id + ")");
            logAcaoRepository.save(log);
            obraRepository.deleteById(id);
        });
    }


    public Obra registrarSaida(Long id, String emailAdmin) {
        Obra obra = obraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Obra não encontrada"));

        obra.setQuantidadeSaidas(obra.getQuantidadeSaidas() + 1);
        obra.setStatus("EMPRESTADO");
        obraRepository.save(obra);

        LogAcao log = new LogAcao();
        log.setEmailUsuario(emailAdmin);
        log.setAcao("SAIDA_OBRA");
        log.setDetalhes("Saída da obra: " + obra.getTitulo() + " | Total saídas: " + obra.getQuantidadeSaidas());
        logAcaoRepository.save(log);

        return obra;
    }


    public Obra registrarDevolucao(Long id, String emailAdmin) {
        Obra obra = obraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Obra não encontrada"));

        obra.setStatus("DISPONIVEL");
        obraRepository.save(obra);

        LogAcao log = new LogAcao();
        log.setEmailUsuario(emailAdmin);
        log.setAcao("DEVOLUCAO_OBRA");
        log.setDetalhes("Devolução da obra: " + obra.getTitulo());
        logAcaoRepository.save(log);

        return obra;
    }

    public List<Obra> maisAcessadas() {
        return obraRepository.findTop5ByOrderByContadorBuscasDesc();
    }
}
