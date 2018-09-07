package com.iridiumit.controleos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.iridiumit.controleos.repository.Clientes;
import com.iridiumit.controleos.repository.OrdensServico;

@Controller
public class HomeController {
	
	@Autowired
	private OrdensServico ordensServico;
	
	@Autowired
	private Clientes clientes;
	
	@RequestMapping(method = RequestMethod.GET, path = "/entrar")
    public String entrar() {
        return "entrar";
    }
	
	@RequestMapping(method = RequestMethod.GET, path = "/")
    public String inicio() {
        return "inicio";
    }
	
	@RequestMapping(method = RequestMethod.GET, path = "/tecnico")
	public ModelAndView tecnico() {
		ModelAndView modelAndView = new ModelAndView("tecnico/lista-ordemServico");

		modelAndView.addObject("ordensServico", ordensServico.findAll());
		return modelAndView;
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/orcamento")
	public ModelAndView listar() {
		ModelAndView modelAndView = new ModelAndView("orcamento/lista-clientes");

		modelAndView.addObject("clientes", clientes.findAll());
		return modelAndView;
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/acessonegado")
    public String acessonegado() {
        return "acessonegado";
    }
}