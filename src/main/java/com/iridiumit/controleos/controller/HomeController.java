package com.iridiumit.controleos.controller;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.iridiumit.controleos.relatorios.ClienteREL;
import com.iridiumit.controleos.relatorios.UsuarioREL;
import com.iridiumit.controleos.repository.Clientes;
import com.iridiumit.controleos.repository.OrdensServico;
import com.iridiumit.controleos.repository.Usuarios;
import com.iridiumit.controleos.repository.filtros.ClienteFiltro;

@Controller
public class HomeController {
	
	@Autowired
	private OrdensServico ordensServico;
	
	@Autowired
	private Clientes clientes;
	
	@Autowired
	private Usuarios usuarios;
	
	@Autowired 
	protected ServletContext servletContext;
	
	@RequestMapping(method = RequestMethod.GET, path = "/entrar")
    public String entrar() {
        return "entrar";
    }
	
	@RequestMapping(method = RequestMethod.GET, path = "/")
    public String inicio() {
        return "inicio";
    }
	
	@RequestMapping(method = RequestMethod.GET, path = "/relatorios/clientes")
    public String relatorioClientes() {
		
		System.out.print(servletContext.getRealPath("/"));
		
		ClienteREL relatorio = new ClienteREL();
		try {
			relatorio.imprimir(clientes.findAll());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        return "inicio";
    }
	
	@RequestMapping(method = RequestMethod.GET, path = "/relatorios/usuarios")
    public String relatorioUsuarios() {
		
		System.out.print(servletContext.getRealPath("/"));
		
		UsuarioREL relatorio = new UsuarioREL();
		try {
			relatorio.imprimir(usuarios.findAll());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        return "inicio";
    }
	
	@RequestMapping(method = RequestMethod.GET, path = "/tecnico")
	public ModelAndView tecnico() {
		ModelAndView modelAndView = new ModelAndView("tecnico/lista-ordemServico");

		modelAndView.addObject("ordensServico", ordensServico.findAll());
		return modelAndView;
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/orcamento")
	public ModelAndView listar(@ModelAttribute("filtro") ClienteFiltro filtro) {
		
		String nome = filtro.getCpf_nome() == null ? "%" : filtro.getCpf_nome();
		
		ModelAndView modelAndView = new ModelAndView("orcamento/lista-clientes");
		
		modelAndView.addObject("clientes", clientes.findByNomeContainingIgnoreCase(nome));
		return modelAndView;
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/acessonegado")
    public String acessonegado() {
        return "acessonegado";
    }
}
