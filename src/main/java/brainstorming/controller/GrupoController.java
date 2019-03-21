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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import brainstorming.model.Grupo;
import brainstorming.model.Sessao;
import brainstorming.model.User;
import brainstorming.service.GrupoService;
import brainstorming.service.UserService;

@Controller
@RequestMapping("/grupos")
public class GrupoController {

	@Autowired GrupoService grupoService;
	
	@Autowired UserService userService;
	
	@GetMapping
	public String index(Model model, Principal principal) {
		User usuario = userService.findByEmail(principal.getName());
		List<Grupo> grupos = usuario.getGrupos();
		List<Grupo> gruposAdministrados = usuario.getGruposAdministrados();
		model.addAttribute("grupos", grupos);
		model.addAttribute("gruposAdministrados", gruposAdministrados);
		
		return "grupo/index";		
	}
	
	@GetMapping(value = "/new")
	public String create(Model model, @ModelAttribute Grupo entityGrupo) {
		return "grupo/form";
	}
	
	@PostMapping
	public String create(@Valid @ModelAttribute Grupo entityGrupo, Principal principal,
						BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		Grupo grupo = null;
		String pagina_retorno = "redirect:/grupos/";
		
		User admin = userService.findByEmail(principal.getName());
		entityGrupo.setAdministrador(admin);
		grupo = grupoService.save(entityGrupo);
		redirectAttributes.addFlashAttribute("success", MSG_SUCCESS_INSERT);
		pagina_retorno = pagina_retorno + grupo.getId();		
		
		return pagina_retorno;		
	}
	
	@GetMapping("{id}/sessoes")
	public String showSessoes(Model model, @PathVariable("id") Integer id, Principal principal) {
		
		String pagina_retorno;
		User user = userService.findByEmail(principal.getName());
		Grupo grupo = grupoService.findOne(id).get();
		model.addAttribute("grupo", grupo);
		List<Sessao> sessoes = grupo.getSessoes();
		model.addAttribute("sessoes", sessoes);
		pagina_retorno = "grupo/showSessoes";
		
		return pagina_retorno;
	}
	
	
	
	
	
	
	
	
	
	
	private static final String MSG_SUCCESS_INSERT = "Grupo criado com Sucesso.";
	private static final String MSG_ERROR_INSERT = "Erro ao criar Grupo.";
}