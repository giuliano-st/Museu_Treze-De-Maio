package inf.laboratorio.museutreze.controller;

import inf.laboratorio.museutreze.model.Role;
import inf.laboratorio.museutreze.model.Usuario;
import inf.laboratorio.museutreze.model.ObraHistorico;
import inf.laboratorio.museutreze.repository.UsuarioRepository;
import inf.laboratorio.museutreze.repository.ObraHistoricoRepository;
import inf.laboratorio.museutreze.repository.ObraRepository;
import inf.laboratorio.museutreze.dto.ObraDTORequest;
import inf.laboratorio.museutreze.dto.ObraDTOResponse;
import inf.laboratorio.museutreze.dto.AutorDTORequest;
import inf.laboratorio.museutreze.dto.EditoraDTORequest;
import inf.laboratorio.museutreze.dto.AssuntoDTORequest;
import inf.laboratorio.museutreze.dto.ExemplarDTORequest;
import inf.laboratorio.museutreze.model.Obra;
import inf.laboratorio.museutreze.service.ObraService;
import inf.laboratorio.museutreze.service.AutorService;
import inf.laboratorio.museutreze.service.EditoraService;
import inf.laboratorio.museutreze.service.AssuntoService;
import inf.laboratorio.museutreze.service.ExemplarService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Controller
public class PaginaController {

    private final UsuarioRepository usuarioRepository;
    private final ObraHistoricoRepository obraHistoricoRepository;
    private final ObraRepository obraRepository;
    private final ObraService obraService;
    private final AutorService autorService;
    private final EditoraService editoraService;
    private final AssuntoService assuntoService;
    private final ExemplarService exemplarService;
    private final PasswordEncoder passwordEncoder;

    public PaginaController(UsuarioRepository usuarioRepository, ObraHistoricoRepository obraHistoricoRepository,
                            ObraRepository obraRepository, ObraService obraService, AutorService autorService,
                            EditoraService editoraService, AssuntoService assuntoService, ExemplarService exemplarService, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.obraHistoricoRepository = obraHistoricoRepository;
        this.obraRepository = obraRepository;
        this.obraService = obraService;
        this.autorService = autorService;
        this.editoraService = editoraService;
        this.assuntoService = assuntoService;
        this.exemplarService = exemplarService;
        this.passwordEncoder = passwordEncoder;
    }

    private Usuario getUsuarioLogado() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        assert auth != null;
        return usuarioRepository.findByEmail(auth.getName()).orElse(null);
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }

    //verificar esse post aqui, pois pode ser uma falha de segurança
    @PostMapping("/register")
    public String register(
            @RequestParam String nomeUsuario,
            @RequestParam String email,
            @RequestParam String senha,
            @RequestParam String role,
            Model model
    ) {

        if (usuarioRepository.findByEmail(email).isPresent()) {
            model.addAttribute("erro", "E-mail já cadastrado.");
            return "register";
        }

        Usuario usuario = new Usuario();

        usuario.setNomeUsuario(nomeUsuario);
        usuario.setEmail(email);
        usuario.setSenha(passwordEncoder.encode(senha));
        usuario.setRole(Role.valueOf(role));

        usuarioRepository.save(usuario);
        return "redirect:/login";
    }

    @GetMapping("/pesquisa")
    public String pesquisa(@RequestParam(required = false) String termo,
                           @RequestParam(required = false) String tipo,
                           @RequestParam(required = false) String dataInicio,
                           @RequestParam(required = false) String dataFim,
                           Model model) {
        List<ObraDTOResponse> obras = obraService.listar();

        if (termo != null && !termo.isBlank()) {
            String termoLower = termo.toLowerCase();
            obras = obras.stream()
                    .filter(o -> (o.titulo_Principal() != null && o.titulo_Principal().toLowerCase().contains(termoLower))
                            || (o.autor() != null && o.autor().nome() != null && o.autor().nome().toLowerCase().contains(termoLower)))
                    .toList();
        }

        if (tipo != null && !tipo.isBlank()) {
            obras = obras.stream().filter(o -> tipo.equals(o.obra_tipo())).toList();
        }

        if (dataInicio != null && !dataInicio.isBlank()) {
            int inicio = Integer.parseInt(dataInicio);
            obras = obras.stream()
                    .filter(o -> o.data() != null && o.data().getYear() >= inicio)
                    .toList();
        }

        if (dataFim != null && !dataFim.isBlank()) {
            int fim = Integer.parseInt(dataFim);
            obras = obras.stream()
                    .filter(o -> o.data() != null && o.data().getYear() <= fim)
                    .toList();
        }

        model.addAttribute("obras", obras);
        model.addAttribute("termo", termo);
        model.addAttribute("tipo", tipo);
        model.addAttribute("dataInicio", dataInicio);
        model.addAttribute("dataFim", dataFim);
        return "pesquisa";
    }

    @GetMapping("/obra/{id}")
    public String detalhes(@PathVariable Long id, Model model) {

        model.addAttribute("obra", obraService.buscarPorId(id));

        model.addAttribute("exemplares", exemplarService.listar().stream()
                .filter(e -> e.obraId() != null && e.obraId().equals(id))
                .toList());

        Usuario u = getUsuarioLogado();

        model.addAttribute("isBibliotecario",
                u != null && u.getRole() == Role.BIBLIOTECARIO);

        return "detalhes";
    }

    @GetMapping("/cadastro")
    public String cadastroEscolha() {
        return "cadastro-escolha";
    }

    @GetMapping("/cadastro/obra")
    public String obraForm(@RequestParam(required = false) Long editar, Model model) {

        model.addAttribute("autores", autorService.listar());
        model.addAttribute("editoras", editoraService.listar());
        model.addAttribute("assuntos", assuntoService.listar());
        model.addAttribute("obra", editar != null ? obraService.buscarPorId(editar) : null);

        return "cadastro-obra";
    }

    @PostMapping("/cadastro/obra")
    public String obraSalvar(@RequestParam(required = false) Long id,
                             @RequestParam String obra_tipo,
                             @RequestParam String titulo_Principal,
                             @RequestParam(required = false)
                                 @DateTimeFormat(pattern = "yyyy-MM-dd")
                                 Date data,
                             @RequestParam(required = false) String capa,
                             @RequestParam(required = false) String local,
                             @RequestParam(required = false) String descFisica,
                             @RequestParam(required = false) String nome,
                             @RequestParam(required = false) String numeroChamada,
                             @RequestParam(required = false) String chamadaLocal,
                             @RequestParam(required = false) String tituloUniforme,
                             @RequestParam(required = false) String isbn,
                             @RequestParam(required = false) String serie,
                             @RequestParam(required = false) String edicao,
                             @RequestParam(required = false) String colecao,
                             @RequestParam(required = false) String notasGerais,
                             @RequestParam(required = false) String issn,
                             @RequestParam(required = false) Integer volume,
                             @RequestParam(required = false) String periodicidade,
                             @RequestParam(required = false) Long autorId,
                             @RequestParam(required = false) Long editoraId,
                             @RequestParam(required = false) List<Long> assuntosIds
                             ) {


        if (capa == null || capa.isBlank()) {

            switch (obra_tipo) {

                case "LIVRO":
                    capa = "/imagens/capaLivro.png";
                    break;

                case "REVISTA":
                    capa = "/imagens/capaRevista.png";
                    break;

                case "JORNAL":
                    capa = "/imagens/capaJornal.png";
                    break;
            }
        }
        LocalDate dataConverida = data.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        ObraDTORequest request = new ObraDTORequest(
                obra_tipo, titulo_Principal, capa, local, dataConverida, descFisica, nome,
                numeroChamada, chamadaLocal, tituloUniforme, isbn, serie, edicao,
                colecao, notasGerais, issn, volume, periodicidade,
                autorId, editoraId, assuntosIds
        );

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        assert authentication != null;
        Usuario usuarioLogado = usuarioRepository
                .findByEmail(authentication.getName())
                .orElse(null);

        ObraDTOResponse resultado;

        if (id != null) {
            resultado = obraService.atualizar(id, request);
            registrarHistorico("EDITOU", usuarioLogado, resultado.id());
        } else {
            resultado = obraService.salvar(request);
            registrarHistorico("CADASTROU", usuarioLogado, resultado.id());
        }

        return "redirect:/cadastro";
    }

    @PostMapping("/obra/excluir/{id}")
    public String excluirObra(@PathVariable Long id) {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        assert authentication != null;
        Usuario usuarioLogado = usuarioRepository
                .findByEmail(authentication.getName())
                .orElse(null);
        registrarHistorico("EXCLUIU", usuarioLogado, id);
        obraService.deletar(id);
        return "redirect:/pesquisa";
    }

    @GetMapping("/dados")
    public String dados(Model model) {
        model.addAttribute("autores", autorService.listar());
        model.addAttribute("editoras", editoraService.listar());
        model.addAttribute("assuntos", assuntoService.listar());
        return "dados";
    }

    @PostMapping("/dados/autor")
    public String salvarAutor(@RequestParam String nome, @RequestParam(required = false) String nacionalidade
                              ) {
        autorService.salvar(new AutorDTORequest(nome, nacionalidade));
        return "redirect:/dados";
    }

    @PostMapping("/dados/editora")
    public String salvarEditora(@RequestParam String nome) {
        editoraService.salvar(new EditoraDTORequest(nome));
        return "redirect:/dados";
    }

    @PostMapping("/dados/assunto")
    public String salvarAssunto(@RequestParam String descricao) {
        assuntoService.salvar(new AssuntoDTORequest(descricao));
        return "redirect:/dados";
    }

    @GetMapping("/obra/{obraId}/exemplares")
    public String exemplares(@PathVariable Long obraId , Model model) {
        model.addAttribute("obra", obraService.buscarPorId(obraId));
        model.addAttribute("exemplares", exemplarService.listar().stream()
                .filter(e -> e.obraId() != null && e.obraId().equals(obraId))
                .toList());
        return "exemplares";
    }

    @PostMapping("/obra/{obraId}/exemplares")
    public String salvarExemplar(@PathVariable Long obraId, @RequestParam Integer numero,
                                 @RequestParam(required = false) Boolean disponibilidade
                                 ) {
        exemplarService.salvar(new ExemplarDTORequest(
                disponibilidade != null ? disponibilidade : true, numero, obraId
        ));
        return "redirect:/obra/" + obraId + "/exemplares";
    }

    @PostMapping("/exemplar/excluir/{id}")
    public String excluirExemplar(@PathVariable Long id, @RequestParam Long obraId) {
        exemplarService.deletar(id);
        return "redirect:/obra/" + obraId + "/exemplares";
    }

    @GetMapping("/historico-acesso")
    public String historico(Model model) {

        Usuario u = getUsuarioLogado();

        if (u == null || u.getRole() != Role.BIBLIOTECARIO) {
            return "redirect:/login";
        }

        List<ObraHistorico> registros = obraHistoricoRepository.findAll().stream()
                .sorted(Comparator.comparing(ObraHistorico::getData).reversed())
                .toList();

        model.addAttribute("registros", registros);
        return "historico";
    }

    private void registrarHistorico(String operacao,
                                    Usuario usuario,
                                    Long obraId) {
        if (usuario == null) return;

        Obra obra = obraRepository.findById(obraId).orElse(null);
        if (obra == null) return;

        ObraHistorico registro = new ObraHistorico();
        registro.setOperacao(operacao);
        registro.setData(LocalDateTime.now());
        registro.setUsuario(usuario);
        registro.setNomeObra(obra.getTitulo_Principal());

        obraHistoricoRepository.save(registro);
    }

}
