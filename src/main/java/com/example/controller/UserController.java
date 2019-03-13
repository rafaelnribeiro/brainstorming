package com.example.controller;

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

import com.example.model.User;
import com.example.service.EmailJaCadastradoException;
import com.example.service.UserService;

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
		String pagina_retorno = "redirect:/usuario/cadastro/";
		try {
			usuarioService.save(entityUsuario);
			redirectAttributes.addFlashAttribute("success", MSG_SUCCESS_REGISTER);
			pagina_retorno = "redirect:/login/";
		
		} catch (EmailJaCadastradoException e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("error", MSG_ERROR_EMAIL);
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("error", MSG_ERROR_REGISTER);
		} catch (Throwable e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("error", MSG_ERROR_REGISTER);
		}
		
		return pagina_retorno;
	}
	private static final String MSG_SUCCESS_REGISTER = "Cadastro Realizado com Sucesso.";
	private static final String MSG_ERROR_REGISTER = "Erro ao Realizar Cadastro.";
	private static final String MSG_ERROR_EMAIL = "Email já cadastrado.";
	
}
