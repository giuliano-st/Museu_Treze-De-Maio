package inf.laboratorio.museutreze.controller.web;

import inf.laboratorio.museutreze.dto.EditoraDTORequest;
import inf.laboratorio.museutreze.dto.EditoraDTOResponse;
import inf.laboratorio.museutreze.service.EditoraService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/editoras")
public class EditoraWeb {

    private final EditoraService editoraService;

    public EditoraWeb(EditoraService editoraService) {
        this.editoraService = editoraService;
    }

    @GetMapping
    public String listar(Model model) {
        List<EditoraDTOResponse> editoras = editoraService.listar();
        model.addAttribute("editoras", editoras);
        return "editoras/lista";
    }

    @GetMapping("/nova")
    public String nova(Model model) {
        model.addAttribute("editora", new EditoraDTORequest(null));
        return "editoras/form";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        EditoraDTOResponse editora = editoraService.buscarPorId(id);
        model.addAttribute("editora", editora);
        return "editoras/form";
    }

    @PostMapping
    public String salvar(EditoraDTORequest editora, RedirectAttributes redirectAttributes) {
        try {
            editoraService.salvar(editora);
            redirectAttributes.addFlashAttribute("mensagem", "Editora criada com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao criar editora: " + e.getMessage());
        }
        return "redirect:/editoras";
    }

    @PostMapping("/{id}")
    public String atualizar(@PathVariable Long id, EditoraDTORequest editora, RedirectAttributes redirectAttributes) {
        try {
            editoraService.atualizar(id, editora);
            redirectAttributes.addFlashAttribute("mensagem", "Editora atualizada com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao atualizar editora: " + e.getMessage());
        }
        return "redirect:/editoras";
    }

    @PostMapping("/{id}/deletar")
    public String deletar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            editoraService.deletar(id);
            redirectAttributes.addFlashAttribute("mensagem", "Editora deletada com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao deletar editora: " + e.getMessage());
        }
        return "redirect:/editoras";
    }
}
