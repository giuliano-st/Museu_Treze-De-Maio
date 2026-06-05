package inf.laboratorio.museutreze.service;

import inf.laboratorio.museutreze.dto.AutorDTORequest;
import inf.laboratorio.museutreze.dto.AutorDTOResponse;
import inf.laboratorio.museutreze.model.Autor;
import inf.laboratorio.museutreze.repository.AutorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutorService {
    private final AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }
    public AutorDTOResponse salvar(AutorDTORequest autorDTO){
        Autor autor = new Autor();
        autor.setNome(autorDTO.nome());
        autor.setNacionalidade(autorDTO.nacionalidade());
        autorRepository.save(autor);

        return new AutorDTOResponse(
                autor.getId(),
                autor.getNome(),
                autor.getNacionalidade()
        );
    }

    public List<AutorDTOResponse> listar(){
        List<Autor> autores = autorRepository.findAll();
        return autores.stream().map(autor -> new AutorDTOResponse(autor.getId(), autor.getNome(), autor.getNacionalidade())).toList();
    }

    public AutorDTOResponse atualizar(Long id, AutorDTORequest autorDTO){
        Autor autor = autorRepository.findById(id).orElseThrow(() -> new RuntimeException("Autor inexistente!"));
        autor.setNome(autorDTO.nome());
        autor.setNacionalidade(autorDTO.nacionalidade());
        autorRepository.save(autor);

        return new AutorDTOResponse(autor.getId(), autor.getNome(), autor.getNacionalidade());
    }

    public void deletar(Long id){
        Autor autor  = autorRepository.findById(id).orElseThrow(() -> new RuntimeException("Autor inexistente!"));
        autorRepository.delete(autor);
    }
}
