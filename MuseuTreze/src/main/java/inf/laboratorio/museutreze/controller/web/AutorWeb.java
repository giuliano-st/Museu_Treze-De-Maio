package inf.laboratorio.museutreze.controller.web;

import inf.laboratorio.museutreze.dto.AutorDTORequest;
import inf.laboratorio.museutreze.dto.AutorDTOResponse;
import inf.laboratorio.museutreze.service.AutorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/autores")
public class AutorWeb {

    private final AutorService autorService;

    public AutorWeb(AutorService autorService) {
        this.autorService = autorService;
    }

    // GET /autores → Listar todos os autores
    @GetMapping
    public String listar(Model model) {
        List<AutorDTOResponse> autores = autorService.listar();
        model.addAttribute("autores", autores);
        return "autores/lista";
    }

    // GET /autores/novo → Formulário vazio
    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("autor", new AutorDTORequest(null, null));
        return "autores/form";
    }

    // GET /autores/{id}/editar → Formulário preenchido
    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        AutorDTOResponse autor = autorService.buscarPorId(id);
        model.addAttribute("autor", autor);
        return "autores/form";
    }

    // POST /autores → Salva novo autor
    @PostMapping
    public String salvar(AutorDTORequest autor, RedirectAttributes redirectAttributes) {
        try {
            autorService.salvar(autor);
            redirectAttributes.addFlashAttribute("mensagem", "Autor criado com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao criar autor: " + e.getMessage());
        }
        return "redirect:/autores";
    }

    // POST /autores/{id} → Atualiza autor existente
    @PostMapping("/{id}")
    public String atualizar(@PathVariable Long id, AutorDTORequest autor, RedirectAttributes redirectAttributes) {
        try {
            autorService.atualizar(id, autor);
            redirectAttributes.addFlashAttribute("mensagem", "Autor atualizado com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao atualizar autor: " + e.getMessage());
        }
        return "redirect:/autores";
    }

    // POST /autores/{id}/deletar → Deleta autor
    @PostMapping("/{id}/deletar")
    public String deletar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            autorService.deletar(id);
            redirectAttributes.addFlashAttribute("mensagem", "Autor deletado com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao deletar autor: " + e.getMessage());
        }
        return "redirect:/autores";
    }
}