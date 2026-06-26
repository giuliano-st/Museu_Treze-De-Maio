package inf.laboratorio.museutreze.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import inf.laboratorio.museutreze.model.*;
import inf.laboratorio.museutreze.repository.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import java.io.InputStream;
import java.util.List;

@Component
public class ObraDataLoader {

    private final ObraRepository obraRepository;
    private final ObjectMapper objectMapper;
    private final AutorRepository autorRepository;
    private final EditoraRepository editoraRepository;
    private final AssuntoRepository assuntoRepository;
    private final ExemplarRepository exemplarRepository;
    private final SecundarioRepository secundarioRepository;


    public ObraDataLoader(
            ObraRepository obraRepository,
            ObjectMapper objectMapper, AutorRepository autorRepository, EditoraRepository editoraRepository, AssuntoRepository assuntoRepository, ExemplarRepository exemplarRepository, SecundarioRepository secundarioRepository
    ) {
        this.obraRepository = obraRepository;
        this.objectMapper = objectMapper;
        this.autorRepository = autorRepository;
        this.editoraRepository = editoraRepository;
        this.assuntoRepository = assuntoRepository;
        this.exemplarRepository = exemplarRepository;
        this.secundarioRepository = secundarioRepository;
    }

    public void carregarObras() {

        if (obraRepository.count() > 0) {
            return;
        }

        try {

            InputStream inputStream =
                    new ClassPathResource(
                            "config/dados_obras.json"
                    ).getInputStream();

            List<ObraJsonDTO> obras =
                    objectMapper.readValue(
                            inputStream,
                            new TypeReference<List<ObraJsonDTO>>() {}
                    );

            for (ObraJsonDTO dto : obras) {
                salvarObra(dto);
            }

        } catch (Exception e) {

            throw new RuntimeException(
                    "Erro ao carregar dados_obras.json",
                    e
            );
        }
    }

    private void salvarObra(ObraJsonDTO dto) {

        Autor autorPrincipal =
                autorRepository.findByNomeIgnoreCase(
                        dto.getAutorPrincipal()
                ).orElseGet(() -> {

                    Autor autor = new Autor();

                    autor.setNome(
                            dto.getAutorPrincipal()
                    );

                    autor.setNacionalidade(
                            dto.getNacionalidadeAutor() != null
                                    ? dto.getNacionalidadeAutor()
                                    : "Não informado"
                    );

                    return autorRepository.save(autor);
                });

        Editora editora = null;

        if (dto.getEditora() != null &&
                !dto.getEditora().isBlank()) {

            editora =
                    editoraRepository.findByNome(
                            dto.getEditora()
                    ).orElseGet(() -> {

                        Editora nova = new Editora();

                        nova.setNome(
                                dto.getEditora()
                        );

                        return editoraRepository.save(
                                nova
                        );
                    });
        }

        List<Assunto> assuntos = new java.util.ArrayList<>();

        if (dto.getAssuntos() != null) {

            for (String descricao : dto.getAssuntos()) {

                Assunto assunto =
                        assuntoRepository.findByDescricaoIgnoreCase(
                                descricao
                        ).orElseGet(() -> {

                            Assunto novo = new Assunto();

                            novo.setDescricao(
                                    descricao
                            );

                            return assuntoRepository.save(
                                    novo
                            );
                        });

                assuntos.add(assunto);
            }
        }

        Obra obra = new Obra();

        obra.setObra_tipo(dto.getTipo());
        obra.setTitulo_Principal(dto.getTitulo());

        obra.setCapa(dto.getCapa());
        obra.setLocal(dto.getLocal());

        obra.setDescFisica(dto.getDescFisica());
        obra.setNome(dto.getNome());

        obra.setNumeroChamada(
                dto.getNumeroChamada()
        );

        obra.setChamadaLocal(
                dto.getChamadaLocal()
        );

        obra.setTituloUniforme(
                dto.getTituloUniforme()
        );

        obra.setIsbn(dto.getIsbn());

        obra.setSerie(dto.getSerie());

        obra.setEdicao(dto.getEdicao());

        obra.setColecao(dto.getColecao());

        obra.setNotasGerais(
                dto.getNotasGerais()
        );

        obra.setIssn(dto.getIssn());

        obra.setVolume(dto.getVolume());

        obra.setPeriodicidade(
                dto.getPeriodicidade()
        );

        obra.setAutor(autorPrincipal);

        obra.setEditora(editora);

        obra.setAssuntos(assuntos);

        //conversor de data
        if (dto.getData() != null &&
                !dto.getData().isBlank()) {

            LocalDate data = LocalDate.parse(dto.getData());
            obra.setData(data);
        }

        switch (dto.getTipo().toUpperCase()) {
            case "LIVRO" ->
                    obra.setCapa("/imagens/capaLivro.png");

            case "JORNAL" ->
                    obra.setCapa("/imagens/capaJornal.png");

            case "REVISTA" ->
                    obra.setCapa("/imagens/capaRevista.png");
        }

        obra = obraRepository.save(obra);

        if (dto.getQuantidadeExemplares() != null) {

            for (int i = 1;
                 i <= dto.getQuantidadeExemplares();
                 i++) {

                Exemplar exemplar = new Exemplar();

                exemplar.setNumero(i);

                exemplar.setDisponibilidade(
                        true
                );

                exemplar.setObra(obra);

                exemplarRepository.save(
                        exemplar
                );
            }
        }

        if (dto.getAutoresSecundarios() != null) {

            for (String nomeAutor :
                    dto.getAutoresSecundarios()) {

                Autor secundarioAutor =
                        autorRepository.findByNomeIgnoreCase(
                                nomeAutor
                        ).orElseGet(() -> {

                            Autor novo = new Autor();

                            novo.setNome(nomeAutor);

                            novo.setNacionalidade(
                                    "Não informado"
                            );

                            return autorRepository.save(
                                    novo
                            );
                        });

                Secundario secundario =
                        new Secundario();

                secundario.setObraId(obra);

                secundario.setAutorId(
                        secundarioAutor
                );

                secundarioRepository.save(
                        secundario
                );
            }
        }
    }
}