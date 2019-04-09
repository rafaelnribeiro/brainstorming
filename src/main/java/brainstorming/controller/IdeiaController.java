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

import brainstorming.model.Ideia;
import brainstorming.model.Sessao;
import brainstorming.model.Sugestao;
import brainstorming.model.User;
import brainstorming.model.Comentario;
import brainstorming.service.IdeiaService;
import brainstorming.service.SessaoService;
import brainstorming.service.SugestaoService;
import brainstorming.service.UserService;
import brainstorming.service.ComentarioService;
import brainstorming.util.exceptions.BusinessException;

@Controller
@RequestMapping("/ideias")
public class IdeiaController {

	@Autowired
	private IdeiaService ideiaService;
	
	@Autowired
	private SessaoService sessaoService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ComentarioService comentarioService;
	
	@Autowired SugestaoService sugestaoService;
	
	@GetMapping("/{id}")
	public String show(Model model,  @PathVariable("id") Integer id, Principal principal) {
		String pagina_retorno;
		Ideia ideia = ideiaService.findOne(id).get();
		User user = userService.findByEmail(principal.getName());
		model.addAttribute("ideia", ideia);
		model.addAttribute("ehAutor", ideia.getAutor().equals(user));
		model.addAttribute("ehVotante", ideia.getVotantes().contains(user));
		model.addAttribute("numVotos", ideia.getVotantes().size());
		pagina_retorno = "ideia/show";
		
		return pagina_retorno;
	}
	
	@GetMapping(value = "/new")
	public String create(Model model, @RequestParam("id_sessao") Integer id_sessao, 
							@ModelAttribute Ideia entityIdeia, @ModelAttribute Sessao entitySessao) {
		model.addAttribute("id_sessao", id_sessao);
		
		return "ideia/form";
	}
	
	@PostMapping
	public String create(@Valid @ModelAttribute Ideia entityIdeia, 
						@RequestParam("id_sessao") Integer id_sessao, Principal principal,
						BindingResult result, RedirectAttributes redirectAttributes) {		
		String pagina_retorno;
		Sessao sessao = sessaoService.findOne(id_sessao).get();
		User user = userService.findByEmail(principal.getName());
		entityIdeia.setSessao(sessao);
		entityIdeia.setAutor(user);
		Ideia ideia;
		try {
			ideia = ideiaService.save(entityIdeia);
			redirectAttributes.addFlashAttribute("success", "Ideia criada com sucesso");
			pagina_retorno = "redirect:/ideias/" + ideia.getId();
		} catch (BusinessException e) {
			redirectAttributes.addFlashAttribute("error", e.getMessage());
			pagina_retorno = "redirect:/sessoes/" + sessao.getId() + "/ideias";
		}
		
		
		return pagina_retorno;
	}
	
	@RequestMapping("/{id}/edit")
	public String update(Model model, @PathVariable("id") Integer id, Principal principal) {
		String pagina_retorno;
		Ideia ideia = ideiaService.findOne(id).get();
		Sessao sessao = ideia.getSessao();
		model.addAttribute("ideia", ideia);
		model.addAttribute("id_sessao", sessao.getId());
		pagina_retorno = "ideia/form";
	
		return pagina_retorno;
	}	
	
	@PutMapping
	public String update(@Valid @ModelAttribute Ideia entityIdeia, BindingResult result,
						@RequestParam("id_sessao") Integer id_sessao, Principal principal,
						RedirectAttributes redirectAttributes) {
		String pagina_retorno;		
		Ideia ideia = ideiaService.findOne(entityIdeia.getId()).get();	
		entityIdeia.setSessao(ideia.getSessao());
		entityIdeia.setAutor(ideia.getAutor());
		try {
			ideia = ideiaService.save(entityIdeia);
			redirectAttributes.addFlashAttribute("success", "Ideia atualizada com sucesso");
			pagina_retorno = "redirect:/ideias/" + ideia.getId();
		} catch (BusinessException e) {
			redirectAttributes.addFlashAttribute("error", e.getMessage());
			pagina_retorno = "redirect:/sessoes/" + ideia.getSessao().getId() + "/ideias";
		}
		
		return pagina_retorno;
	}
	
	@RequestMapping("/{id}/delete")
	public String delete(@PathVariable("id") Integer id, Principal principal,
							RedirectAttributes redirectAttributes) {
		String pagina_retorno;
		Ideia ideia = ideiaService.findOne(id).get();
		Sessao sessao = ideia.getSessao();
		ideiaService.delete(ideia);
		redirectAttributes.addFlashAttribute("success", "Ideia removida com sucesso");
		pagina_retorno = "redirect:/sessoes/" + sessao.getId() + "/ideias";
		
		return pagina_retorno;
	}
	
	@RequestMapping("/{id}/vote")
	public String vote(Model model, @PathVariable("id") Integer id, Principal principal,
							RedirectAttributes redirectAttributes) {
		User votante = userService.findByEmail(principal.getName());
		Ideia ideia = ideiaService.findOne(id).get();
		try {
			ideiaService.vote(ideia, votante);
			redirectAttributes.addFlashAttribute("success",	"Voto realizado com sucesso"); 
		} catch (BusinessException e) {
			redirectAttributes.addFlashAttribute("error", e.getMessage()); 
		}
		return "redirect:/ideias/" + ideia.getId();	
	}
	
	@RequestMapping("/{id}/unvote")
	public String unvote(Model model, @PathVariable("id") Integer id, Principal principal,
							RedirectAttributes redirectAttributes) {
		User votante = userService.findByEmail(principal.getName());
		Ideia ideia = ideiaService.findOne(id).get();
		try {
			ideiaService.unvote(ideia, votante);
			redirectAttributes.addFlashAttribute("success",	"Voto cancelado com sucesso"); 
		} catch (BusinessException e) {
			redirectAttributes.addFlashAttribute("error", e.getMessage()); 
		}
		return "redirect:/ideias/" + ideia.getId();	
	}
	
	@RequestMapping("/{id}/comment")
	public String comment(Model model, @PathVariable("id") Integer id, @ModelAttribute Comentario comment) {
		return "/ideia/comente";
	}
	
	@PostMapping("/{id}/comment")
	public String comment( @PathVariable("id") Integer id, @Valid @ModelAttribute Comentario comment, 
						BindingResult bindingResult, RedirectAttributes redirectAttributes, Principal principal) {
		Ideia ideia = ideiaService.findOne(id).get();
		User user = userService.findByEmail(principal.getName());
		comment.setAutor(user);
		comment.setIdeia(ideia);
		
		try {
			comentarioService.save(comment);			
			redirectAttributes.addFlashAttribute("success", "Comentario adicionado com sucesso");
		} catch (BusinessException e) {
			redirectAttributes.addFlashAttribute("error", e.getMessage());
		}
		
				
		return "redirect:/ideias/"+id;
	}
	
	@GetMapping("/{id}/sugestoes")
	public String showSuggestion(@PathVariable("id") Integer id, Model model) {
		Ideia ideia = ideiaService.findOne(id).get();
		List<Sugestao> sugestao = ideia.getSugestoes();
		model.addAttribute("sugestoes", sugestao);
		
		
		return "ideia/showSugestoes";
	}
	
	@RequestMapping("/{id}/suggest")
	public String suggest(Model model, @PathVariable("id") Integer id, @ModelAttribute Sugestao sugestao) {
		return "/ideia/sugestaoForm";
	}
	
	@PostMapping("/{id}/suggest")
	public String suggest( @PathVariable("id") Integer id, @Valid @ModelAttribute Sugestao sugestao, 
						BindingResult bindingResult, RedirectAttributes redirectAttributes, Principal principal) {
		Ideia ideia = ideiaService.findOne(id).get();
		User user = userService.findByEmail(principal.getName());
		sugestao.setAutor(user);
		sugestao.setIdeia(ideia);
		
		try {
			sugestaoService.save(sugestao);			
			redirectAttributes.addFlashAttribute("success", "Sugestão adicionada com sucesso");
		} catch (BusinessException e) {
			redirectAttributes.addFlashAttribute("error", e.getMessage());
		}
		
				
		return "redirect:/ideias/"+id;
	}

}
