package brainstorming.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import brainstorming.model.Ideia;
import brainstorming.model.Solicitacao;
import brainstorming.model.Sugestao;
import brainstorming.service.IdeiaService;
import brainstorming.service.SolicitacaoService;
import brainstorming.service.SugestaoService;
import brainstorming.util.exceptions.BusinessException;

@Controller
@RequestMapping("/solicitacoes")
public class SolicitacaoController {

	@Autowired SolicitacaoService solicitacaoService;
	@Autowired SugestaoService sugestaoService;
	@Autowired IdeiaService ideiaService;
	
	@GetMapping("/{id}")
	public String show(Model model, @PathVariable("id") Integer id) {
		Solicitacao sol = solicitacaoService.findOne(id).get();
		model.addAttribute("solicitacao", sol);
		
		return "solicitacao/show";
	}
	
	@GetMapping(value = "/new")
	public String create(Model model, @ModelAttribute Solicitacao solicitacao, @ModelAttribute Ideia ideia, 
						@RequestParam("id_sugestao") Integer id_sugestao) {
		model.addAttribute("id_sugestao", id_sugestao);		
		model.addAttribute("id_ideia", ideia);
		return "solicitacao/form";
	}
	
	@PostMapping
	public String create(@Valid @ModelAttribute Solicitacao solicitacao, BindingResult result, 
						@RequestParam("id_sugestao") Integer id_sugestao, RedirectAttributes redAtt) {
		String pagina_retorno;
		Sugestao sug = sugestaoService.findOne(id_sugestao).get();
		Ideia ideia = sug.getIdeia();
		solicitacao.setSugestao(sug);		
		solicitacao.setIdeia(ideia);
		try {
			solicitacaoService.save(solicitacao);
			redAtt.addFlashAttribute("success", "Solicitação salva no sistema, aguardando análise por moderador");
			pagina_retorno = "redirect:/ideias/" + ideia.getId();
		} catch (BusinessException e) {
			redAtt.addFlashAttribute("error", e.getMessage());
			pagina_retorno = "redirect:/ideias/" + ideia.getId();
		}		
		return pagina_retorno;
	}
	
	@GetMapping("/{id}/aprovar")
	public String approve(@PathVariable("id") Integer id, RedirectAttributes redAtt) {
		String pagina_retorno;
		Solicitacao sol = solicitacaoService.findOne(id).get();
		Ideia ideia = ideiaService.findOne(sol.getIdeia().getId()).get();
		ideia.setTitulo(sol.getNovoTitulo());
		ideia.setDescricao(sol.getNovaDescricao());
		try {
			//System.out.println("TITULO: " + ideiaService.findOne(ideia.getId()).get().getTitulo());
			ideiaService.save(ideia);
			solicitacaoService.delete(sol);
			redAtt.addFlashAttribute("success", "Solicitação aprovada, a ideia foi modificada conforme especificado");
			pagina_retorno = "redirect:/sessoes/" + ideia.getSessao().getId() + "/solicitacoes";
		} catch (Exception e) {
			redAtt.addFlashAttribute("error", e.getMessage());
			pagina_retorno = "redirect:/solicitacoes/" + sol.getId();
		}	
		return pagina_retorno;
	}
	@GetMapping("/{id}/recusar")
	public String deny(@PathVariable("id") Integer id, RedirectAttributes redAtt) {
		Solicitacao sol = solicitacaoService.findOne(id).get();
		int id_sessao = sol.getIdeia().getSessao().getId();
		solicitacaoService.delete(sol);
		redAtt.addFlashAttribute("success", "Solicitação recusada");
		return "redirect:/sessoes/" + id_sessao + "/solicitacoes";
	}
	
}
