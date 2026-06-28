package inf.laboratorio.museutreze.service;

import inf.laboratorio.museutreze.dto.ObraDTORequest;
import inf.laboratorio.museutreze.dto.ObraDTOResponse;
import inf.laboratorio.museutreze.dto.AutorDTOResponse;
import inf.laboratorio.museutreze.dto.EditoraDTOResponse;
import inf.laboratorio.museutreze.dto.AssuntoDTOResponse;
import inf.laboratorio.museutreze.mapper.ObraMapper;
import inf.laboratorio.museutreze.model.Assunto;
import inf.laboratorio.museutreze.model.Autor;
import inf.laboratorio.museutreze.model.Editora;
import inf.laboratorio.museutreze.model.Obra;
import inf.laboratorio.museutreze.repository.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ObraService {

    private final ObraRepository obraRepository;
    private final AutorRepository autorRepository;
    private final EditoraRepository editoraRepository;
    private final AssuntoRepository assuntoRepository;
    private final ExemplarRepository exemplarRepository;
    private final SecundarioRepository secundarioRepository;
    private final ObraMapper obraMapper;

    public ObraService(ObraRepository obraRepository, AutorRepository autorRepository,
                       EditoraRepository editoraRepository, AssuntoRepository assuntoRepository, ExemplarRepository exemplarRepository, SecundarioRepository secundarioRepository, ObraMapper obraMapper) {
        this.obraRepository = obraRepository;
        this.autorRepository = autorRepository;
        this.editoraRepository = editoraRepository;
        this.assuntoRepository = assuntoRepository;
        this.exemplarRepository = exemplarRepository;
        this.secundarioRepository = secundarioRepository;
        this.obraMapper = obraMapper;
    }

    public ObraDTOResponse salvar(ObraDTORequest obraDTO) {
        Autor autor = obraDTO.autorId() != null ? autorRepository.findById(obraDTO.autorId()).orElse(null) : null;
        Editora editora = obraDTO.editoraId() != null ? editoraRepository.findById(obraDTO.editoraId()).orElse(null) : null;

        List<Assunto> assuntos = obraDTO.assuntosIds() != null
                ? new ArrayList<>(assuntoRepository.findAllById(obraDTO.assuntosIds()))
                : new ArrayList<>();

        Obra obra = new Obra();
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

    public List<ObraDTOResponse> salvarLista(List<ObraDTORequest> obrasDTO) {
        List<ObraDTOResponse> responses = new ArrayList<>();
        for (ObraDTORequest dto : obrasDTO) {
            responses.add(salvar(dto));
        }
        return responses;
    }

    public List<ObraDTOResponse> listar() {
        return obraRepository.findAll().stream().map(obraMapper::toResponse).toList();
    }

    public ObraDTOResponse buscarPorId(Long id) {
        Obra obra = obraRepository.findById(id).orElseThrow(() -> new RuntimeException("Obra não encontrada!"));
        return obraMapper.toResponse(obra);
    }

    public ObraDTOResponse atualizar(Long id, ObraDTORequest obraDTO) {
        Obra obra = obraRepository.findById(id).orElseThrow(() -> new RuntimeException("Obra não encontrada!"));
        Autor autor = obraDTO.autorId() != null ? autorRepository.findById(obraDTO.autorId()).orElse(null) : null;
        Editora editora = obraDTO.editoraId() != null ? editoraRepository.findById(obraDTO.editoraId()).orElse(null) : null;

        List<Assunto> assuntos = obraDTO.assuntosIds() != null
                ? new ArrayList<>(assuntoRepository.findAllById(obraDTO.assuntosIds()))
                : new ArrayList<>();

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

    // Mudei o deletar(): adicionei a remoção dos registros de Secundario vinculados à obra
    // ANTES de deletar a obra. Isso é necessário porque a tabela secundario tem uma FK pra
    // obras (obra_id), e o banco rejeita o delete da obra enquanto existir um secundario
    // apontando pra ela (erro de FK constraint). Fiz isso pra resolver esse erro.
    // Ass: Mribas
    public void deletar(Long id) {
        Obra obra = obraRepository.findById(id).orElseThrow(() -> new RuntimeException("Obra não encontrada!"));
        exemplarRepository.deleteAll(exemplarRepository.findByObraId(id));
        secundarioRepository.deleteAll(secundarioRepository.findAllByObraId(obra));
        obraRepository.delete(obra);
    }
}