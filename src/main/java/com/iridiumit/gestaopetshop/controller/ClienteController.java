package com.iridiumit.gestaopetshop.controller;

import java.io.IOException;
import java.io.InputStream;

import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.iridiumit.gestaopetshop.model.Cliente;
import com.iridiumit.gestaopetshop.model.Endereco;
import com.iridiumit.gestaopetshop.relatorios.ClienteREL;
import com.iridiumit.gestaopetshop.repository.Animais;
import com.iridiumit.gestaopetshop.repository.Clientes;
import com.iridiumit.gestaopetshop.repository.Enderecos;
import com.iridiumit.gestaopetshop.repository.filtros.FiltroGeral;
import com.iridiumit.gestaopetshop.service.ClienteService;

@Controller
@RequestMapping("/atendimento/clientes")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private Clientes clientes;

	@Autowired
	private Enderecos enderecos;

	@Autowired
	private Animais animais;

	@GetMapping
	public ModelAndView listar(@ModelAttribute("filtro") FiltroGeral filtro) {

		ModelAndView modelAndView = new ModelAndView("atendimento/cliente/lista-clientes");
		
		String nome = "";
		
		if(filtro.getTextoFiltro() == null) {
			nome = "%";
		}else {
			nome = filtro.getTextoFiltro();
		}

		modelAndView.addObject("clientes", clientes.findByNomeContainingIgnoreCaseAndAtivo(nome, true));
		return modelAndView;
	}

	@GetMapping("/inativos")
	public ModelAndView listarInativos(@ModelAttribute("filtro") FiltroGeral filtro) {

		ModelAndView modelAndView = new ModelAndView("atendimento/cliente/lista-clientes-inativos");
		
		String nome = "";
		
		if(filtro.getTextoFiltro() == null) {
			nome = "%";
		}else {
			nome = filtro.getTextoFiltro();
		}

		modelAndView.addObject("clientes", clientes.findByNomeContainingIgnoreCaseAndAtivo(nome, false));
		return modelAndView;
	}

	@GetMapping("/{id}")
	public ModelAndView clienteAnimais(@PathVariable Long id) {

		ModelAndView modelAndView = new ModelAndView("atendimento/cliente/lista-cliente-e-animais");

		Cliente c = clientes.findOne(id);

		modelAndView.addObject(c);

		modelAndView.addObject("animais", animais.findByCliente(c));

		modelAndView.addObject("mensagem", "Cliente salvo com sucesso!");

		return modelAndView;
	}
	
	
	  @GetMapping("/selecao/{id}") public ModelAndView
	  SelecaoPorCliente(@PathVariable Long id, @ModelAttribute("filtro")
	  FiltroGeral filtro) {
	  
	  Cliente c = clientes.findOne(id);
	  
	  ModelAndView modelAndView = new
	  ModelAndView("atendimento/cliente/lista-cliente-e-animais");
	  
	  modelAndView.addObject(c);
	  
	  modelAndView.addObject("animais", animais.findByCliente(c));
	  
	  return modelAndView;
	  
	  }
	 

	@DeleteMapping("excluir/{id}")
	public String excluir(@PathVariable Long id, RedirectAttributes attributes) {

		Cliente c = clientes.findOne(id);

		c.setAtivo(false); // Inativa o cliente na base de dados, mas mantem as informações de cadastro

		clienteService.salvar(c);

		attributes.addFlashAttribute("mensagem", "Cliente inativado com sucesso!!");

		return "redirect:/atendimento/clientes";
	}

	@GetMapping("editar/{id}")
	public ModelAndView editar(@PathVariable Long id) {

		return novo(clienteService.localizar(id));
	}

	@GetMapping("ativar/{id}")
	public ModelAndView ativar(@PathVariable Long id) {

		Cliente c = clienteService.localizar(id);

		c.setAtivo(true);

		clientes.save(c);

		ModelAndView modelAndView = new ModelAndView("atendimento/cliente/lista-cliente-e-animais");

		modelAndView.addObject(c);

		modelAndView.addObject("animais", animais.findByCliente(c));

		modelAndView.addObject("mensagem", "Cliente re-ativado com sucesso!");

		return modelAndView;

	}

	@GetMapping("/novo")
	public ModelAndView novo(Cliente cliente) {
		ModelAndView modelAndView = new ModelAndView("atendimento/cliente/cadastro-cliente");

		modelAndView.addObject(cliente);

		return modelAndView;
	}

	@PostMapping("/salvar")
	public ModelAndView salvar(@Valid Cliente cliente, BindingResult result, RedirectAttributes attributes) {

		Cliente c = clienteService.localizarLogin(cliente.getCpf());
		Endereco e = cliente.getEndereco();

		if (c != null && c.getId() != cliente.getId()) {
			result.rejectValue("cpf", "cpf.existente");
		}

		if (result.hasErrors()) {
			return novo(cliente);
		}

		enderecos.save(e);

		cliente.setEndereco(e);

		cliente.setAtivo(true);

		clienteService.salvar(cliente);

		attributes.addFlashAttribute("mensagem", "Cliente salvo com sucesso!!");

		return new ModelAndView("redirect:/atendimento/clientes/" + cliente.getId());

	}

	
	  @GetMapping(value = "/rel-clientes", produces =
	  MediaType.APPLICATION_PDF_VALUE) public @ResponseBody byte[] getRelClientes()
	  throws IOException {
	  
	  ClienteREL relatorio = new ClienteREL();
	  
	  try {
		  relatorio.imprimir(clientes.findAll());
	  } catch (Exception e) {
		  e.printStackTrace(); 
	  }
	  
	  InputStream in = this.getClass().getResourceAsStream("/relatorios/Relatorio_de_Clientes.pdf");
	  return IOUtils.toByteArray(in); 
	  
	  }

}
