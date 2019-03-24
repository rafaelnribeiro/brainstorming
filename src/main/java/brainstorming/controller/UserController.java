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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import brainstorming.model.User;
import brainstorming.service.UserService;
import brainstorming.util.exceptions.BusinessException;

@Controller
@RequestMapping("/usuario")
public class UserController {
	
	@Autowired
	private UserService usuarioService;
	
	@GetMapping("/cadastro")
	public String cadastrar(Model model, @ModelAttribute User entityUsuario) {
		return "usuario/form";
	}
	
	@PostMapping
	public String cadastrar(@Valid @ModelAttribute User entityUsuario, BindingResult bidingResult
							, RedirectAttributes redirectAttributes) {
		String pagina_retorno;
		try {
			usuarioService.save(entityUsuario);
			redirectAttributes.addFlashAttribute("success", "Cadastro realizado com sucesso");
			pagina_retorno = "redirect:/login/";
		} catch (BusinessException e) {
			redirectAttributes.addFlashAttribute("error", e.getMessage());
			pagina_retorno = "redirect:/usuario/cadastro/";
		}
		
		return pagina_retorno;
	}	
}
