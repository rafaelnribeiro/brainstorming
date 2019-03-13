package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.service.UserService;

@Controller
public class LoginController {
	
	@Autowired UserService userService;

	@RequestMapping("/login")
	public String login() {	
		return "login/form";
	}
	
	@RequestMapping("/acessoNegado")
	public String acessoNegado() {
		return "login/acessoNegado";
	}
}
