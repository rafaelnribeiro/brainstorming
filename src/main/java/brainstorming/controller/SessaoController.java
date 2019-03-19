package brainstorming.controller;

import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.List;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import brainstorming.model.Ideia;
import brainstorming.model.Sessao;
import brainstorming.model.User;
import brainstorming.service.ParticipanteJaAdicionadoException;
import brainstorming.service.SessaoService;
import brainstorming.service.UserService;
import brainstorming.service.UsuarioNaoEncontradoException;

@Controller
@RequestMapping("/sessoes")
public class SessaoController {
	
	@Autowired SessaoService sessaoService;
	
	@Autowired UserService userService;
	
	@GetMapping
	public String index(Model model, Principal principal) {
		User usuario = userService.findByEmail(principal.getName());
		List<Sessao> sessoes = usuario.getSessoes();
		List<Sessao> sessoesModeradas = usuario.getSessoesModeradas();
		model.addAttribute("sessoes", sessoes);
		model.addAttribute("sessoesModeradas", sessoesModeradas);
		return "sessao/index";
	}
	
	@GetMapping("/{id}/ideias")
	public String show(Model model, @PathVariable("id") Integer id, Principal principal) {
		String pagina_retorno = "redirect:/acessoNegado";
		User user = userService.findByEmail(principal.getName());
		if (id != null) {
			Sessao sessao = sessaoService.findOne(id).get();
			boolean ehModerador = sessao.getModerador().equals(user);
			boolean ehParticipante = sessao.getParticipantes().contains(user);
			if(ehModerador || ehParticipante) {
				model.addAttribute("sessao", sessao);
				List<Ideia> ideias = sessao.getIdeias();
				model.addAttribute("ideias", ideias);
				model.addAttribute("ehModerador", ehModerador);
				pagina_retorno = "sessao/showIdeias";
			}
		}
		
		return pagina_retorno;
		
	}
	
	@GetMapping(value = "/new")
	public String create(Model model, @ModelAttribute Sessao entitySessao) {
		return "sessao/form";
	}
	
	@PostMapping
	public String create(@Valid @ModelAttribute Sessao entitySessao, Principal principal,
						BindingResult result, RedirectAttributes redirectAttributes) {
		Sessao sessao = null;
		String pagina_retorno = "redirect:/sessoes/";
		
		try {
			User moderador = userService.findByEmail(principal.getName());
			entitySessao.setModerador(moderador);
			sessao = sessaoService.save(entitySessao);
			redirectAttributes.addFlashAttribute("success", MSG_SUCCESS_INSERT);
			pagina_retorno = pagina_retorno + sessao.getId() + "/participantes";
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
				Sessao sessao = sessaoService.findOne(id).get();
				User user = userService.findByEmail(principal.getName());
				if(sessao.getModerador().equals(user)) {
					model.addAttribute("sessao", sessao);
					pagina_retorno = "sessao/form";
				}				
			}
		}catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		
		return pagina_retorno;
	}
	
	@PutMapping
	public String update(@Valid @ModelAttribute Sessao entitySessao, BindingResult result,
						RedirectAttributes redirectAttributes, Principal principal) {
		String pagina_retorno = "redirect:/acessoNegado";
		try {
			Sessao sessao = sessaoService.findOne(entitySessao.getId()).get();
			User user = userService.findByEmail(principal.getName());
			if(sessao.getModerador().equals(user)) {
				sessao.setDetalhes(entitySessao.getDetalhes());
				sessao.setTema(entitySessao.getTema());
				sessaoService.save(sessao);
				redirectAttributes.addFlashAttribute("success", MSG_SUCCESS_EDIT);
				pagina_retorno = "redirect:/sessoes/" + sessao.getId() + "/ideias";
			}
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", MSG_ERROR_EDIT);
			e.printStackTrace();
		}
		
		return pagina_retorno;
	}
	
	@RequestMapping("/{id}/delete")
	public String delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes, Principal principal) {
		String pagina_retorno = "redirect:/acessoNegado";
		try {
			if(id != null) {
				Sessao sessao = sessaoService.findOne(id).get();
				User user = userService.findByEmail(principal.getName());
				if(sessao.getModerador().equals(user)) {
					sessaoService.delete(sessao);
					redirectAttributes.addFlashAttribute("success", MSG_SUCCESS_DELETE);
					pagina_retorno = "redirect:/sessoes/";
				}		
			}
		} catch (Exception e) {
			redirectAttributes.addAttribute("error", MSG_ERROR_DELETE);
			e.printStackTrace();
		}
		return pagina_retorno;
	}
	
	@GetMapping("/{id}/participantes")
	public String showParticipantes(Model model, @PathVariable("id") Integer id, Principal principal) {
		String pagina_retorno = "redirect:/acessoNegado";
		User user = userService.findByEmail(principal.getName());
		if (id != null) {
			Sessao sessao = sessaoService.findOne(id).get();
			
			boolean ehModerador = sessao.getModerador().equals(user);
			boolean ehParticipante = sessao.getParticipantes().contains(user);
			if(ehModerador || ehParticipante) {
				model.addAttribute("sessao", sessao);
				List<User> participantes = sessao.getParticipantes();
				User moderador = sessao.getModerador();
				model.addAttribute("moderador", moderador);
				model.addAttribute("participantes", participantes);
				model.addAttribute("ehModerador", ehModerador);
				pagina_retorno = "sessao/showParticipantes";
			}
		}
		return pagina_retorno;
	}
	
	@GetMapping("/{id}/addParticipante")
	public String addParticipante(Model model, @PathVariable("id") Integer id, Principal principal) {
		String pagina_retorno = "redirect:/acessoNegado";
		if (id != null) {
			Sessao sessao = sessaoService.findOne(id).get();
			User user = userService.findByEmail(principal.getName());
			if(sessao.getModerador().equals(user)) {
				model.addAttribute("sessao", sessao);
				pagina_retorno = "sessao/addParticipante";
			}
		}	
		return pagina_retorno;
	}
	
	@PostMapping("/{id}/addParticipante")
	public String addParticipante(@Valid @ModelAttribute("email") String email, @PathVariable("id") Integer id,
								Principal principal, BindingResult result,
								RedirectAttributes redirectAttributes)throws UnsupportedEncodingException {
		String pagina_retorno = "redirect:/acessoNegado";
		try {
			if (id != null) {
				Sessao sessao = sessaoService.findOne(id).get();
				User user = userService.findByEmail(principal.getName());
				if(sessao.getModerador().equals(user)) {
					User participante = userService.findByEmail(email);
					pagina_retorno = "redirect:/sessoes/" + sessao.getId() +"/addParticipante";
					if(participante != null) {
						sessaoService.addParticipante(sessao, participante);
						redirectAttributes.addFlashAttribute("success", MSG_SUCCESS_ADD_USER);					
					}else {
						redirectAttributes.addFlashAttribute("error", MSG_ERROR_FIND_USER);
					}
					pagina_retorno = "redirect:/sessoes/" + sessao.getId() +"/addParticipante";
				}
			}
		}catch (ParticipanteJaAdicionadoException e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("error", MSG_ALREADY_ADDED_USER);
		}catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("error", MSG_ERROR_ADD_USER);
		}catch (Throwable e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("error", MSG_ERROR_ADD_USER);
		}
		return pagina_retorno;
	}
	
	@RequestMapping("/{id}/rmvParticipante/{id_participante}")
	public String rmvParticipante(@PathVariable("id") Integer id, @PathVariable("id_participante") Integer id_participante, 
								RedirectAttributes redirectAttributes, Principal principal) {
		String pagina_retorno = "redirect:/acessoNegado";
		try {
			if(id != null) {
				Sessao sessao = sessaoService.findOne(id).get();
				User user = userService.findByEmail(principal.getName());
				if(sessao.getModerador().equals(user)) {
					if(id_participante != null) {
						User participante = userService.findOne(id_participante).get();	
						sessao.getParticipantes().remove(participante);
						sessaoService.save(sessao);
						redirectAttributes.addFlashAttribute("success", MSG_SUCCESS_RMV_USER);
						pagina_retorno = "redirect:/sessoes/" + sessao.getId() + "/participantes"; 
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("error", MSG_ERROR_RMV_USER);
		}catch (Throwable e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("error", MSG_ERROR_RMV_USER);
		}
		
		return pagina_retorno;
	}
	
	private static final String MSG_SUCCESS_INSERT = "Sessão Adicionada com Sucesso.";
	private static final String MSG_ERROR_INSERT = "Erro ao adicionar Sessão.";
	private static final String MSG_SUCCESS_EDIT = "Sessão Modificada com Sucesso.";
	private static final String MSG_ERROR_EDIT = "Erro ao Modificar Sessão.";
	private static final String MSG_SUCCESS_DELETE = "Sessão Removida com Sucesso.";
	private static final String MSG_ERROR_DELETE = "Erro ao remover Sessão.";
	private static final String MSG_SUCCESS_ADD_USER = "Usuário Adicionado à Sessão.";
	private static final String MSG_ERROR_ADD_USER = "Erro ao adicionar Usuário.";
	private static final String MSG_ERROR_FIND_USER = "Usuário não encontrado.";
	private static final String MSG_ALREADY_ADDED_USER = "Usuário já Pertence à Sessão";
	private static final String MSG_SUCCESS_RMV_USER = "Usuário Removido com Sucesso.";
	private static final String MSG_ERROR_RMV_USER = "Erro ao Remover Usuário.";
}
