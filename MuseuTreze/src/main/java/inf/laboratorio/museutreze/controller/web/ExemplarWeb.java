package inf.laboratorio.museutreze.controller.web;

import inf.laboratorio.museutreze.dto.ExemplarDTORequest;
import inf.laboratorio.museutreze.dto.ExemplarDTOResponse;
import inf.laboratorio.museutreze.dto.web.ExemplarWebDTO;
import inf.laboratorio.museutreze.service.ExemplarService;
import inf.laboratorio.museutreze.service.ObraService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/exemplares")
public class ExemplarWeb {

	private final ExemplarService exemplarService;
	private final ObraService obraService;

	public ExemplarWeb(ExemplarService exemplarService, ObraService obraService) {
		this.exemplarService = exemplarService;
		this.obraService = obraService;
	}

	@GetMapping
	public String listar(Model model) {
		List<ExemplarDTOResponse> exemplares = exemplarService.listar();
		model.addAttribute("exemplares", exemplares);
		return "exemplares/lista";
	}

	@GetMapping("/novo")
	public String novo(Model model) {
		model.addAttribute("exemplar", new ExemplarWebDTO());
		model.addAttribute("obras", obraService.listar());
		return "exemplares/form";
	}

	@GetMapping("/{id}/editar")
	public String editar(@PathVariable Long id, Model model) {
		ExemplarDTOResponse exemplar = exemplarService.buscarPorId(id);
		model.addAttribute("exemplar", exemplar);
		model.addAttribute("obras", obraService.listar());
		return "exemplares/form";
	}

	@PostMapping
	public String salvar(ExemplarDTORequest exemplar, RedirectAttributes redirectAttributes) {
		try {
			exemplarService.salvar(exemplar);
			redirectAttributes.addFlashAttribute("mensagem", "Exemplar criado com sucesso!");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("erro", "Erro ao criar exemplar: " + e.getMessage());
		}
		return "redirect:/exemplares";
	}

	@PostMapping("/{id}")
	public String atualizar(@PathVariable Long id, ExemplarDTORequest exemplar, RedirectAttributes redirectAttributes) {
		try {
			exemplarService.atualizar(id, exemplar);
			redirectAttributes.addFlashAttribute("mensagem", "Exemplar atualizado com sucesso!");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("erro", "Erro ao atualizar exemplar: " + e.getMessage());
		}
		return "redirect:/exemplares";
	}

	@PostMapping("/{id}/deletar")
	public String deletar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		try {
			exemplarService.deletar(id);
			redirectAttributes.addFlashAttribute("mensagem", "Exemplar deletado com sucesso!");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("erro", "Erro ao deletar exemplar: " + e.getMessage());
		}
		return "redirect:/exemplares";
	}
}
