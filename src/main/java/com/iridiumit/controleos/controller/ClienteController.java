package com.iridiumit.controleos.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.iridiumit.controleos.model.Cliente;
import com.iridiumit.controleos.repository.Clientes;

@Controller
@RequestMapping(method = RequestMethod.GET, path = "/atendimento/clientes")
public class ClienteController {
	
	@Autowired
	private Clientes clientes;
	
	@GetMapping
	public ModelAndView listar() {
		ModelAndView modelAndView = new ModelAndView("orcamento/lista-clientes");

		modelAndView.addObject("clientes", clientes.findAll());
		return modelAndView;
	}
	
	@DeleteMapping("/{id}")
	public String remover(@PathVariable Long id, RedirectAttributes attributes) {
		
		clientes.delete(id);

		attributes.addFlashAttribute("mensagem", "Cliente excluido com sucesso!!");
		
		return "redirect:/atendimento/clientes";
	}

	@GetMapping("/{id}")
	public ModelAndView editar(@PathVariable Long id) {

		return novo(clientes.findOne(id));
	}

	@GetMapping("/novo")
	public ModelAndView novo(Cliente cliente) {
		ModelAndView modelAndView = new ModelAndView("atendimento/clientes/cadastro-cliente");

		modelAndView.addObject(cliente);

		return modelAndView;
	}

	@PostMapping("/salvar")
	public ModelAndView salvar(@Valid Cliente cliente, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return novo(cliente);
		}

		clientes.save(cliente);

		attributes.addFlashAttribute("mensagem", "Cliente salvo com sucesso!!");

		return new ModelAndView("redirect:/atendimento/clientes/novo");
	}
}
