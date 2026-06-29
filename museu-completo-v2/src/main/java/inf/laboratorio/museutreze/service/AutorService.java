package inf.laboratorio.museutreze.service;

import inf.laboratorio.museutreze.dto.AutorDTORequest;
import inf.laboratorio.museutreze.dto.AutorDTOResponse;
import inf.laboratorio.museutreze.model.Autor;
import inf.laboratorio.museutreze.model.Obra;
import inf.laboratorio.museutreze.repository.AutorRepository;
import inf.laboratorio.museutreze.repository.ObraRepository;
import inf.laboratorio.museutreze.repository.SecundarioRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AutorService {

    private static final String NOME_AUTOR_DESCONHECIDO = "Autor desconhecido";
    private static final String NACIONALIDADE_AUTOR_DESCONHECIDO = "Desconhecida";

    private final AutorRepository autorRepository;
    private final ObraRepository obraRepository;
    private final SecundarioRepository secundarioRepository;

    public AutorService(AutorRepository autorRepository, ObraRepository obraRepository, SecundarioRepository secundarioRepository) {
        this.autorRepository = autorRepository;
        this.obraRepository = obraRepository;
        this.secundarioRepository = secundarioRepository;
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

    public List<AutorDTOResponse> salvarLista(List<AutorDTORequest> autoresDTO) {
        List<AutorDTOResponse> responses = new ArrayList<>();
        for (AutorDTORequest dto : autoresDTO) {
            responses.add(salvar(dto));
        }
        return responses;
    }

    public AutorDTOResponse buscarPorId(Long id){
        Autor autor = autorRepository.findById(id).orElseThrow(() -> new RuntimeException("Autor inexistente!"));
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

    /**
     * Exclui o autor SEM excluir as obras associadas (zero cascade delete).
     * Antes de excluir, todas as obras vinculadas a este autor são reatribuídas
     * automaticamente para o autor genérico "Autor desconhecido" (fallback,
     * mesmo princípio usado para capa padrão quando não há imagem).
     */
    // Mudei o deletar(): adicionei a remoção dos registros de Secundario vinculados a este
    // autor ANTES de deletar o autor. Mesmo motivo do ObraService: a tabela secundario tem
    // FK pra autores (autor_id), e o banco rejeita o delete enquanto existir um secundario
    // apontando pra esse autor. Fiz isso pra resolver esse erro.
    // Ass: Mribas
    public void deletar(Long id){
        Autor autor = autorRepository.findById(id).orElseThrow(() -> new RuntimeException("Autor inexistente!"));

        Autor autorDesconhecido = buscarOuCriarAutorDesconhecido();

        if (!autor.getId().equals(autorDesconhecido.getId())) {
            List<Obra> obrasDoAutor = obraRepository.findByAutorId(autor.getId());
            for (Obra obra : obrasDoAutor) {
                obra.setAutor(autorDesconhecido);
            }
            obraRepository.saveAll(obrasDoAutor);
        }

        secundarioRepository.deleteAll(secundarioRepository.findAllByAutorId(autor));

        autorRepository.delete(autor);
    }

    private Autor buscarOuCriarAutorDesconhecido() {
        return autorRepository.findByNomeIgnoreCase(NOME_AUTOR_DESCONHECIDO)
                .orElseGet(() -> {
                    Autor desconhecido = new Autor();
                    desconhecido.setNome(NOME_AUTOR_DESCONHECIDO);
                    desconhecido.setNacionalidade(NACIONALIDADE_AUTOR_DESCONHECIDO);
                    return autorRepository.save(desconhecido);
                });
    }
}