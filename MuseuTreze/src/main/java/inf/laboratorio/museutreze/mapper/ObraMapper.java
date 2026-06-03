package inf.laboratorio.museutreze.mapper;

import inf.laboratorio.museutreze.dto.ObraDTORequest;
import inf.laboratorio.museutreze.dto.ObraDTOResponse;
import inf.laboratorio.museutreze.model.Assunto;
import inf.laboratorio.museutreze.model.Autor;
import inf.laboratorio.museutreze.model.Editora;
import inf.laboratorio.museutreze.model.Obra;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ObraMapper {

    private final AutorMapper autorMapper = new AutorMapper();
    private final EditoraMapper editoraMapper = new EditoraMapper();
    private final AssuntoMapper assuntoMapper = new AssuntoMapper();

    public Obra toEntity(ObraDTORequest request, Autor autor, Editora editora, List<Assunto> assuntos) {
        if (request == null) return null;
        Obra obra = new Obra();
        obra.setObra_tipo(request.obra_tipo());
        obra.setTitulo_Principal(request.titulo_Principal());
        obra.setCapa(request.capa());
        obra.setLocal(request.local());
        obra.setData(request.data());
        obra.setDescFisica(request.descFisica());
        obra.setNome(request.nome());
        obra.setNumeroChamada(request.numeroChamada());
        obra.setChamadaLocal(request.chamadaLocal());
        obra.setTituloUniforme(request.tituloUniforme());
        obra.setIsbn(request.isbn());
        obra.setSerie(request.serie());
        obra.setEdicao(request.edicao());
        obra.setColecao(request.colecao());
        obra.setNotasGerais(request.notasGerais());
        obra.setIssn(request.issn());
        obra.setVolume(request.volume());
        obra.setPeriodicidade(request.periodicidade());
        obra.setAutor(autor);
        obra.setEditora(editora);
        obra.setAssuntos(assuntos);
        return obra;
    }

    public ObraDTOResponse toResponse(Obra obra) {
        if (obra == null) return null;

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
                obra.getAutor() != null ? autorMapper.toResponse(obra.getAutor()) : null,
                obra.getEditora() != null ? editoraMapper.toResponse(obra.getEditora()) : null,
                obra.getAssuntos() != null ? obra.getAssuntos().stream()
                        .map(assuntoMapper::toResponse)
                        .collect(Collectors.toList()) : null
        );
    }
}