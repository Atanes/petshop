package com.iridiumit.controleos.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.iridiumit.controleos.model.Cliente;
import com.iridiumit.controleos.repository.Clientes;
import com.iridiumit.controleos.repository.filtros.ClienteFiltro;
import com.iridiumit.controleos.service.ClienteService;

@Controller
@RequestMapping("/atendimento/clientes")
public class ClienteController {
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private Clientes clientes;
	
	@GetMapping
	public ModelAndView listar(@ModelAttribute("filtro") ClienteFiltro filtro) {
		
		ModelAndView modelAndView = new ModelAndView("orcamento/lista-clientes");

		modelAndView.addObject("clientes", clientes.findByNomeContainingIgnoreCase(filtro.getCpf_nome()));
		return modelAndView;
	}
	
	@DeleteMapping("/{id}")
	public String remover(@PathVariable Long id, RedirectAttributes attributes) {
		
		clienteService.excluir(id);

		attributes.addFlashAttribute("mensagem", "Cliente excluido com sucesso!!");
		
		return "redirect:/atendimento/clientes";
	}
	
	@GetMapping("excluir/{id}")
	public String excluir(@PathVariable Long id, RedirectAttributes attributes) {
		
		clienteService.excluir(id);

		attributes.addFlashAttribute("mensagem", "Cliente excluido com sucesso!!");
		
		return "redirect:/atendimento/clientes";
	}

	@GetMapping("editar/{id}")
	public ModelAndView editar(@PathVariable Long id) {

		return novo(clienteService.localizar(id));
	}

	@GetMapping("/novo")
	public ModelAndView novo(Cliente cliente) {
		ModelAndView modelAndView = new ModelAndView("atendimento/clientes/cadastro-cliente");

		modelAndView.addObject(cliente);

		return modelAndView;
	}

	@PostMapping("/salvar")
	public ModelAndView salvar(@Valid Cliente cliente, BindingResult result, RedirectAttributes attributes) {
		
		Cliente c = clienteService.localizarLogin(cliente.getCpfcnpj());
		
		if (c != null && c.getId() != cliente.getId()) {
			result.rejectValue("cpfcnpj", "cpfcnpj.existente");
        }
		
		if (result.hasErrors()) {
			return novo(cliente);
		}

		clienteService.salvar(cliente);

		attributes.addFlashAttribute("mensagem", "Cliente salvo com sucesso!!");

		return new ModelAndView("redirect:/atendimento/clientes/novo");
	}
	
}
