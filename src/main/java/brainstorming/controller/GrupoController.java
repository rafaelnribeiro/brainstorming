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
		pagina_retorno = pagina_retorno + grupo.getId() + "/sessoes";		
		
		return pagina_retorno;		
	}
	
	@GetMapping("{id}/edit")
	public String update(Model model, @PathVariable("id") Integer id) {
		Grupo grupo = grupoService.findOne(id).get();
		model.addAttribute("grupo", grupo);
		
		return "grupo/form";
	}
	
	@GetMapping("{id}/delete")
	public String delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
		String pagina_retorno;
		Grupo grupo = grupoService.findOne(id).get();
		grupoService.delete(grupo);
		redirectAttributes.addFlashAttribute("success", MSG_SUCCESS_DELETE);
		pagina_retorno = "redirect:/grupos";
		
		return pagina_retorno;
	}
	
	@PutMapping
	public String update(@Valid @ModelAttribute Grupo entityGrupo, BindingResult result, 
							RedirectAttributes redirectAttributes) {
		String pagina_retorno;
		Grupo grupo = grupoService.findOne(entityGrupo.getId()).get();
		grupo.setNome(entityGrupo.getNome());
		grupoService.save(grupo);
		redirectAttributes.addFlashAttribute("success", MSG_SUCCESS_EDIT);
		pagina_retorno = "redirect:/grupos/" + grupo.getId() + "/sessoes";
		
		return pagina_retorno;
	}
	
	@GetMapping("{id}/sessoes")
	public String showSessoes(Model model, @PathVariable("id") Integer id, Principal principal) {
		
		String pagina_retorno;
		User user = userService.findByEmail(principal.getName());
		Grupo grupo = grupoService.findOne(id).get();
		List<Sessao> sessoes = grupo.getSessoes();
		boolean ehAdmin = grupo.getAdministrador().getId().equals(user.getId());		
		model.addAttribute("grupo", grupo);		
		model.addAttribute("sessoes", sessoes);
		model.addAttribute("ehAdmin", ehAdmin);
		pagina_retorno = "grupo/showSessoes";
		
		return pagina_retorno;
	}
	
	@GetMapping("/{id}/participantes")
	public String showParticipantes(Model model, @PathVariable("id") Integer id, Principal principal) {
		String pagina_retorno = "redirect:/acessoNegado";
		User user = userService.findByEmail(principal.getName());
		Grupo grupo = grupoService.findOne(id).get();	
		boolean ehAdmin = grupo.getAdministrador().getId().equals(user.getId());
		List<User> participantes = grupo.getParticipantes();
		model.addAttribute("grupo", grupo);
		model.addAttribute("participantes", participantes);
		model.addAttribute("ehAdmin", ehAdmin);
		pagina_retorno = "grupo/showParticipantes";
		
		return pagina_retorno;
	}
	
	@GetMapping("/{id}/addParticipante")
	public String addParticipante(Model model, @PathVariable("id") Integer id) {
		String pagina_retorno;
		Grupo grupo = grupoService.findOne(id).get();
		model.addAttribute("grupo", grupo);
		pagina_retorno = "grupo/addParticipante";
		
		return pagina_retorno;
	}
	
	@PostMapping("/{id}/addParticipante")
	public String addParticipante(@Valid @ModelAttribute("email") String email, @PathVariable("id") Integer id,
									BindingResult result, RedirectAttributes redirectAttributes) {
		String pagina_retorno;
		Grupo grupo = grupoService.findOne(id).get();
		User participante = userService.findByEmail(email);
		grupoService.addParticipante(grupo, participante);
		redirectAttributes.addFlashAttribute("success", MSG_SUCCESS_ADD_USER);
		pagina_retorno = "redirect:/grupos/" + grupo.getId() + "/participantes";
				
		return pagina_retorno;
	}
	
	@GetMapping("/{id}/rmvParticipante/{id_participante}")
	public String rmbParticipante(@PathVariable("id") Integer id, @PathVariable("id_participante")
									Integer id_participante, RedirectAttributes redirectAttributes) {
		String pagina_retorno;
		Grupo grupo = grupoService.findOne(id).get();
		User participante = userService.findOne(id_participante).get();
		grupoService.rmvParticipante(grupo, participante);
		redirectAttributes.addFlashAttribute("success", MSG_SUCCESS_RMV_USER);
		pagina_retorno = "redirect:/grupos/" + grupo.getId() + "/participantes";
		
		return pagina_retorno;
	}
	

	private static final String MSG_SUCCESS_INSERT = "Grupo criado com sucesso.";
	private static final String MSG_SUCCESS_ADD_USER = "Usu�rio adicionado ao grupo.";
	private static final String MSG_SUCCESS_RMV_USER = "Usu�rio Removido com Sucesso.";
	private static final String MSG_SUCCESS_EDIT = "Grupo modificado com Sucesso.";
	private static final String MSG_SUCCESS_DELETE = "Grupo Removido com Sucesso.";
}