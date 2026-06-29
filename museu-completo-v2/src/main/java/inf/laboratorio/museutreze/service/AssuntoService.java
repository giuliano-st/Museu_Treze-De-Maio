package inf.laboratorio.museutreze.service;

import inf.laboratorio.museutreze.dto.AssuntoDTORequest;
import inf.laboratorio.museutreze.dto.AssuntoDTOResponse;
import inf.laboratorio.museutreze.model.Assunto;
import inf.laboratorio.museutreze.model.Obra;
import inf.laboratorio.museutreze.repository.AssuntoRepository;
import inf.laboratorio.museutreze.repository.ObraRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AssuntoService {
    private final AssuntoRepository assuntoRepository;
    private final ObraRepository obraRepository;

    public AssuntoService(AssuntoRepository assuntoRepository, ObraRepository obraRepository) {
        this.assuntoRepository = assuntoRepository;
        this.obraRepository = obraRepository;
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

    /**
     * Exclui o assunto e remove o vínculo dele de qualquer obra que o usa
     * (sem excluir a obra e sem precisar de fallback, já que uma obra
     * pode ter outros assuntos ou nenhum).
     */
    public void deletar(Long id){
        Assunto assunto = assuntoRepository.findById(id).orElseThrow(() -> new RuntimeException("Assunto não encontrado!"));

        List<Obra> obras = obraRepository.findAll().stream()
                .filter(obra -> obra.getAssuntos() != null && obra.getAssuntos().contains(assunto))
                .toList();

        for (Obra obra : obras) {
            obra.getAssuntos().remove(assunto);
        }
        obraRepository.saveAll(obras);

        assuntoRepository.delete(assunto);
    }
}