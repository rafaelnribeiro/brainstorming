package brainstorming.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import brainstorming.model.Ideia;
import brainstorming.model.Solicitacao;
import brainstorming.model.Sugestao;
import brainstorming.service.SolicitacaoService;
import brainstorming.service.SugestaoService;

@Controller
@RequestMapping("/solicitacoes")
public class SolicitacaoController {

	@Autowired SolicitacaoService solicitacaoService;
	@Autowired SugestaoService sugestaoService;
	
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
		solicitacaoService.save(solicitacao);
		redAtt.addFlashAttribute("success", "Solicitação salva no sistema, aguardando análise por moderador");
		pagina_retorno = "redirect:/ideias/" + ideia.getId();
		
		return pagina_retorno;
	}
	
}
