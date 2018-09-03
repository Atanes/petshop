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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.iridiumit.controleos.model.Cliente;
import com.iridiumit.controleos.model.Equipamento;
import com.iridiumit.controleos.repository.Clientes;
import com.iridiumit.controleos.repository.Equipamentos;

@Controller
@RequestMapping("/equipamentos")
public class EquipamentoController {
	
	@Autowired
	private Equipamentos equipamentos;
	
	@Autowired
	private Clientes clientes;
	
	@GetMapping
	public ModelAndView listar() {
		ModelAndView modelAndView = new ModelAndView("equipamentos/lista-equipamentos");

		modelAndView.addObject("equipamentos", equipamentos.findAll());
		return modelAndView;
	}
	
	@DeleteMapping("/{id}")
	public String remover(@PathVariable Long id, RedirectAttributes attributes) {
		
		equipamentos.delete(id);

		attributes.addFlashAttribute("mensagem", "Equipamento excluido com sucesso!!");
		
		return "redirect:/equipamentos";
	}

	@GetMapping("/{id}")
	public ModelAndView editar(@PathVariable Long id) {

		return novo(equipamentos.findOne(id));
	}

	@GetMapping("/novo")
	public ModelAndView novo(Equipamento equipamento) {
		ModelAndView modelAndView = new ModelAndView("atendimento/equipamentos/cadastro-equipamento");

		modelAndView.addObject(equipamento);
		modelAndView.addObject("clientes", clientes.findAll());

		return modelAndView;
	}

	@PostMapping("/salvar")
	public ModelAndView salvar(@Valid Equipamento equipamento, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return novo(equipamento);
		}

		equipamentos.save(equipamento);

		attributes.addFlashAttribute("mensagem", "Equipamento salvo com sucesso!!");

		return new ModelAndView("redirect:/equipamentos/novo");
	}
	
	@GetMapping("/selecao/{id}")
	public ModelAndView SelecaoPorCliente(@PathVariable Long id) {
		
		Cliente c = clientes.findOne(id);
		ModelAndView modelAndView = new ModelAndView("tecnico/lista-equipamentos");

		modelAndView.addObject("equipamentos", equipamentos.findByCliente(c));
		modelAndView.addObject("mensagem", "Equipamentos do cliente " + c.getNome());
		return modelAndView;

	}
}
