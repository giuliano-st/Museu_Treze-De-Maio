package inf.laboratorio.museutreze.service;

import inf.laboratorio.museutreze.dto.ObraHistoricoDTOResponse;
import inf.laboratorio.museutreze.model.Obra;
import inf.laboratorio.museutreze.model.ObraHistorico;
import inf.laboratorio.museutreze.model.Usuario;
import inf.laboratorio.museutreze.repository.ObraHistoricoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ObraHistoricoService {
    private final ObraHistoricoRepository obraHistoricoRepository;

    public ObraHistoricoService(ObraHistoricoRepository obraHistoricoRepository) {
        this.obraHistoricoRepository = obraHistoricoRepository;
    }

    public ObraHistoricoDTOResponse salvar(ObraHistoricoDTOResponse obraHistoricoDTO) {
        ObraHistorico obraHistorico = new ObraHistorico();
        Obra obra = new Obra();
        Usuario usuario = new Usuario();
        usuario.setId(obraHistoricoDTO.usuarioId());
        usuario.setNomeUsuario(obraHistoricoDTO.nomeUsuario());
        obra.setId(obraHistoricoDTO.obraId());
        obra.setTitulo_Principal(obraHistoricoDTO.obraTitulo());

        obraHistorico.setOperacao(obraHistoricoDTO.operacao());
        obraHistorico.setData(obraHistoricoDTO.data());
        obraHistorico.setUsuario(usuario);
        obraHistorico.setObra(obra);
        obraHistoricoRepository.save(obraHistorico);
        return new ObraHistoricoDTOResponse(obraHistorico.getId(), obraHistorico.getOperacao(), obraHistorico.getData(), obraHistorico.getUsuario().getId(), obraHistorico.getUsuario().getNomeUsuario(), obraHistorico.getObra().getId(), obraHistorico.getObra().getTitulo_Principal());
    }

    public List<ObraHistoricoDTOResponse> listar() {
        List<ObraHistorico> historicos = obraHistoricoRepository.findAll();
        return historicos.stream().map(obraHistorico -> new ObraHistoricoDTOResponse(obraHistorico.getId(), obraHistorico.getOperacao(), obraHistorico.getData(), obraHistorico.getUsuario().getId(), obraHistorico.getUsuario().getNomeUsuario(), obraHistorico.getObra().getId(), obraHistorico.getObra().getTitulo_Principal())).toList();
    }

    public ObraHistoricoDTOResponse atualizar(Long id, ObraHistoricoDTOResponse obraHistoricoDTO) {
        ObraHistorico obraHistorico = obraHistoricoRepository.findById(id).orElseThrow(() -> new RuntimeException("Historico não encontrado!"));
        Obra obra = new Obra();
        Usuario usuario = new Usuario();
        usuario.setId(obraHistoricoDTO.usuarioId());
        usuario.setNomeUsuario(obraHistoricoDTO.nomeUsuario());
        obra.setId(obraHistoricoDTO.obraId());
        obra.setTitulo_Principal(obraHistoricoDTO.obraTitulo());

        obraHistorico.setOperacao(obraHistoricoDTO.operacao());
        obraHistorico.setData(obraHistoricoDTO.data());
        obraHistorico.setUsuario(usuario);
        obraHistorico.setObra(obra);
        obraHistoricoRepository.save(obraHistorico);
        return new ObraHistoricoDTOResponse(obraHistorico.getId(), obraHistorico.getOperacao(), obraHistorico.getData(), obraHistorico.getUsuario().getId(), obraHistorico.getUsuario().getNomeUsuario(), obraHistorico.getObra().getId(), obraHistorico.getObra().getTitulo_Principal());
    }

    public void deletar(Long id) {
        ObraHistorico obraHistorico = obraHistoricoRepository.findById(id).orElseThrow(() -> new RuntimeException("Historico não encontrado!"));
        obraHistoricoRepository.delete(obraHistorico);
    }
}
