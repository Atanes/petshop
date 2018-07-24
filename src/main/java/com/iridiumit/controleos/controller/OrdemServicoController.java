package com.iridiumit.controleos.controller;

import java.util.Date;

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

import com.iridiumit.controleos.model.OrdemServico;
import com.iridiumit.controleos.repository.Clientes;
import com.iridiumit.controleos.repository.Equipamentos;
import com.iridiumit.controleos.repository.OrdensServico;

@Controller
@RequestMapping("/atendimento")
public class OrdemServicoController {
	
	@Autowired
	private OrdensServico ordensServico;
	
	@Autowired
	private Clientes clientes;
	
	@Autowired
	private Equipamentos equipamentos;
		
	
	@GetMapping
	public ModelAndView listar() {
		ModelAndView modelAndView = new ModelAndView("tecnico/lista-ordemServico");

		modelAndView.addObject("ordensServico", ordensServico.findAll());
		return modelAndView;
	}
	
	@DeleteMapping("/{id}")
	public String remover(@PathVariable Long id, RedirectAttributes attributes) {
		
		ordensServico.delete(id);

		attributes.addFlashAttribute("mensagem", "Ordem de Servico excluida com sucesso!!");
		
		return "redirect:/atendimento";
	}

	@GetMapping("/{id}")
	public ModelAndView editar(@PathVariable Long id) {

		return novo(ordensServico.findOne(id));
	}

	@GetMapping("/novo")
	public ModelAndView novo(OrdemServico ordemServico) {
		ModelAndView modelAndView = new ModelAndView("atendimento/cadastro-ordemServico");

		modelAndView.addObject(ordemServico);
		modelAndView.addObject("clientes", clientes.findAll());
		modelAndView.addObject("equipamentos", equipamentos.findAll());

		return modelAndView;
	}

	@PostMapping("/salvar")
	public ModelAndView salvar(@Valid OrdemServico ordemServico, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return novo(ordemServico);
		}
		Date dataOS = new Date();
		ordemServico.setData_emissao(dataOS);
		ordensServico.save(ordemServico);

		attributes.addFlashAttribute("mensagem", "Ordem de Servico salva com sucesso!!");

		return new ModelAndView("redirect:/atendimento/novo");
	}
}
