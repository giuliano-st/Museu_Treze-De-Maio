package com.acervo.controller;

import com.acervo.model.Obra;
import com.acervo.model.Usuario;
import com.acervo.service.AcessoService;
import com.acervo.service.ObraService;
import com.acervo.service.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class PaginaController {

    @Autowired
    private ObraService obraService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AcessoService acessoService;

    // ─── HOME ───────────────────────────────────────────────
    @GetMapping("/")
    public String home(HttpSession session, HttpServletRequest request) {
        Usuario u = (Usuario) session.getAttribute("usuario");
        registrar(request, "/", u);
        return "home";
    }

    // ─── LOGIN ──────────────────────────────────────────────
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

    // ─── CADASTRO DE USUÁRIO ────────────────────────────────
    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute Usuario usuario, Model model) {
        try {
            usuarioService.cadastrar(usuario);
            return "redirect:/login";
        } catch (RuntimeException e) {
            model.addAttribute("erro", e.getMessage());
            return "register";
        }
    }

    // ─── PESQUISA ───────────────────────────────────────────
    @GetMapping("/pesquisa")
    public String pesquisa(@RequestParam(required = false) String termo,
                           @RequestParam(required = false) String tipo,
                           @RequestParam(required = false) String categoria,
                           @RequestParam(required = false) String dataInicio,
                           @RequestParam(required = false) String dataFim,
                           Model model, HttpSession session, HttpServletRequest request) {
        Usuario u = (Usuario) session.getAttribute("usuario");
        registrar(request, "/pesquisa", u);

        List<Obra> obras = obraService.buscar(termo, tipo, categoria, dataInicio, dataFim);
        model.addAttribute("obras", obras);
        model.addAttribute("termo", termo);
        model.addAttribute("tipo", tipo);
        model.addAttribute("categoria", categoria);
        model.addAttribute("dataInicio", dataInicio);
        model.addAttribute("dataFim", dataFim);
        return "pesquisa";
    }

    // ─── DETALHES ───────────────────────────────────────────
    @GetMapping("/obra/{id}")
    public String detalhes(@PathVariable Long id, Model model,
                           HttpSession session, HttpServletRequest request) {
        Usuario u = (Usuario) session.getAttribute("usuario");
        registrar(request, "/obra/" + id, u);

        obraService.buscarPorId(id).ifPresent(o -> model.addAttribute("obra", o));
        model.addAttribute("isAdmin", u != null && "ADMINISTRADOR".equals(u.getPapel()));
        return "detalhes";
    }

    // ─── CADASTRO OBRAS (escolha de tipo) ───────────────────
    @GetMapping("/cadastro")
    public String cadastroEscolha(HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";
        return "cadastro-escolha";
    }

    // ─── CADASTRO LIVRO ─────────────────────────────────────
    @GetMapping("/cadastro/livro")
    public String livroForm(HttpSession session, Model model,
                            @RequestParam(required = false) Long editar) {
        if (!isAdmin(session)) return "redirect:/login";
        if (editar != null) {
            obraService.buscarPorId(editar).ifPresent(o -> model.addAttribute("obra", o));
        } else {
            model.addAttribute("obra", new Obra());
        }
        return "cadastro-livro";
    }

    @PostMapping("/cadastro/livro")
    public String livroSalvar(@ModelAttribute Obra obra, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";
        Usuario u = (Usuario) session.getAttribute("usuario");
        obra.setTipo("LIVRO");
        if (obra.getQuantidadeSaidas() == null) obra.setQuantidadeSaidas(0);
        if (obra.getContadorBuscas() == null) obra.setContadorBuscas(0);
        if (obra.getStatus() == null || obra.getStatus().isBlank()) obra.setStatus("DISPONIVEL");
        obraService.salvar(obra, u.getEmail());
        return "redirect:/cadastro";
    }

    // ─── CADASTRO JORNAL ────────────────────────────────────
    @GetMapping("/cadastro/jornal")
    public String jornalForm(HttpSession session, Model model,
                             @RequestParam(required = false) Long editar) {
        if (!isAdmin(session)) return "redirect:/login";
        if (editar != null) {
            obraService.buscarPorId(editar).ifPresent(o -> model.addAttribute("obra", o));
        } else {
            model.addAttribute("obra", new Obra());
        }
        return "cadastro-jornal";
    }

    @PostMapping("/cadastro/jornal")
    public String jornalSalvar(@ModelAttribute Obra obra, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";
        Usuario u = (Usuario) session.getAttribute("usuario");
        obra.setTipo("JORNAL");
        if (obra.getQuantidadeSaidas() == null) obra.setQuantidadeSaidas(0);
        if (obra.getContadorBuscas() == null) obra.setContadorBuscas(0);
        if (obra.getStatus() == null || obra.getStatus().isBlank()) obra.setStatus("DISPONIVEL");
        obraService.salvar(obra, u.getEmail());
        return "redirect:/cadastro";
    }

    // ─── CADASTRO REVISTA ───────────────────────────────────
    @GetMapping("/cadastro/revista")
    public String revistaForm(HttpSession session, Model model,
                              @RequestParam(required = false) Long editar) {
        if (!isAdmin(session)) return "redirect:/login";
        if (editar != null) {
            obraService.buscarPorId(editar).ifPresent(o -> model.addAttribute("obra", o));
        } else {
            model.addAttribute("obra", new Obra());
        }
        return "cadastro-revista";
    }

    @PostMapping("/cadastro/revista")
    public String revistaSalvar(@ModelAttribute Obra obra, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";
        Usuario u = (Usuario) session.getAttribute("usuario");
        obra.setTipo("REVISTA");
        if (obra.getQuantidadeSaidas() == null) obra.setQuantidadeSaidas(0);
        if (obra.getContadorBuscas() == null) obra.setContadorBuscas(0);
        if (obra.getStatus() == null || obra.getStatus().isBlank()) obra.setStatus("DISPONIVEL");
        obraService.salvar(obra, u.getEmail());
        return "redirect:/cadastro";
    }

    // ─── EXCLUIR ────────────────────────────────────────────
    @PostMapping("/obra/excluir/{id}")
    public String excluir(@PathVariable Long id, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";
        Usuario u = (Usuario) session.getAttribute("usuario");
        obraService.excluir(id, u.getEmail());
        return "redirect:/pesquisa";
    }

    // ─── SAÍDA / DEVOLUÇÃO ──────────────────────────────────
    @PostMapping("/obra/saida/{id}")
    public String saida(@PathVariable Long id, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";
        Usuario u = (Usuario) session.getAttribute("usuario");
        obraService.registrarSaida(id, u.getEmail());
        return "redirect:/obra/" + id;
    }

    @PostMapping("/obra/devolucao/{id}")
    public String devolucao(@PathVariable Long id, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";
        Usuario u = (Usuario) session.getAttribute("usuario");
        obraService.registrarDevolucao(id, u.getEmail());
        return "redirect:/obra/" + id;
    }

    // ─── PAINEL ADMIN ───────────────────────────────────────
    @GetMapping("/admin")
    public String admin(HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        model.addAttribute("acessos", acessoService.listarTodos());
        model.addAttribute("top", obraService.maisAcessadas());
        return "admin";
    }

    // ─── PERFIL ─────────────────────────────────────────────
    @GetMapping("/perfil")
    public String perfil(HttpSession session, Model model) {
        Usuario u = (Usuario) session.getAttribute("usuario");
        if (u == null) return "redirect:/login";
        model.addAttribute("usuario", u);
        return "perfil";
    }

    // ─── HELPERS ────────────────────────────────────────────
    private boolean isAdmin(HttpSession session) {
        Usuario u = (Usuario) session.getAttribute("usuario");
        return u != null && "ADMINISTRADOR".equals(u.getPapel());
    }

    private void registrar(HttpServletRequest request, String pagina, Usuario u) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null) ip = request.getRemoteAddr();
        acessoService.registrar(ip, pagina, u != null ? u.getEmail() : null);
    }
}
