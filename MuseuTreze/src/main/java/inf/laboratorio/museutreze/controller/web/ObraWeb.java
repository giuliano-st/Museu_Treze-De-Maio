package inf.laboratorio.museutreze.controller.web;

import inf.laboratorio.museutreze.dto.ObraDTORequest;
import inf.laboratorio.museutreze.dto.ObraDTOResponse;
import inf.laboratorio.museutreze.dto.web.ObraWebDTO;
import inf.laboratorio.museutreze.service.ObraService;
import inf.laboratorio.museutreze.service.AutorService;
import inf.laboratorio.museutreze.service.EditoraService;
import inf.laboratorio.museutreze.service.AssuntoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/obras")
public class ObraWeb {

    private final ObraService obraService;
    private final AutorService autorService;
    private final EditoraService editoraService;
    private final AssuntoService assuntoService;

    public ObraWeb(ObraService obraService, AutorService autorService,
                             EditoraService editoraService, AssuntoService assuntoService) {
        this.obraService = obraService;
        this.autorService = autorService;
        this.editoraService = editoraService;
        this.assuntoService = assuntoService;
    }

    // Listar todas as obras
    @GetMapping
    public String listar(Model model) {
        List<ObraDTOResponse> obras = obraService.listar();
        model.addAttribute("obras", obras);
        return "obras/lista";
    }

    // Exibir formulário para criar nova obra
    @GetMapping("/nova")
    public String nova(Model model) {
        model.addAttribute("obra", new ObraWebDTO());
        model.addAttribute("autores", autorService.listar());
        model.addAttribute("editoras", editoraService.listar());
        model.addAttribute("assuntos", assuntoService.listar());
        return "obras/form";
    }

    // Exibir formulário para editar obra existente
    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        ObraDTOResponse obra = obraService.buscarPorId(id);
        model.addAttribute("obra", obra);
        model.addAttribute("autores", autorService.listar());
        model.addAttribute("editoras", editoraService.listar());
        model.addAttribute("assuntos", assuntoService.listar());
        return "obras/form";
    }

    // Salvar nova obra (POST para /obras/nova)
    @PostMapping
    public String salvar(ObraDTORequest obra, RedirectAttributes redirectAttributes) {
        try {
            obraService.salvar(obra);
            redirectAttributes.addFlashAttribute("mensagem", "Obra criada com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao criar obra: " + e.getMessage());
        }
        return "redirect:/obras";
    }

    // Atualizar obra existente (POST para /obras/{id})
    @PostMapping("/{id}")
    public String atualizar(@PathVariable Long id, ObraDTORequest obra, RedirectAttributes redirectAttributes) {
        try {
            obraService.atualizar(id, obra);
            redirectAttributes.addFlashAttribute("mensagem", "Obra atualizada com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao atualizar obra: " + e.getMessage());
        }
        return "redirect:/obras";
    }

    // Deletar obra (POST para /obras/{id}/deletar)
    @PostMapping("/{id}/deletar")
    public String deletar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            obraService.deletar(id);
            redirectAttributes.addFlashAttribute("mensagem", "Obra deletada com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao deletar obra: " + e.getMessage());
        }
        return "redirect:/obras";
    }
}