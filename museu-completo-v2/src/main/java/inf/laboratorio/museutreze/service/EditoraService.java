package inf.laboratorio.museutreze.service;

import inf.laboratorio.museutreze.dto.EditoraDTORequest;
import inf.laboratorio.museutreze.dto.EditoraDTOResponse;
import inf.laboratorio.museutreze.model.Editora;
import inf.laboratorio.museutreze.model.Obra;
import inf.laboratorio.museutreze.repository.EditoraRepository;
import inf.laboratorio.museutreze.repository.ObraRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EditoraService {

    private static final String NOME_EDITORA_DESCONHECIDA = "Editora desconhecida";

    private final EditoraRepository editoraRepository;
    private final ObraRepository obraRepository;

    public EditoraService(EditoraRepository editoraRepository, ObraRepository obraRepository) {
        this.editoraRepository = editoraRepository;
        this.obraRepository = obraRepository;
    }

    public EditoraDTOResponse salvar(EditoraDTORequest editoraDTO) {
        Editora editora = new Editora();
        editora.setNome(editoraDTO.nome());
        editoraRepository.save(editora);
        return new EditoraDTOResponse(editora.getId(),  editora.getNome());
    }

    public List<EditoraDTOResponse> salvarLista(List<EditoraDTORequest> editorasDTO) {
        List<EditoraDTOResponse> responses = new ArrayList<>();
        for (EditoraDTORequest dto : editorasDTO) {
            responses.add(salvar(dto));
        }
        return responses;
    }

    public EditoraDTOResponse buscarPorId(Long id) {
        Editora editora = editoraRepository.findById(id).orElseThrow(() -> new RuntimeException("Editora inexistente!"));
        return new EditoraDTOResponse(editora.getId(),  editora.getNome());
    }

    public List<EditoraDTOResponse> listar() {
        List<Editora> editoras = editoraRepository.findAll();
        return editoras.stream().map(editora -> new EditoraDTOResponse(editora.getId(), editora.getNome())).toList();
    }

    public EditoraDTOResponse atualizar(Long id, EditoraDTORequest editoraDTO) {
        Editora editora = editoraRepository.findById(id).orElseThrow(() -> new RuntimeException("Editora inexistente!"));
        editora.setNome(editoraDTO.nome());
        editoraRepository.save(editora);
        return new EditoraDTOResponse(editora.getId(), editora.getNome());
    }

    /**
     * Exclui a editora SEM excluir as obras associadas (zero cascade delete).
     * As obras vinculadas são reatribuídas automaticamente para a editora
     * genérica "Editora desconhecida" antes da exclusão.
     */
    public void deletar(Long id) {
        Editora editora = editoraRepository.findById(id).orElseThrow(() -> new RuntimeException("Editora inexistente!"));

        Editora editoraDesconhecida = buscarOuCriarEditoraDesconhecida();

        if (!editora.getId().equals(editoraDesconhecida.getId())) {
            List<Obra> obrasDaEditora = obraRepository.findByEditoraId(editora.getId());
            for (Obra obra : obrasDaEditora) {
                obra.setEditora(editoraDesconhecida);
            }
            obraRepository.saveAll(obrasDaEditora);
        }

        editoraRepository.delete(editora);
    }

    private Editora buscarOuCriarEditoraDesconhecida() {
        Editora existente = editoraRepository.findByNomeIgnoreCase(NOME_EDITORA_DESCONHECIDA);
        if (existente != null) {
            return existente;
        }
        Editora desconhecida = new Editora();
        desconhecida.setNome(NOME_EDITORA_DESCONHECIDA);
        return editoraRepository.save(desconhecida);
    }
}