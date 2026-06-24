package inf.laboratorio.museutreze.service;

import inf.laboratorio.museutreze.dto.SecundarioDTORequest;
import inf.laboratorio.museutreze.dto.SecundarioDTOResponse;
import inf.laboratorio.museutreze.model.Autor;
import inf.laboratorio.museutreze.model.Obra;
import inf.laboratorio.museutreze.model.Secundario;
import inf.laboratorio.museutreze.repository.AutorRepository;
import inf.laboratorio.museutreze.repository.ObraRepository;
import inf.laboratorio.museutreze.repository.SecundarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecundarioService {
    private final SecundarioRepository secundarioRepository;
    private final ObraRepository obraRepository;
    private final AutorRepository autorRepository;

    public SecundarioService(SecundarioRepository secundarioRepository,
                             ObraRepository obraRepository,
                             AutorRepository autorRepository) {
        this.secundarioRepository = secundarioRepository;
        this.obraRepository = obraRepository;
        this.autorRepository = autorRepository;
    }

    public SecundarioDTOResponse salvar(SecundarioDTORequest secundarioDTO) {
        Secundario secundario = new Secundario();

        Obra obra = obraRepository.findById(secundarioDTO.obraId())
                .orElseThrow(() -> new RuntimeException("Obra não encontrada!"));
        Autor autor = autorRepository.findById(secundarioDTO.autorId())
                .orElseThrow(() -> new RuntimeException("Autor não encontrado!"));

        secundario.setObraId(obra);
        secundario.setAutorId(autor);
        Secundario salvo = secundarioRepository.save(secundario);

        return new SecundarioDTOResponse(
                salvo.getId(),
                salvo.getObraId().getId(),
                salvo.getObraId().getTitulo_Principal(),
                salvo.getAutorId().getId(),
                salvo.getAutorId().getNome()
        );
    }

    public SecundarioDTOResponse buscarPorId(Long id) {
        Secundario secundario = secundarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro secundário não encontrado!"));
        return new SecundarioDTOResponse(
                secundario.getId(),
                secundario.getObraId().getId(),
                secundario.getObraId().getTitulo_Principal(),
                secundario.getAutorId().getId(),
                secundario.getAutorId().getNome()
        );
    }

    public List<SecundarioDTOResponse> listar() {
        List<Secundario> secundarios = secundarioRepository.findAll();
        return secundarios.stream().map(secundario -> new SecundarioDTOResponse(
                secundario.getId(),
                secundario.getObraId().getId(),
                secundario.getObraId().getTitulo_Principal(),
                secundario.getAutorId().getId(),
                secundario.getAutorId().getNome()
        )).toList();
    }

    public SecundarioDTOResponse atualizar(Long id, SecundarioDTORequest secundarioDTO) {
        Secundario secundario = secundarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro secundário não encontrado!"));

        Obra obra = obraRepository.findById(secundarioDTO.obraId())
                .orElseThrow(() -> new RuntimeException("Obra não encontrada!"));
        Autor autor = autorRepository.findById(secundarioDTO.autorId())
                .orElseThrow(() -> new RuntimeException("Autor não encontrado!"));

        secundario.setObraId(obra);
        secundario.setAutorId(autor);

        Secundario atualizado = secundarioRepository.save(secundario);

        return new SecundarioDTOResponse(
                atualizado.getId(),
                atualizado.getObraId().getId(),
                atualizado.getObraId().getTitulo_Principal(),
                atualizado.getAutorId().getId(),
                atualizado.getAutorId().getNome()
        );
    }

    public void deletar(Long id) {
        Secundario secundario = secundarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro secundário não encontrado!"));
        secundarioRepository.delete(secundario);
    }
}