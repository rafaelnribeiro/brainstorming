package brainstorming.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import brainstorming.service.GrupoService;

@Controller
@RequestMapping("/grupos")
public class GrupoController {

	@Autowired GrupoService grupoService;
}
