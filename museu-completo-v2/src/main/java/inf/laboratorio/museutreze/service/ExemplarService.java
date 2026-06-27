package inf.laboratorio.museutreze.service;

import inf.laboratorio.museutreze.dto.ExemplarDTORequest;
import inf.laboratorio.museutreze.dto.ExemplarDTOResponse;
import inf.laboratorio.museutreze.model.Exemplar;
import inf.laboratorio.museutreze.model.Obra;
import inf.laboratorio.museutreze.repository.ExemplarRepository;
import inf.laboratorio.museutreze.repository.ObraRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExemplarService {
    private final ExemplarRepository exemplarRepository;
    private final ObraRepository obraRepository;

    public ExemplarService(ExemplarRepository exemplarRepository, ObraRepository obraRepository) {
        this.exemplarRepository = exemplarRepository;
        this.obraRepository = obraRepository;
    }

    public ExemplarDTOResponse salvar(ExemplarDTORequest exemplarDTO) {
        Exemplar exemplar = new Exemplar();
        Obra obra = obraRepository.findById(exemplarDTO.obraId())
                .orElseThrow(() -> new RuntimeException("Obra não encontrada!"));

        exemplar.setDisponibilidade(exemplarDTO.disponibilidade());
        exemplar.setNumero(exemplarDTO.numero());
        exemplar.setObra(obra);
        exemplarRepository.save(exemplar);

        return new ExemplarDTOResponse(
                exemplar.getId(),
                exemplar.getDisponibilidade(),
                exemplar.getNumero(),
                exemplar.getObra().getId(),
                exemplar.getObra().getTitulo_Principal()
        );
    }

    public List<ExemplarDTOResponse> salvarLista(List<ExemplarDTORequest> exemplaresDTO) {
        List<ExemplarDTOResponse> responses = new ArrayList<>();
        for (ExemplarDTORequest dto : exemplaresDTO) {
            responses.add(salvar(dto));
        }
        return responses;
    }

    public ExemplarDTOResponse buscarPorId(Long id) {
        Exemplar exemplar = exemplarRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exemplar não encontrado!"));

        return new ExemplarDTOResponse(
                exemplar.getId(),
                exemplar.getDisponibilidade(),
                exemplar.getNumero(),
                exemplar.getObra().getId(),
                exemplar.getObra().getTitulo_Principal()
        );
    }

    public List<ExemplarDTOResponse> listar() {
        List<Exemplar> exemplares = exemplarRepository.findAll();
        return exemplares.stream().map(exemplar -> new ExemplarDTOResponse(
                exemplar.getId(),
                exemplar.getDisponibilidade(),
                exemplar.getNumero(),
                exemplar.getObra() != null ? exemplar.getObra().getId() : null,
                exemplar.getObra() != null ? exemplar.getObra().getTitulo_Principal() : "Obra não vinculada"
        )).toList();
    }

    public ExemplarDTOResponse atualizar(Long id, ExemplarDTORequest exemplarDTO) {
        Exemplar exemplar = exemplarRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exemplar não encontrado!"));
        Obra obra = obraRepository.findById(exemplarDTO.obraId())
                .orElseThrow(() -> new RuntimeException("Obra não encontrada!"));

        exemplar.setDisponibilidade(exemplarDTO.disponibilidade());
        exemplar.setNumero(exemplarDTO.numero());
        exemplar.setObra(obra);
        exemplarRepository.save(exemplar);

        return new ExemplarDTOResponse(
                exemplar.getId(),
                exemplar.getDisponibilidade(),
                exemplar.getNumero(),
                exemplar.getObra().getId(),
                exemplar.getObra().getTitulo_Principal()
        );
    }

    public void deletar(Long id) {
        Exemplar exemplar = exemplarRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exemplar não encontrado!"));
        exemplarRepository.delete(exemplar);
    }
}