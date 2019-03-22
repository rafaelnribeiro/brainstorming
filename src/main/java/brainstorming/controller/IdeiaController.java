package brainstorming.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.hibernate.service.spi.ServiceException;
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

import brainstorming.model.Ideia;
import brainstorming.model.Sessao;
import brainstorming.model.User;
import brainstorming.service.IdeiaService;
import brainstorming.service.SessaoService;
import brainstorming.service.UserService;

@Controller
@RequestMapping("/ideias")
public class IdeiaController {

	@Autowired
	private IdeiaService ideiaService;
	
	@Autowired
	private SessaoService sessaoService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/{id}")
	public String show(Model model,  @PathVariable("id") Integer id, Principal principal) {
		String pagina_retorno = "redirect:/acessoNegado";
		if(id != null) {
			Ideia ideia = ideiaService.findOne(id).get();
			User user = userService.findByEmail(principal.getName());
				model.addAttribute("ideia", ideia);
				model.addAttribute("ehAutor", ideia.getAutor().equals(user));
				pagina_retorno = "ideia/show";
		}
		return pagina_retorno;
	}
	
	@GetMapping(value = "/new")
	public String create(Model model, @RequestParam("id_sessao") Integer id_sessao, 
							@ModelAttribute Ideia entityIdeia, @ModelAttribute Sessao entitySessao) {
		if(id_sessao != null) {
			model.addAttribute("id_sessao", id_sessao);
		}
		
		return "ideia/form";
	}
	
	@PostMapping
	public String create(@Valid @ModelAttribute Ideia entityIdeia, 
						@RequestParam("id_sessao") Integer id_sessao, Principal principal,
						BindingResult result, RedirectAttributes redirectAttributes) {
		
		String pagina_retorno = "redirect:/acessoNegado";
		
		try {
			if(id_sessao != null){
				Sessao sessao = sessaoService.findOne(id_sessao).get();
				User user = userService.findByEmail(principal.getName());
					entityIdeia.setSessao(sessao);
					entityIdeia.setAutor(user);
					Ideia ideia = ideiaService.save(entityIdeia);
					redirectAttributes.addFlashAttribute("success", MSG_SUCCESS_INSERT);
					pagina_retorno = "redirect:/ideias/" + ideia.getId();		
			}
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("error", MSG_ERROR_INSERT);
		} catch (Throwable e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("error", MSG_ERROR_INSERT);
		}
		
		return pagina_retorno;
	}
	
	@RequestMapping("/{id}/edit")
	public String update(Model model, @PathVariable("id") Integer id, Principal principal) {

		String pagina_retorno = "redirect:/acessoNegado";
		
		try {
			if(id != null) {
				User user = userService.findByEmail(principal.getName());
				Ideia ideia = ideiaService.findOne(id).get();
				Sessao sessao = ideia.getSessao();
				boolean ehParticipante = sessao.getGrupo().getParticipantes().contains(user);
				boolean ehAdmin = sessao.getGrupo().getAdministrador().getId().equals(user.getId());
				boolean ehAutor = ideia.getAutor().equals(user);
				if((ehParticipante || ehAdmin) && ehAutor) {
					model.addAttribute("ideia", ideia);
					model.addAttribute("id_sessao", sessao.getId());
					pagina_retorno = "ideia/form";
				}
			}
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	
		return pagina_retorno;
	}	
	
	@PutMapping
	public String update(@Valid @ModelAttribute Ideia entityIdeia, BindingResult result,
						@RequestParam("id_sessao") Integer id_sessao, Principal principal,
						RedirectAttributes redirectAttributes) {
		String pagina_retorno = "redirect:/acessoNegado";
		
		try {
			Ideia ideia = ideiaService.findOne(entityIdeia.getId()).get();
			Sessao sessao = ideia.getSessao();
			User user = userService.findByEmail(principal.getName());				
			boolean ehParticipante = sessao.getGrupo().getParticipantes().contains(user);
			boolean ehAdmin = sessao.getGrupo().getAdministrador().getId().equals(user.getId());
			boolean ehAutor = ideia.getAutor().equals(user);
			if((ehParticipante || ehAdmin) && ehAutor) {
				entityIdeia.setSessao(ideia.getSessao());
				entityIdeia.setAutor(ideia.getAutor());
				ideiaService.save(entityIdeia);
				redirectAttributes.addFlashAttribute("success", MSG_SUCCESS_EDIT);
				pagina_retorno = "redirect:/ideias/" + ideia.getId();			
			}
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("error", MSG_ERROR_EDIT);
		} catch (Throwable e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("error", MSG_ERROR_EDIT);
		}
		
		return pagina_retorno;
	}
	
	@RequestMapping("/{id}/delete")
	public String delete(@PathVariable("id") Integer id, Principal principal,
							RedirectAttributes redirectAttributes) {
		String pagina_retorno = "redirect:/acessoNegado";
		
		try {
			if(id != null) {
				User user = userService.findByEmail(principal.getName());
				Ideia ideia = ideiaService.findOne(id).get();
				Sessao sessao = ideia.getSessao();
				boolean ehParticipante = sessao.getGrupo().getParticipantes().contains(user);
				boolean ehAdmin = sessao.getGrupo().getAdministrador().getId().equals(user.getId());
				boolean ehAutor = ideia.getAutor().equals(user);
				if((ehParticipante || ehAdmin) && ehAutor) {
					ideiaService.delete(ideia);
					redirectAttributes.addFlashAttribute("success", MSG_SUCCESS_DELETE);
					pagina_retorno = "redirect:/sessoes/" + sessao.getId() + "/ideias";
				}				
			}			
		}
		catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", MSG_ERROR_DELETE);
			e.printStackTrace();
		}	
		catch (Throwable e) {
			redirectAttributes.addFlashAttribute("error", MSG_ERROR_DELETE);
			e.printStackTrace();
		}
		
		return pagina_retorno;
	}
	
	private static final String MSG_SUCCESS_INSERT = "Ideia Adicionada com Sucesso.";
	private static final String MSG_ERROR_INSERT = "Erro ao Adicionar Ideia.";
	private static final String MSG_SUCCESS_EDIT = "Ideia Modificada com Sucesso.";
	private static final String MSG_ERROR_EDIT = "Erro ao Modificar Ideia.";
	private static final String MSG_SUCCESS_DELETE = "Ideia Removida com Sucesso.";
	private static final String MSG_ERROR_DELETE = "Erro ao Remover Ideia.";
}
