package inf.laboratorio.museutreze.controller.web;

import inf.laboratorio.museutreze.dto.SecundarioDTORequest;
import inf.laboratorio.museutreze.dto.SecundarioDTOResponse;
import inf.laboratorio.museutreze.service.SecundarioService;
import inf.laboratorio.museutreze.service.AutorService;
import inf.laboratorio.museutreze.service.ObraService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/secundarios")
public class SecundarioWeb {

	private final SecundarioService secundarioService;
	private final ObraService obraService;
	private final AutorService autorService;

	public SecundarioWeb(SecundarioService secundarioService, ObraService obraService, AutorService autorService) {
		this.secundarioService = secundarioService;
		this.obraService = obraService;
		this.autorService = autorService;
	}

	@GetMapping
	public String listar(Model model) {
		List<SecundarioDTOResponse> secundarios = secundarioService.listar();
		model.addAttribute("secundarios", secundarios);
		return "secundarios/lista";
	}

	@GetMapping("/novo")
	public String novo(Model model) {
		// DTORequest é um record; para popular o formulário podemos fornecer valores nulos
		model.addAttribute("secundario", new SecundarioDTORequest(null, null));
		model.addAttribute("obras", obraService.listar());
		model.addAttribute("autores", autorService.listar());
		return "secundarios/form";
	}

	@GetMapping("/{id}/editar")
	public String editar(@PathVariable Long id, Model model) {
		SecundarioDTOResponse secundario = secundarioService.buscarPorId(id);
		model.addAttribute("secundario", secundario);
		model.addAttribute("obras", obraService.listar());
		model.addAttribute("autores", autorService.listar());
		return "secundarios/form";
	}

	@PostMapping
	public String salvar(SecundarioDTORequest secundario, RedirectAttributes redirectAttributes) {
		try {
			secundarioService.salvar(secundario);
			redirectAttributes.addFlashAttribute("mensagem", "Registro secundário criado com sucesso!");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("erro", "Erro ao criar registro secundário: " + e.getMessage());
		}
		return "redirect:/secundarios";
	}

	@PostMapping("/{id}")
	public String atualizar(@PathVariable Long id, SecundarioDTORequest secundario, RedirectAttributes redirectAttributes) {
		try {
			secundarioService.atualizar(id, secundario);
			redirectAttributes.addFlashAttribute("mensagem", "Registro secundário atualizado com sucesso!");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("erro", "Erro ao atualizar registro secundário: " + e.getMessage());
		}
		return "redirect:/secundarios";
	}

	@PostMapping("/{id}/deletar")
	public String deletar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		try {
			secundarioService.deletar(id);
			redirectAttributes.addFlashAttribute("mensagem", "Registro secundário deletado com sucesso!");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("erro", "Erro ao deletar registro secundário: " + e.getMessage());
		}
		return "redirect:/secundarios";
	}
}
