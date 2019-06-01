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
import brainstorming.model.Participacao;
import brainstorming.model.Sessao;
import brainstorming.model.User;
import brainstorming.service.GrupoService;
import brainstorming.service.UserService;
import brainstorming.util.exceptions.BusinessException;

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
		String pagina_retorno;
		User admin = userService.findByEmail(principal.getName());
		entityGrupo.setAdministrador(admin);
		try {
			Grupo grupo = grupoService.save(entityGrupo);
			redirectAttributes.addFlashAttribute("success", "Grupo criado com sucesso");
			pagina_retorno = "redirect:/grupos/" + grupo.getId() + "/sessoes";
		}catch (BusinessException e) {
			redirectAttributes.addFlashAttribute("error", e.getMessage());
			pagina_retorno = "redirect:/grupos/new";
		}		
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
		redirectAttributes.addFlashAttribute("success", "Grupo removico com sucesso");
		pagina_retorno = "redirect:/grupos";
		
		return pagina_retorno;
	}
	
	@PutMapping
	public String update(@Valid @ModelAttribute Grupo entityGrupo, BindingResult result, 
							RedirectAttributes redirectAttributes) {
		String pagina_retorno;		
		Grupo grupo = grupoService.findOne(entityGrupo.getId()).get();
		grupo.setNome(entityGrupo.getNome());
		try {
			grupoService.save(grupo);
			redirectAttributes.addFlashAttribute("success", "Grupo modificado com sucesso");
			pagina_retorno = "redirect:/grupos/" + grupo.getId() + "/sessoes";
		}catch (BusinessException e) {
			redirectAttributes.addFlashAttribute("error", e.getMessage());
			pagina_retorno = "redirect:/grupos/" + grupo.getId() + "/edit";
		}
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
		String pagina_retorno;
		User user = userService.findByEmail(principal.getName());
		Grupo grupo = grupoService.findOne(id).get();	
		boolean ehAdmin = grupo.getAdministrador().getId().equals(user.getId());
		List<User> moderadores = grupo.getModeradores();
		List<User> participantes = grupo.getParticipantes();
		participantes.removeAll(moderadores);
		model.addAttribute("grupo", grupo);
		model.addAttribute("moderadores", moderadores);
		model.addAttribute("participantes", participantes);
		model.addAttribute("ehAdmin", ehAdmin);
		pagina_retorno = "grupo/showParticipantes";	
		
		return pagina_retorno;
	}
	
	@GetMapping("/{id}/ranking")
	public String showRanking(Model model, @PathVariable("id") Integer id, Principal principal) {
		String pagina_retorno;
		Grupo grupo = grupoService.findOne(id).get();	
		User user = userService.findByEmail(principal.getName());
		boolean ehAdmin = grupo.getAdministrador().getId().equals(user.getId());
		List<Participacao> participacoes = grupo.getParticipacoes();
		model.addAttribute("grupo", grupo);
		model.addAttribute("participacoes", participacoes);
		model.addAttribute("ehAdmin", ehAdmin);
		pagina_retorno = "grupo/showRanking";	
		
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
		try {
			User participante = userService.findIfExistsByEmail(email);
			grupoService.addParticipacao(grupo, participante);
			redirectAttributes.addFlashAttribute("success", "Usuário adicionado com sucesso");
			pagina_retorno = "redirect:/grupos/" + grupo.getId() + "/participantes";
		} catch (BusinessException e) {
			redirectAttributes.addFlashAttribute("error", e.getMessage());
			pagina_retorno = "redirect:/grupos/" + grupo.getId() + "/addParticipante";
		}				
		return pagina_retorno;
	}
	
	@GetMapping("/{id}/rmvParticipante/{id_participante}")
	public String rmvParticipante(@PathVariable("id") Integer id, @PathVariable("id_participante")
									Integer id_participante, RedirectAttributes redirectAttributes) {
		String pagina_retorno;
		Grupo grupo = grupoService.findOne(id).get();
		User participante = userService.findOne(id_participante).get();
		grupoService.rmvParticipacao(grupo, participante);
		redirectAttributes.addFlashAttribute("success", "Usuário Removido com Sucesso");
		pagina_retorno = "redirect:/grupos/" + grupo.getId() + "/participantes";
		
		return pagina_retorno;
	}
	
	@GetMapping("/{id}/addModerador/{id_moderador}")
	public String addModerador(@PathVariable("id") Integer id, @PathVariable("id_moderador")
									Integer id_moderador, RedirectAttributes redirectAttributes) {
		String pagina_retorno;
		Grupo grupo = grupoService.findOne(id).get();
		User moderador = userService.findOne(id_moderador).get();
		grupoService.addModerador(grupo, moderador);
		redirectAttributes.addFlashAttribute("success", "Participante foi promovido para moderador");
		pagina_retorno = "redirect:/grupos/" + grupo.getId() +"/participantes";
		
		return pagina_retorno;
	}
	
	@GetMapping("/{id}/rmvModerador/{id_moderador}")
	public String rmvModerador(@PathVariable("id") Integer id, @PathVariable("id_moderador")
								Integer id_moderador, RedirectAttributes redirectAttributes) {
		String pagina_retorno;
		Grupo grupo = grupoService.findOne(id).get();
		User moderador = userService.findOne(id_moderador).get();
		grupoService.rmvModerador(grupo, moderador);
		redirectAttributes.addFlashAttribute("success", "Moderador foi rebaixado para apenas participante");
		pagina_retorno = "redirect:/grupos/" + grupo.getId() +"/participantes";
		
		return pagina_retorno;
	}
}





