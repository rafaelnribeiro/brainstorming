package brainstorming.controller;

import java.io.IOException;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import brainstorming.controller.estrutura_factory.DivisorFactory;
import brainstorming.controller.estrutura_factory.EstruturaFactory;
import brainstorming.model.Grupo;
import brainstorming.model.Sessao;
import brainstorming.model.Solicitacao;
import brainstorming.model.User;
import brainstorming.model.estrutura.Divisor;
import brainstorming.model.estrutura.Estrutura;
import brainstorming.service.GrupoService;
import brainstorming.service.SessaoService;
import brainstorming.service.SolicitacaoService;
import brainstorming.service.UserService;
import brainstorming.service.SolucaoStrategy.SolucaoMaisVotada;
import brainstorming.service.SolucaoStrategy.SolucaoStrategy;
import brainstorming.util.exceptions.BusinessException;

@Controller
@RequestMapping("/sessoes")
public class SessaoController {
	EstruturaFactory eFactory = new DivisorFactory();
	
	SolucaoStrategy eStrategy = new SolucaoMaisVotada();
	
	@Autowired SessaoService sessaoService;
	
	@Autowired GrupoService grupoService;
	
	@Autowired UserService userService;
	
	@Autowired SolicitacaoService solicitacaoService;
	
	@GetMapping("/{id}/show")
	public String show(Model model, @PathVariable("id") Integer id, Principal principal) {
		String pagina_retorno;
		Sessao sessao = sessaoService.findOne(id).get();		
		User user = userService.findByEmail(principal.getName());
		boolean ehAdmin = sessao.getGrupo().getAdministrador().getId() == user.getId();
		boolean ehModerador = ehAdmin || sessao.getGrupo().getModeradores().contains(user);
		model.addAttribute("ehModerador", ehModerador);
		model.addAttribute("sessao", sessao);
		
		//Parte específica de Divisor
		Divisor divisor = (Divisor)sessao.getEstrutura();
		model.addAttribute("divisor", divisor);
		
		pagina_retorno = "sessao/show";
		
		return pagina_retorno;
	}
	
	@GetMapping("/{id}/solicitacoes")
	public String showSolicitacoes(Model model, @PathVariable("id") Integer id, Principal principal) {
		Sessao sessao = sessaoService.findOne(id).get();
		List<Solicitacao> solicitacoes = solicitacaoService.findBySessao(id);
		User user = userService.findByEmail(principal.getName());
		boolean ehAdmin = sessao.getGrupo().getAdministrador().getId() == user.getId();
		boolean ehModerador = ehAdmin || sessao.getGrupo().getModeradores().contains(user);
		model.addAttribute("ehModerador", ehModerador);
		model.addAttribute("solicitacoes", solicitacoes);
		model.addAttribute("sessao", sessao);	
				
		return "sessao/showSolicitacoes";
	}
	
	@GetMapping("/{id}/finalizar")
	public String finalizar(Model model, @PathVariable("id") Integer id, 
							RedirectAttributes redirectAttributes) {
		Sessao sessao = sessaoService.findOne(id).get();
		try {
			sessaoService.finalizarSessao(sessao);
			redirectAttributes.addFlashAttribute("success", "Sessão finalizada");
		} catch (BusinessException e) {
			redirectAttributes.addFlashAttribute("error", e.getMessage());
		}		
		return "redirect:/sessoes/" + sessao.getId() + "/show";
	}
	
	@GetMapping(value = "/new")
	public String create(Model model, @RequestParam("id_grupo") Integer id_grupo,
							@ModelAttribute Sessao entitySessao, @ModelAttribute Grupo entityGrupo) {
		model.addAttribute("id_grupo", id_grupo);
		return "sessao/form";
	}
	
	@PostMapping
	public String create(@Valid @ModelAttribute Sessao entitySessao, Principal principal,
						@RequestParam("id_grupo") Integer id_grupo, @RequestParam("file") MultipartFile file, 
						BindingResult result, RedirectAttributes redirectAttributes) {
		String pagina_retorno;
		Sessao sessao = null;
		Estrutura estrutura = null;
		Grupo grupo = grupoService.findOne(id_grupo).get();
		entitySessao.setGrupo(grupo);
		
		try {
			estrutura = eFactory.create(file);
			entitySessao.setEstrutura(estrutura);
			sessao = sessaoService.save(entitySessao);
			redirectAttributes.addFlashAttribute("success", "Sessão adicionada com sucesso");
			pagina_retorno = "redirect:/sessoes/" + sessao.getId() + "/show";
		} catch (IOException e) {
			redirectAttributes.addFlashAttribute("error", e.getMessage());
			pagina_retorno = "redirect:/grupos/" + id_grupo + "/sessoes";
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
			sessaoService.save(sessao);
			redirectAttributes.addFlashAttribute("success", "Sessão atualizada com sucesso");
			pagina_retorno = "redirect:/sessoes/" + sessao.getId() + "/ideias";
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
