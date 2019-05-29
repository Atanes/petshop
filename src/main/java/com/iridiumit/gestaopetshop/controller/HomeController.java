package com.iridiumit.gestaopetshop.controller;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.iridiumit.gestaopetshop.relatorios.ClienteREL;
import com.iridiumit.gestaopetshop.relatorios.UsuarioREL;
import com.iridiumit.gestaopetshop.repository.Clientes;
import com.iridiumit.gestaopetshop.repository.Fornecedores;
import com.iridiumit.gestaopetshop.repository.Usuarios;
import com.iridiumit.gestaopetshop.repository.filtros.ClienteFiltro;

@Controller
public class HomeController {

	@Autowired
	private Fornecedores fornecedores;

	@Autowired
	private Clientes clientes;

	@Autowired
	private Usuarios usuarios;

	@RequestMapping(method = RequestMethod.GET, path = "/entrar")
	public String entrar() {
		return "entrar";
	}

	@RequestMapping(method = RequestMethod.GET, path = "/")
	public String inicio() {
		return "inicio";
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

	@GetMapping(value = "/rel-usuarios", produces = MediaType.APPLICATION_PDF_VALUE)
	public @ResponseBody byte[] getRelUsuarios() throws IOException {

		UsuarioREL relatorio = new UsuarioREL();
		try {
			relatorio.imprimir(usuarios.findAll());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		InputStream in = getClass().getResourceAsStream("/relatorios/Relatorio_de_Usuarios.pdf");
		return IOUtils.toByteArray(in);
	}

	@RequestMapping(method = RequestMethod.GET, path = "/fornecedores")
	public ModelAndView fornecedor() {
		ModelAndView modelAndView = new ModelAndView("fornecedor/lista-fornecedores");

		modelAndView.addObject("fornecedores", fornecedores.findAll());
		return modelAndView;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/clientes")
	public ModelAndView listar(@ModelAttribute("filtro") ClienteFiltro filtro) {

		String nome = filtro.getNome() == null ? "%" : filtro.getNome();

		ModelAndView modelAndView = new ModelAndView("administracao/lista-clientes");

		modelAndView.addObject("clientes", clientes.findByNomeContainingIgnoreCase(nome));
		return modelAndView;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/acessonegado")
	public String acessonegado() {
		return "acessonegado";
	}
}
