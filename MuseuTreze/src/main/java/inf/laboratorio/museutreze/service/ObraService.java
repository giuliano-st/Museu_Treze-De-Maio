package inf.laboratorio.museutreze.service;

import inf.laboratorio.museutreze.dto.ObraDTORequest;
import inf.laboratorio.museutreze.dto.ObraDTOResponse;
import inf.laboratorio.museutreze.mapper.ObraMapper;
import inf.laboratorio.museutreze.model.Assunto;
import inf.laboratorio.museutreze.model.Autor;
import inf.laboratorio.museutreze.model.Editora;
import inf.laboratorio.museutreze.model.Obra;
import inf.laboratorio.museutreze.repository.AssuntoRepository;
import inf.laboratorio.museutreze.repository.AutorRepository;
import inf.laboratorio.museutreze.repository.EditoraRepository;
import inf.laboratorio.museutreze.repository.ObraRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ObraService {

    private final ObraRepository obraRepository;
    private final AutorRepository autorRepository;
    private final EditoraRepository editoraRepository;
    private final AssuntoRepository assuntoRepository;
    private final ObraMapper obraMapper;

    public ObraService(ObraRepository obraRepository, AutorRepository autorRepository,
                       EditoraRepository editoraRepository, AssuntoRepository assuntoRepository,
                       ObraMapper obraMapper) {
        this.obraRepository = obraRepository;
        this.autorRepository = autorRepository;
        this.editoraRepository = editoraRepository;
        this.assuntoRepository = assuntoRepository;
        this.obraMapper = obraMapper;
    }

    public ObraDTOResponse salvar(ObraDTORequest obraDTO) {
        Autor autor = resolverAutor(obraDTO.autorId());
        Editora editora = resolverEditora(obraDTO.editoraId());
        List<Assunto> assuntos = resolverAssuntos(obraDTO.assuntosIds());

        // Usa o mapper — sem duplicação de mapeamento manual
        Obra obra = obraMapper.toEntity(obraDTO, autor, editora, assuntos);
        obraRepository.save(obra);

        return obraMapper.toResponse(obra);
    }

    public List<ObraDTOResponse> listar() {
        return obraRepository.findAll().stream()
                .map(obraMapper::toResponse)
                .toList();
    }

    public ObraDTOResponse buscarPorId(Long id) {
        Obra obra = obraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Obra não encontrada!"));
        return obraMapper.toResponse(obra);
    }

    public ObraDTOResponse atualizar(Long id, ObraDTORequest obraDTO) {
        Obra obra = obraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Obra não encontrada!"));

        Autor autor = resolverAutor(obraDTO.autorId());
        Editora editora = resolverEditora(obraDTO.editoraId());
        List<Assunto> assuntos = resolverAssuntos(obraDTO.assuntosIds());

        // Atualiza os campos usando o mapper como referência, mas preservando o ID
        obra.setObra_tipo(obraDTO.obra_tipo());
        obra.setTitulo_Principal(obraDTO.titulo_Principal());
        obra.setCapa(obraDTO.capa());
        obra.setLocal(obraDTO.local());
        obra.setData(obraDTO.data());
        obra.setDescFisica(obraDTO.descFisica());
        obra.setNome(obraDTO.nome());
        obra.setNumeroChamada(obraDTO.numeroChamada());
        obra.setChamadaLocal(obraDTO.chamadaLocal());
        obra.setTituloUniforme(obraDTO.tituloUniforme());
        obra.setIsbn(obraDTO.isbn());
        obra.setSerie(obraDTO.serie());
        obra.setEdicao(obraDTO.edicao());
        obra.setColecao(obraDTO.colecao());
        obra.setNotasGerais(obraDTO.notasGerais());
        obra.setIssn(obraDTO.issn());
        obra.setVolume(obraDTO.volume());
        obra.setPeriodicidade(obraDTO.periodicidade());
        obra.setAutor(autor);
        obra.setEditora(editora);
        obra.setAssuntos(assuntos);
        obraRepository.save(obra);

        return obraMapper.toResponse(obra);
    }

    public void deletar(Long id) {
        Obra obra = obraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Obra não encontrada!"));
        obraRepository.delete(obra);
    }

    // ── Helpers privados ──────────────────────────────────────────────────────

    private Autor resolverAutor(Long autorId) {
        return autorId != null
                ? autorRepository.findById(autorId).orElse(null)
                : null;
    }

    private Editora resolverEditora(Long editoraId) {
        return editoraId != null
                ? editoraRepository.findById(editoraId).orElse(null)
                : null;
    }

    private List<Assunto> resolverAssuntos(List<Long> ids) {
        return ids != null
                ? new ArrayList<>(assuntoRepository.findAllById(ids))
                : new ArrayList<>();
    }
}
