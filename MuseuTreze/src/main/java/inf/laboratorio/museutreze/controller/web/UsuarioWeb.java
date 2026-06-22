package inf.laboratorio.museutreze.controller.web;

import inf.laboratorio.museutreze.dto.UsuarioDTORequest;
import inf.laboratorio.museutreze.dto.UsuarioDTOResponse;
import inf.laboratorio.museutreze.dto.web.UsuarioWebDTO;
import inf.laboratorio.museutreze.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/usuarios")
public class UsuarioWeb {

	private final UsuarioService usuarioService;

	public UsuarioWeb(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	@GetMapping
	public String listar(Model model) {
		List<UsuarioDTOResponse> usuarios = usuarioService.listar();
		model.addAttribute("usuarios", usuarios);
		return "usuarios/lista";
	}

	@GetMapping("/novo")
	public String novo(Model model) {
		model.addAttribute("usuario", new UsuarioWebDTO());
		return "usuarios/form";
	}

	@GetMapping("/{id}/editar")
	public String editar(@PathVariable Long id, Model model) {
		UsuarioDTOResponse usuario = usuarioService.buscarPorId(id);
		model.addAttribute("usuario", usuario);
		return "usuarios/form";
	}

	@PostMapping
	public String salvar(UsuarioDTORequest usuario, RedirectAttributes redirectAttributes) {
		try {
			usuarioService.salvar(usuario);
			redirectAttributes.addFlashAttribute("mensagem", "Usuário criado com sucesso!");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("erro", "Erro ao criar usuário: " + e.getMessage());
		}
		return "redirect:/usuarios";
	}

	@PostMapping("/{id}")
	public String atualizar(@PathVariable Long id, UsuarioDTORequest usuario, RedirectAttributes redirectAttributes) {
		try {
			usuarioService.atualizar(id, usuario);
			redirectAttributes.addFlashAttribute("mensagem", "Usuário atualizado com sucesso!");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("erro", "Erro ao atualizar usuário: " + e.getMessage());
		}
		return "redirect:/usuarios";
	}

	@PostMapping("/{id}/deletar")
	public String deletar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		try {
			usuarioService.deletar(id);
			redirectAttributes.addFlashAttribute("mensagem", "Usuário deletado com sucesso!");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("erro", "Erro ao deletar usuário: " + e.getMessage());
		}
		return "redirect:/usuarios";
	}
}
