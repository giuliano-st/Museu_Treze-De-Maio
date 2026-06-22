package inf.laboratorio.museutreze.controller.web;

import inf.laboratorio.museutreze.dto.ObraHistoricoDTORequest;
import inf.laboratorio.museutreze.dto.ObraHistoricoDTOResponse;
import inf.laboratorio.museutreze.dto.ObraHistoricoDTORequest;
import inf.laboratorio.museutreze.service.ObraHistoricoService;
import inf.laboratorio.museutreze.service.ObraService;
import inf.laboratorio.museutreze.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/obras-historico")
public class ObraHistoricoWeb {

	private final ObraHistoricoService obraHistoricoService;
	private final ObraService obraService;
	private final UsuarioService usuarioService;

	public ObraHistoricoWeb(ObraHistoricoService obraHistoricoService, ObraService obraService, UsuarioService usuarioService) {
		this.obraHistoricoService = obraHistoricoService;
		this.obraService = obraService;
		this.usuarioService = usuarioService;
	}

	@GetMapping
	public String listar(Model model) {
		List<ObraHistoricoDTOResponse> historicos = obraHistoricoService.listar();
		model.addAttribute("historicos", historicos);
		return "obras-historico/lista";
	}

	@GetMapping("/novo")
	public String novo(Model model) {
		// DTORequest é um record; para popular o formulário fornecemos valores nulos
		model.addAttribute("obraHistorico", new ObraHistoricoDTORequest(null, null, null));
		model.addAttribute("obras", obraService.listar());
		model.addAttribute("usuarios", usuarioService.listar());
		return "obras-historico/form";
	}

	@GetMapping("/{id}/editar")
	public String editar(@PathVariable Long id, Model model) {
		ObraHistoricoDTOResponse historico = obraHistoricoService.buscarPorId(id);
		model.addAttribute("obraHistorico", historico);
		model.addAttribute("obras", obraService.listar());
		model.addAttribute("usuarios", usuarioService.listar());
		return "obras-historico/form";
	}

	@PostMapping
	public String salvar(ObraHistoricoDTORequest historico, RedirectAttributes redirectAttributes) {
		try {
			obraHistoricoService.salvar(historico);
			redirectAttributes.addFlashAttribute("mensagem", "Histórico criado com sucesso!");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("erro", "Erro ao criar histórico: " + e.getMessage());
		}
		return "redirect:/obras-historico";
	}

	@PostMapping("/{id}")
	public String atualizar(@PathVariable Long id, ObraHistoricoDTORequest historico, RedirectAttributes redirectAttributes) {
		try {
			obraHistoricoService.atualizar(id, historico);
			redirectAttributes.addFlashAttribute("mensagem", "Histórico atualizado com sucesso!");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("erro", "Erro ao atualizar histórico: " + e.getMessage());
		}
		return "redirect:/obras-historico";
	}

	@PostMapping("/{id}/deletar")
	public String deletar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		try {
			obraHistoricoService.deletar(id);
			redirectAttributes.addFlashAttribute("mensagem", "Histórico deletado com sucesso!");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("erro", "Erro ao deletar histórico: " + e.getMessage());
		}
		return "redirect:/obras-historico";
	}
}
