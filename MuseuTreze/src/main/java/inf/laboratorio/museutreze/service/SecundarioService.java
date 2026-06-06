package inf.laboratorio.museutreze.service;

import inf.laboratorio.museutreze.dto.SecundarioDTORequest;
import inf.laboratorio.museutreze.dto.SecundarioDTOResponse;
import inf.laboratorio.museutreze.model.Autor;
import inf.laboratorio.museutreze.model.Obra;
import inf.laboratorio.museutreze.model.Secundario;
import inf.laboratorio.museutreze.repository.SecundarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecundarioService {
    private final SecundarioRepository secundarioRepository;

    public SecundarioService(SecundarioRepository secundarioRepository) {
        this.secundarioRepository = secundarioRepository;
    }

    public SecundarioDTOResponse salvar(SecundarioDTORequest secundarioDTO) {
        Secundario secundario = new Secundario();
        Obra obra = new Obra();
        Autor autor = new Autor();
        obra.setId(secundarioDTO.obraId());
        autor.setId(secundarioDTO.autorId());
        secundarioRepository.save(secundario);
        return new SecundarioDTOResponse(secundario.getId(), secundario.getObraId().getId(), secundario.getObraId().getTitulo_Principal(), secundario.getAutorId().getId(), secundario.getAutorId().getNome());
    }

    public List<SecundarioDTOResponse> listar() {
        List<Secundario> secundarios = secundarioRepository.findAll();
        return secundarios.stream().map(secundario -> new SecundarioDTOResponse(secundario.getId(), secundario.getObraId().getId(), secundario.getObraId().getTitulo_Principal(), secundario.getAutorId().getId(), secundario.getAutorId().getNome())).toList();
    }

    public SecundarioDTOResponse atualizar(Long id, SecundarioDTORequest secundarioDTO) {
        Secundario secundario = secundarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Autor não encontrado!"));
        Obra obra = new Obra();
        Autor autor = new Autor();
        obra.setId(secundarioDTO.obraId());
        autor.setId(secundarioDTO.autorId());
        secundarioRepository.save(secundario);
        return new SecundarioDTOResponse(secundario.getId(), secundario.getObraId().getId(), secundario.getObraId().getTitulo_Principal(), secundario.getAutorId().getId(), secundario.getAutorId().getNome());
    }

    public void deletar(Long id) {
        Secundario secundario = secundarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Autor não encontrado!"));
        secundarioRepository.delete(secundario);
    }
}
