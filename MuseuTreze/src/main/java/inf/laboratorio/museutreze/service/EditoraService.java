package inf.laboratorio.museutreze.service;

import inf.laboratorio.museutreze.dto.EditoraDTORequest;
import inf.laboratorio.museutreze.dto.EditoraDTOResponse;
import inf.laboratorio.museutreze.model.Editora;
import inf.laboratorio.museutreze.repository.EditoraRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EditoraService {
    private final EditoraRepository editoraRepository;

    public EditoraService(EditoraRepository editoraRepository) {
        this.editoraRepository = editoraRepository;
    }

    public EditoraDTOResponse salvar(EditoraDTORequest editoraDTO) {
        Editora editora = new Editora();
        editora.setNome(editoraDTO.nome());
        editoraRepository.save(editora);
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

    public void deletar(Long id) {
        Editora editora = editoraRepository.findById(id).orElseThrow(() -> new RuntimeException("Editora inexistente!"));
        editoraRepository.delete(editora);
    }
}
