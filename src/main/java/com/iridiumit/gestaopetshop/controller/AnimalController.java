package com.iridiumit.gestaopetshop.controller;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.iridiumit.gestaopetshop.model.Animal;
import com.iridiumit.gestaopetshop.model.Cliente;
import com.iridiumit.gestaopetshop.repository.Animais;
import com.iridiumit.gestaopetshop.repository.Clientes;
import com.iridiumit.gestaopetshop.repository.filtros.AnimalFiltro;


@Controller
@RequestMapping("/clientes/animais")
public class AnimalController {
	
	@Autowired
	private Clientes clientes;
	
	@Autowired
	private Animais animais;
	
	@GetMapping
	public ModelAndView listar(@ModelAttribute("filtro") AnimalFiltro filtro) {
		
		String nome = filtro.getNome() == null ? "%" : filtro.getNome();
		
		ModelAndView modelAndView = new ModelAndView("animais/lista-animais");

		modelAndView.addObject("animais", animais.findByNomeContainingIgnoreCase(nome));
		return modelAndView;
	}

	@PostMapping("/excluir/{id}")
	public String remover(@PathVariable Long id, RedirectAttributes attributes) {

		Cliente c = animais.findOne(id).getCliente();

		animais.delete(id);

		attributes.addFlashAttribute("mensagem", "Animal excluido com sucesso!!");

		return "redirect:/atendimento/clientes/selecao/" + c.getId();
	}

	@GetMapping("/{id}")
	public ModelAndView editar(@PathVariable Long id) {

		return novo(animais.findOne(id));
	}

	@GetMapping("/novo")
	public ModelAndView novo(Animal animal) {
		ModelAndView modelAndView = new ModelAndView("animais/cadastro-animal2");

		modelAndView.addObject(animal);
		
		//modelAndView.addObject("clientes", clientes.findAll());

		return modelAndView;
	}
	
	@GetMapping("/incluirAnimal/{id}")
	public ModelAndView incluirAnimal(@PathVariable Long id) {
		ModelAndView modelAndView = new ModelAndView("animais/cadastro-animal2");
		
		Cliente c = clientes.findOne(id);
		
		Animal a = new Animal();
		
		a.setCliente(c);
		
		modelAndView.addObject(a);

		return modelAndView;
	}

	@GetMapping("/novo/{id}")
	public ModelAndView incluir(Animal animal, @PathVariable("id") Long id) {
		ModelAndView modelAndView = new ModelAndView("animais/cadastro-animal");

		modelAndView.addObject(animal);
		
		modelAndView.addObject("clientes", clientes.findOne(id));

		return modelAndView;
	}
	
	@PostMapping("/salvar")
	public ModelAndView salvar(@Valid Animal animal, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return novo(animal);
		}
		
		animal.setData_cadastro(new Date());

		animais.save(animal);

		attributes.addFlashAttribute("mensagem", "Animal salvo com sucesso!!");

		return new ModelAndView("redirect:/atendimento/clientes/selecao/" + animal.getCliente().getId());
	}

}
