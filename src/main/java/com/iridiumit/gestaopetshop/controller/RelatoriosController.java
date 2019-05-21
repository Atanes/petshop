package com.iridiumit.gestaopetshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/ordemServico/relatorios")

public class RelatoriosController {

	@GetMapping
	public ModelAndView diario() {
		ModelAndView modelAndView = new ModelAndView("ordemServico/relatorios/diario");

		//modelAndView.addObject("clientes", clientes.findAll());
		return modelAndView;
	}
	
	
}
