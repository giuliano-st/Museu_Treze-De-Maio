package inf.laboratorio.museutreze.controller.web;

import inf.laboratorio.museutreze.dto.AssuntoDTORequest;
import inf.laboratorio.museutreze.dto.AssuntoDTOResponse;
import inf.laboratorio.museutreze.dto.web.AssuntoWebDTO;
import inf.laboratorio.museutreze.dto.web.AutorWebDTO;
import inf.laboratorio.museutreze.service.AssuntoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/assuntos")
public class AssuntoWeb {

    private final AssuntoService assuntoService;

    public AssuntoWeb(AssuntoService assuntoService) {
        this.assuntoService = assuntoService;
    }

    @GetMapping
    public String listar(Model model) {
        List<AssuntoDTOResponse> assuntos = assuntoService.listar();
        model.addAttribute("assuntos", assuntos);
        return "assuntos/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("assunto", new AssuntoWebDTO());
        return "assuntos/form";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        AssuntoDTOResponse assunto = assuntoService.buscarPorId(id);
        model.addAttribute("assunto", assunto);
        return "assuntos/form";
    }

    @PostMapping
    public String salvar(AssuntoDTORequest assunto, RedirectAttributes redirectAttributes) {
        try {
            assuntoService.salvar(assunto);
            redirectAttributes.addFlashAttribute("mensagem", "Assunto criado com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao criar assunto: " + e.getMessage());
        }
        return "redirect:/assuntos";
    }

    @PostMapping("/{id}")
    public String atualizar(@PathVariable Long id, AssuntoDTORequest assunto, RedirectAttributes redirectAttributes) {
        try {
            assuntoService.atualizar(id, assunto);
            redirectAttributes.addFlashAttribute("mensagem", "Assunto atualizado com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao atualizar assunto: " + e.getMessage());
        }
        return "redirect:/assuntos";
    }

    @PostMapping("/{id}/deletar")
    public String deletar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            assuntoService.deletar(id);
            redirectAttributes.addFlashAttribute("mensagem", "Assunto deletado com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao deletar assunto: " + e.getMessage());
        }
        return "redirect:/assuntos";
    }
}
