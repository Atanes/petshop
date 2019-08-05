package com.iridiumit.gestaopetshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.iridiumit.gestaopetshop.model.Animal;
import com.iridiumit.gestaopetshop.model.Servico;
import com.iridiumit.gestaopetshop.repository.Animais;
import com.iridiumit.gestaopetshop.repository.Servicos;

@Controller
@RequestMapping("/atendimento/servicos")
public class ServicosController {

	@Autowired
	private Animais animais;

	@Autowired
	private Servicos servicos;

	@GetMapping
	public ModelAndView listar() {

		ModelAndView modelAndView = new ModelAndView("atendimento/servico/lista-servicos");

		modelAndView.addObject("servicos", servicos.findAllByOrderByDescricao());
		return modelAndView;
	}

	@GetMapping("/incluir/{id}")
	public ModelAndView incluir(Servico servico, @PathVariable("id") Long idAnimal) {
		ModelAndView modelAndView = new ModelAndView("atendimento/servico/cadastro-servicos");

		Animal a = animais.findOne(idAnimal);

		modelAndView.addObject(a);

		return modelAndView;
	}

}
