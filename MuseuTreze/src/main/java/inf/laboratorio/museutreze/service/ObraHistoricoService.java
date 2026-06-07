package inf.laboratorio.museutreze.service;

import inf.laboratorio.museutreze.dto.ObraHistoricoDTORequest;
import inf.laboratorio.museutreze.dto.ObraHistoricoDTOResponse;
import inf.laboratorio.museutreze.model.Obra;
import inf.laboratorio.museutreze.model.ObraHistorico;
import inf.laboratorio.museutreze.model.Usuario;
import inf.laboratorio.museutreze.repository.ObraHistoricoRepository;
import inf.laboratorio.museutreze.repository.ObraRepository;
import inf.laboratorio.museutreze.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ObraHistoricoService {

    private final ObraHistoricoRepository obraHistoricoRepository;
    private final ObraRepository obraRepository;
    private final UsuarioRepository usuarioRepository;

    public ObraHistoricoService(ObraHistoricoRepository obraHistoricoRepository,
                                ObraRepository obraRepository,
                                UsuarioRepository usuarioRepository) {
        this.obraHistoricoRepository = obraHistoricoRepository;
        this.obraRepository = obraRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public ObraHistoricoDTOResponse salvar(ObraHistoricoDTORequest obraHistoricoDTO) {
        Usuario usuario = usuarioRepository.findById(obraHistoricoDTO.usuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));
        Obra obra = obraRepository.findById(obraHistoricoDTO.obraId())
                .orElseThrow(() -> new RuntimeException("Obra não encontrada!"));

        ObraHistorico obraHistorico = new ObraHistorico();
        obraHistorico.setOperacao(obraHistoricoDTO.operacao());
        obraHistorico.setData(LocalDateTime.now());
        obraHistorico.setUsuario(usuario);
        obraHistorico.setObra(obra);

        obraHistoricoRepository.save(obraHistorico);

        return new ObraHistoricoDTOResponse(
                obraHistorico.getId(),
                obraHistorico.getOperacao(),
                obraHistorico.getData(),
                obraHistorico.getUsuario().getId(),
                obraHistorico.getUsuario().getNomeUsuario(),
                obraHistorico.getObra().getId(),
                obraHistorico.getObra().getTitulo_Principal()
        );
    }

    public List<ObraHistoricoDTOResponse> listar() {
        List<ObraHistorico> historicos = obraHistoricoRepository.findAll();
        return historicos.stream().map(obraHistorico -> new ObraHistoricoDTOResponse(
                obraHistorico.getId(),
                obraHistorico.getOperacao(),
                obraHistorico.getData(),
                obraHistorico.getUsuario() != null ? obraHistorico.getUsuario().getId() : null,
                obraHistorico.getUsuario() != null ? obraHistorico.getUsuario().getNomeUsuario() : "Usuário deletado/desconhecido",
                obraHistorico.getObra() != null ? obraHistorico.getObra().getId() : null,
                obraHistorico.getObra() != null ? obraHistorico.getObra().getTitulo_Principal() : "Obra deletada/desconhecida"
        )).toList();
    }

    public ObraHistoricoDTOResponse atualizar(Long id, ObraHistoricoDTORequest obraHistoricoDTO) {
        ObraHistorico obraHistorico = obraHistoricoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Histórico não encontrado!"));

        Usuario usuario = usuarioRepository.findById(obraHistoricoDTO.usuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));
        Obra obra = obraRepository.findById(obraHistoricoDTO.obraId())
                .orElseThrow(() -> new RuntimeException("Obra não encontrada!"));

        obraHistorico.setOperacao(obraHistoricoDTO.operacao());
        obraHistorico.setUsuario(usuario);
        obraHistorico.setObra(obra);

        obraHistoricoRepository.save(obraHistorico);

        return new ObraHistoricoDTOResponse(
                obraHistorico.getId(),
                obraHistorico.getOperacao(),
                obraHistorico.getData(),
                obraHistorico.getUsuario().getId(),
                obraHistorico.getUsuario().getNomeUsuario(),
                obraHistorico.getObra().getId(),
                obraHistorico.getObra().getTitulo_Principal()
        );
    }

    public void deletar(Long id) {
        ObraHistorico obraHistorico = obraHistoricoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Histórico não encontrado!"));
        obraHistoricoRepository.delete(obraHistorico);
    }
}