package inf.laboratorio.museutreze.service;

import inf.laboratorio.museutreze.dto.ExemplarDTOResponse;
import inf.laboratorio.museutreze.model.Exemplar;
import inf.laboratorio.museutreze.model.Obra;
import inf.laboratorio.museutreze.repository.ExemplarRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExemplarService {
    private final ExemplarRepository exemplarRepository;

    public ExemplarService(ExemplarRepository exemplarRepository) {
        this.exemplarRepository = exemplarRepository;
    }

    public ExemplarDTOResponse salvar(ExemplarDTOResponse exemplarDTO) {
        Exemplar exemplar = new Exemplar();
        Obra obra = new Obra();
        obra.setId(exemplarDTO.obraId());

        exemplar.setDisponibilidade(exemplarDTO.disponibilidade());
        exemplar.setNumero(exemplarDTO.numero());
        exemplar.setObra(obra);
        exemplarRepository.save(exemplar);
        /* Ver com o Gustavo
        Obra obra = new Obra();
        obra.setId(exemplarDTO.obraId());
        Exemplar exemplar = mapper.toEntity(exemplarDTO, obra);
        exemplarRepository.save(exemplar);*/
        return new ExemplarDTOResponse(exemplar.getId(), exemplar.getDisponibilidade(), exemplar.getNumero(),exemplar.getObra().getId(), exemplar.getObra().getTitulo_Principal());
    }

    public List<ExemplarDTOResponse> listar() {
        List<Exemplar> exemplares = exemplarRepository.findAll();
        return exemplares.stream().map(exemplar -> new ExemplarDTOResponse(exemplar.getId(), exemplar.getDisponibilidade(), exemplar.getNumero(), exemplar.getObra().getId(), exemplar.getObra().getTitulo_Principal())).toList();
    }

    public ExemplarDTOResponse atualizar(Long id, ExemplarDTOResponse exemplarDTO) {
        Exemplar exemplar = exemplarRepository.findById(id).orElseThrow(() -> new RuntimeException("Exemplar não encontrado!"));
        Obra obra = new Obra();
        obra.setId(exemplarDTO.obraId());

        exemplar.setDisponibilidade(exemplarDTO.disponibilidade());
        exemplar.setNumero(exemplarDTO.numero());
        exemplar.setObra(obra);
        exemplarRepository.save(exemplar);
        return new ExemplarDTOResponse(exemplar.getId(), exemplar.getDisponibilidade(), exemplar.getNumero(),exemplar.getObra().getId(), exemplar.getObra().getTitulo_Principal());
    }

    public void deletar(Long id) {
        Exemplar exemplar = exemplarRepository.findById(id).orElseThrow(() -> new RuntimeException("Exemplar não encontrado!"));
        exemplarRepository.delete(exemplar);
    }
}
