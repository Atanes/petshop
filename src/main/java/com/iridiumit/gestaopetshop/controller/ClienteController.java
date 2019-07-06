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
import com.iridiumit.gestaopetshop.repository.filtros.AnimalFiltro;
import com.iridiumit.gestaopetshop.repository.filtros.ClienteFiltro;
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
	public ModelAndView listar(@ModelAttribute("filtro") ClienteFiltro filtro) {
		
		String nome = filtro.getNome() == null ? "%" : filtro.getNome();
		
		ModelAndView modelAndView = new ModelAndView("atendimento/cliente/lista-clientes");

		modelAndView.addObject("clientes", clientes.findByNomeContainingIgnoreCase(nome));
		return modelAndView;
	}
	
	@DeleteMapping("excluir/{id}")
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

		clienteService.salvar(cliente);

		attributes.addFlashAttribute("mensagem", "Cliente salvo com sucesso!!");

		return new ModelAndView("redirect:/atendimento/clientes/novo");
	}
	
	@GetMapping("/selecao/{id}")
	public ModelAndView SelecaoPorCliente(@PathVariable Long id, @ModelAttribute("filtro") AnimalFiltro filtro) {

		Cliente c = clientes.findOne(id);
		ModelAndView modelAndView = new ModelAndView("animais/lista-animais");

		modelAndView.addObject("animais", animais.findByCliente(c));
		modelAndView.addObject("mensagem", "Animais do cliente " + c.getNome());
		return modelAndView;

	}
	
	@GetMapping(value = "/rel-clientes", produces = MediaType.APPLICATION_PDF_VALUE)
	public @ResponseBody byte[] getRelClientes() throws IOException {

		ClienteREL relatorio = new ClienteREL();
		try {
			relatorio.imprimir(clientes.findAll());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		InputStream in = getClass().getResourceAsStream("/relatorios/Relatorio_de_Clientes.pdf");
		return IOUtils.toByteArray(in);
	}
	
}
