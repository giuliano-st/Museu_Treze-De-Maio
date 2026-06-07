package inf.laboratorio.museutreze.service;

import inf.laboratorio.museutreze.dto.ObraDTORequest;
import inf.laboratorio.museutreze.dto.ObraDTOResponse;
import inf.laboratorio.museutreze.dto.AutorDTOResponse;
import inf.laboratorio.museutreze.dto.EditoraDTOResponse;
import inf.laboratorio.museutreze.dto.AssuntoDTOResponse;
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

    public ObraService(ObraRepository obraRepository, AutorRepository autorRepository,
                       EditoraRepository editoraRepository, AssuntoRepository assuntoRepository) {
        this.obraRepository = obraRepository;
        this.autorRepository = autorRepository;
        this.editoraRepository = editoraRepository;
        this.assuntoRepository = assuntoRepository;
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

        return toResponse(obra);
    }

    public List<ObraDTOResponse> listar() {
        return obraRepository.findAll().stream().map(this::toResponse).toList();
    }

    public ObraDTOResponse buscarPorId(Long id) {
        Obra obra = obraRepository.findById(id).orElseThrow(() -> new RuntimeException("Obra não encontrada!"));
        return toResponse(obra);
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

        return toResponse(obra);
    }

    public void deletar(Long id) {
        Obra obra = obraRepository.findById(id).orElseThrow(() -> new RuntimeException("Obra não encontrada!"));
        obraRepository.delete(obra);
    }

    private ObraDTOResponse toResponse(Obra obra) {
        AutorDTOResponse autorResponse = obra.getAutor() != null
                ? new AutorDTOResponse(obra.getAutor().getId(), obra.getAutor().getNome(), obra.getAutor().getNacionalidade())
                : null;

        EditoraDTOResponse editoraResponse = obra.getEditora() != null
                ? new EditoraDTOResponse(obra.getEditora().getId(), obra.getEditora().getNome())
                : null;

        List<AssuntoDTOResponse> assuntosResponse = obra.getAssuntos() != null
                ? obra.getAssuntos().stream().map(a -> new AssuntoDTOResponse(a.getId(), a.getDescricao())).toList()
                : List.of();

        return new ObraDTOResponse(
                obra.getId(),
                obra.getObra_tipo(),
                obra.getTitulo_Principal(),
                obra.getCapa(),
                obra.getLocal(),
                obra.getData(),
                obra.getDescFisica(),
                obra.getNome(),
                obra.getNumeroChamada(),
                obra.getChamadaLocal(),
                obra.getTituloUniforme(),
                obra.getIsbn(),
                obra.getSerie(),
                obra.getEdicao(),
                obra.getColecao(),
                obra.getNotasGerais(),
                obra.getIssn(),
                obra.getVolume(),
                obra.getPeriodicidade(),
                autorResponse,
                editoraResponse,
                assuntosResponse
        );
    }
}