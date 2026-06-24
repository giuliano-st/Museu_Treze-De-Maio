package inf.laboratorio.museutreze.service;

import inf.laboratorio.museutreze.dto.AssuntoDTORequest;
import inf.laboratorio.museutreze.dto.AssuntoDTOResponse;
import inf.laboratorio.museutreze.model.Assunto;
import inf.laboratorio.museutreze.repository.AssuntoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AssuntoService {
    private final AssuntoRepository assuntoRepository;

    public AssuntoService(AssuntoRepository assuntoRepository) {
        this.assuntoRepository = assuntoRepository;
    }

    public AssuntoDTOResponse salvar(AssuntoDTORequest assuntoDTO){
        Assunto assunto = new Assunto();
        assunto.setDescricao(assuntoDTO.descricao());
        assuntoRepository.save(assunto);
        return new AssuntoDTOResponse(
                assunto.getId(),
                assunto.getDescricao()
        );
    }

    public List<AssuntoDTOResponse> salvarLista(List<AssuntoDTORequest> assuntosDTO) {
        List<AssuntoDTOResponse> responses = new ArrayList<>();
        for (AssuntoDTORequest dto : assuntosDTO) {
            responses.add(salvar(dto));
        }
        return responses;
    }

    public AssuntoDTOResponse buscarPorId(Long id){
        Assunto assunto = assuntoRepository.findById(id).orElseThrow(() -> new RuntimeException("Assunto não encontrado!"));
        return new AssuntoDTOResponse(
                assunto.getId(),
                assunto.getDescricao()
        );
    }

    public List<AssuntoDTOResponse> listar(){
        List<Assunto> assuntos = assuntoRepository.findAll();
        return assuntos.stream().map(assunto -> new AssuntoDTOResponse(assunto.getId(), assunto.getDescricao())).toList();
    }

    public AssuntoDTOResponse atualizar(Long id, AssuntoDTORequest assuntoDTO){
        Assunto assunto = assuntoRepository.findById(id).orElseThrow(() -> new RuntimeException("Assunto não encontrado!"));
        assunto.setDescricao(assuntoDTO.descricao());
        assuntoRepository.save(assunto);
        return new AssuntoDTOResponse(assunto.getId(), assunto.getDescricao());
    }

    public void deletar(Long id){
        Assunto assunto = assuntoRepository.findById(id).orElseThrow(() -> new RuntimeException("Assunto não encontrado!"));
        assuntoRepository.delete(assunto);
    }
}
