package com.acervo.controller;

import com.acervo.model.*;
import com.acervo.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class PaginaController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ObraService obraService;

    @Autowired
    private ExemplarService exemplarService;

    @Autowired
    private HistoricoService historicoService;

    @Autowired
    private AutorEditoraAssuntoService auxService;

    // HOME
    @GetMapping("/")
    public String home() {
        return "home";
    }

    // LOGIN
    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String senha,
                        HttpSession session, Model model) {
        Optional<Usuario> usuario = usuarioService.login(email, senha);
        if (usuario.isPresent()) {
            session.setAttribute("usuario", usuario.get());
            return "redirect:/";
        }
        model.addAttribute("erro", "E-mail ou senha inválidos.");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    // PESQUISA
    @GetMapping("/pesquisa")
    public String pesquisa(@RequestParam(required = false) String termo,
                           @RequestParam(required = false) String tipo,
                           Model model) {
        model.addAttribute("obras", obraService.buscar(termo, tipo));
        model.addAttribute("termo", termo);
        model.addAttribute("tipo", tipo);
        return "pesquisa";
    }

    // VISUALIZAR OBRA
    @GetMapping("/obra/{id}")
    public String visualizar(@PathVariable Long id, Model model, HttpSession session) {
        Optional<Obra> obra = obraService.buscarPorId(id);
        if (obra.isEmpty()) return "redirect:/pesquisa";
        model.addAttribute("obra", obra.get());
        model.addAttribute("exemplares", exemplarService.listarPorObra(id));
        Usuario u = (Usuario) session.getAttribute("usuario");
        model.addAttribute("isBibliotecario", u != null && "BIBLIOTECARIO".equals(u.getRole()));
        model.addAttribute("isAdmin", u != null && "ADMIN".equals(u.getRole()));
        return "detalhes";
    }

    // CADASTRAR OBRA
    @GetMapping("/cadastro")
    public String cadastroEscolha(HttpSession session) {
        if (!isBibliotecario(session)) return "redirect:/login";
        return "cadastro-escolha";
    }

    @GetMapping("/cadastro/livro")
    public String livroForm(@RequestParam(required = false) Long editar,
                            HttpSession session, Model model) {
        if (!isBibliotecario(session)) return "redirect:/login";
        carregarAuxiliares(model);
        model.addAttribute("obra", editar != null
                ? obraService.buscarPorId(editar).orElse(new Obra()) : new Obra());
        return "cadastro-livro";
    }

    @PostMapping("/cadastro/livro")
    public String livroSalvar(@ModelAttribute Obra obra,
                              @RequestParam(required = false) Long autorId,
                              @RequestParam(required = false) Long editoraId,
                              HttpSession session) {
        if (!isBibliotecario(session)) return "redirect:/login";
        Usuario u = (Usuario) session.getAttribute("usuario");
        obra.setObraTipo("LIVRO");
        vincularAuxiliares(obra, autorId, editoraId);
        obraService.salvar(obra, u);
        return "redirect:/cadastro";
    }

    @GetMapping("/cadastro/jornal")
    public String jornalForm(@RequestParam(required = false) Long editar,
                             HttpSession session, Model model) {
        if (!isBibliotecario(session)) return "redirect:/login";
        carregarAuxiliares(model);
        model.addAttribute("obra", editar != null
                ? obraService.buscarPorId(editar).orElse(new Obra()) : new Obra());
        return "cadastro-jornal";
    }

    @PostMapping("/cadastro/jornal")
    public String jornalSalvar(@ModelAttribute Obra obra,
                               @RequestParam(required = false) Long autorId,
                               @RequestParam(required = false) Long editoraId,
                               HttpSession session) {
        if (!isBibliotecario(session)) return "redirect:/login";
        Usuario u = (Usuario) session.getAttribute("usuario");
        obra.setObraTipo("JORNAL");
        vincularAuxiliares(obra, autorId, editoraId);
        obraService.salvar(obra, u);
        return "redirect:/cadastro";
    }

    @GetMapping("/cadastro/revista")
    public String revistaForm(@RequestParam(required = false) Long editar,
                              HttpSession session, Model model) {
        if (!isBibliotecario(session)) return "redirect:/login";
        carregarAuxiliares(model);
        model.addAttribute("obra", editar != null
                ? obraService.buscarPorId(editar).orElse(new Obra()) : new Obra());
        return "cadastro-revista";
    }

    @PostMapping("/cadastro/revista")
    public String revistaSalvar(@ModelAttribute Obra obra,
                                @RequestParam(required = false) Long autorId,
                                @RequestParam(required = false) Long editoraId,
                                HttpSession session) {
        if (!isBibliotecario(session)) return "redirect:/login";
        Usuario u = (Usuario) session.getAttribute("usuario");
        obra.setObraTipo("REVISTA");
        vincularAuxiliares(obra, autorId, editoraId);
        obraService.salvar(obra, u);
        return "redirect:/cadastro";
    }

    // EXCLUIR OBRA
    @PostMapping("/obra/excluir/{id}")
    public String excluirObra(@PathVariable Long id, HttpSession session) {
        if (!isBibliotecario(session)) return "redirect:/login";
        Usuario u = (Usuario) session.getAttribute("usuario");
        obraService.excluir(id, u);
        return "redirect:/pesquisa";
    }

    // EXEMPLARES
    @GetMapping("/obra/{obraId}/exemplares")
    public String exemplares(@PathVariable Long obraId, HttpSession session, Model model) {
        if (!isBibliotecario(session)) return "redirect:/login";
        model.addAttribute("obra", obraService.buscarPorId(obraId).orElse(null));
        model.addAttribute("exemplares", exemplarService.listarPorObra(obraId));
        model.addAttribute("exemplar", new Exemplar());
        return "exemplares";
    }

    @PostMapping("/obra/{obraId}/exemplares/salvar")
    public String salvarExemplar(@PathVariable Long obraId,
                                 @ModelAttribute Exemplar exemplar,
                                 HttpSession session) {
        if (!isBibliotecario(session)) return "redirect:/login";
        obraService.buscarPorId(obraId).ifPresent(exemplar::setObra);
        if (exemplar.getDisponibilidade() == null) exemplar.setDisponibilidade(true);
        exemplarService.salvar(exemplar);
        return "redirect:/obra/" + obraId + "/exemplares";
    }

    @PostMapping("/exemplar/excluir/{id}")
    public String excluirExemplar(@PathVariable Long id, HttpSession session) {
        if (!isBibliotecario(session)) return "redirect:/login";
        Exemplar e = exemplarService.buscarPorId(id).orElse(null);
        Long obraId = e != null ? e.getObra().getId() : null;
        exemplarService.excluir(id);
        return obraId != null ? "redirect:/obra/" + obraId + "/exemplares" : "redirect:/pesquisa";
    }

    // HISTÓRICO
    @GetMapping("/historico")
    public String historico(HttpSession session, Model model) {
        if (!isBibliotecario(session)) return "redirect:/login";
        model.addAttribute("registros", historicoService.listarTodos());
        return "historico";
    }

    // USUÁRIOS (só admin)
    @GetMapping("/usuarios")
    public String usuarios(HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        model.addAttribute("usuarios", usuarioService.listarTodos());
        model.addAttribute("usuario", new Usuario());
        return "usuarios";
    }

    @PostMapping("/usuarios/salvar")
    public String salvarUsuario(@ModelAttribute Usuario usuario,
                                HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        try {
            usuarioService.salvar(usuario);
        } catch (RuntimeException e) {
            model.addAttribute("erro", e.getMessage());
            model.addAttribute("usuarios", usuarioService.listarTodos());
            model.addAttribute("usuario", usuario);
            return "usuarios";
        }
        return "redirect:/usuarios";
    }

    @PostMapping("/usuarios/excluir/{id}")
    public String excluirUsuario(@PathVariable Long id, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";
        usuarioService.excluir(id);
        return "redirect:/usuarios";
    }

    // DADOS AUXILIARES
    @GetMapping("/dados")
    public String dados(HttpSession session, Model model) {
        if (!isBibliotecario(session)) return "redirect:/login";
        model.addAttribute("autores", auxService.listarAutores());
        model.addAttribute("editoras", auxService.listarEditoras());
        model.addAttribute("assuntos", auxService.listarAssuntos());
        model.addAttribute("autor", new Autor());
        model.addAttribute("editora", new Editora());
        model.addAttribute("assunto", new Assunto());
        return "dados";
    }

    @PostMapping("/dados/autor/salvar")
    public String salvarAutor(@ModelAttribute Autor autor, HttpSession session) {
        if (!isBibliotecario(session)) return "redirect:/login";
        auxService.salvarAutor(autor);
        return "redirect:/dados";
    }

    @PostMapping("/dados/editora/salvar")
    public String salvarEditora(@ModelAttribute Editora editora, HttpSession session) {
        if (!isBibliotecario(session)) return "redirect:/login";
        auxService.salvarEditora(editora);
        return "redirect:/dados";
    }

    @PostMapping("/dados/assunto/salvar")
    public String salvarAssunto(@ModelAttribute Assunto assunto, HttpSession session) {
        if (!isBibliotecario(session)) return "redirect:/login";
        auxService.salvarAssunto(assunto);
        return "redirect:/dados";
    }

    // HELPERS
    private boolean isBibliotecario(HttpSession session) {
        Usuario u = (Usuario) session.getAttribute("usuario");
        return u != null && ("BIBLIOTECARIO".equals(u.getRole()) || "ADMIN".equals(u.getRole()));
    }

    private boolean isAdmin(HttpSession session) {
        Usuario u = (Usuario) session.getAttribute("usuario");
        return u != null && "ADMIN".equals(u.getRole());
    }

    private void carregarAuxiliares(Model model) {
        model.addAttribute("autores", auxService.listarAutores());
        model.addAttribute("editoras", auxService.listarEditoras());
        model.addAttribute("assuntos", auxService.listarAssuntos());
    }

    private void vincularAuxiliares(Obra obra, Long autorId, Long editoraId) {
        if (autorId != null) { Autor a = new Autor(); a.setId(autorId); obra.setAutor(a); }
        if (editoraId != null) { Editora e = new Editora(); e.setId(editoraId); obra.setEditora(e); }
    }
}
