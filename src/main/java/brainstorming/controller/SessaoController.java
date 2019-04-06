package brainstorming.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import brainstorming.model.Grupo;
import brainstorming.model.Ideia;
import brainstorming.model.Sessao;
import brainstorming.model.User;
import brainstorming.service.GrupoService;
import brainstorming.service.SessaoService;
import brainstorming.service.UserService;
import brainstorming.util.exceptions.BusinessException;

@Controller
@RequestMapping("/sessoes")
public class SessaoController {
	
	@Autowired SessaoService sessaoService;
	
	@Autowired GrupoService grupoService;
	
	@Autowired UserService userService;
	
	@GetMapping("/{id}/ideias")
	public String show(Model model, @PathVariable("id") Integer id, Principal principal) {
		String pagina_retorno;
		Sessao sessao = sessaoService.findOne(id).get();
		List<Ideia> ideias = sessao.getIdeias();
		User user = userService.findByEmail(principal.getName());
		boolean ehAdmin = sessao.getGrupo().getAdministrador().getId() == user.getId();
		boolean ehModerador = ehAdmin || sessao.getGrupo().getModeradores().contains(user);
		model.addAttribute("ehModerador", ehModerador);
		model.addAttribute("sessao", sessao);
		model.addAttribute("ideias", ideias);
		
		pagina_retorno = "sessao/showIdeias";
		
		return pagina_retorno;
		
	}
	
	@GetMapping(value = "/new")
	public String create(Model model, @RequestParam("id_grupo") Integer id_grupo,
							@ModelAttribute Sessao entitySessao, @ModelAttribute Grupo entityGrupo) {
		model.addAttribute("id_grupo", id_grupo);
		return "sessao/form";
	}
	
	@PostMapping
	public String create(@Valid @ModelAttribute Sessao entitySessao, Principal principal,
						@RequestParam("id_grupo") Integer id_grupo, 
						BindingResult result, RedirectAttributes redirectAttributes) {
		Sessao sessao = null;
		String pagina_retorno;
		
		Grupo grupo = grupoService.findOne(id_grupo).get();
		entitySessao.setGrupo(grupo);
		try {
			sessao = sessaoService.save(entitySessao);
			redirectAttributes.addFlashAttribute("success", "Sessão adicionada com sucesso");
			pagina_retorno = "redirect:/sessoes/" + sessao.getId() + "/ideias";
		} catch (BusinessException e) {
			redirectAttributes.addFlashAttribute("error", e.getMessage());
			pagina_retorno = "redirect:/grupos/" + id_grupo + "/sessoes";
		}
		
		
		return pagina_retorno;
	}
	
	@RequestMapping("/{id}/edit")
	public String update(Model model, @PathVariable("id") Integer id) {
		String pagina_retorno;

		Sessao sessao = sessaoService.findOne(id).get();
		model.addAttribute("sessao", sessao);
		pagina_retorno = "sessao/form";				

		return pagina_retorno;
	}
	
	@PutMapping
	public String update(@Valid @ModelAttribute Sessao entitySessao, BindingResult result,
						RedirectAttributes redirectAttributes) {
		String pagina_retorno;

		Sessao sessao = sessaoService.findOne(entitySessao.getId()).get();
		sessao.setDetalhes(entitySessao.getDetalhes());
		sessao.setTema(entitySessao.getTema());
		try {
			sessao = sessaoService.save(entitySessao);
			redirectAttributes.addFlashAttribute("success", "Sessão atualizada com sucesso");
			pagina_retorno = "redirect:/sessoes" + sessao.getId() + "/ideias";
		} catch (BusinessException e) {
			redirectAttributes.addFlashAttribute("error", e.getMessage());
			pagina_retorno = "redirect:/grupos/" + sessao.getGrupo().getId() + "/sessoes";
		}
				
		return pagina_retorno;
	}
	
	@RequestMapping("/{id}/delete")
	public String delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
		String pagina_retorno;
		Sessao sessao = sessaoService.findOne(id).get();
		Grupo grupo = sessao.getGrupo();
		sessaoService.delete(sessao);
		redirectAttributes.addFlashAttribute("success", "Sessão removida com sucesso");
		pagina_retorno = "redirect:/grupos/" + grupo.getId() + "/sessoes";
		return pagina_retorno;
	}
}
