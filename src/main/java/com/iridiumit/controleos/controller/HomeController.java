package com.iridiumit.controleos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.iridiumit.controleos.model.Cliente;
import com.iridiumit.controleos.model.Equipamento;
import com.iridiumit.controleos.repository.Clientes;
import com.iridiumit.controleos.repository.Equipamentos;
import com.iridiumit.controleos.repository.OrdensServico;

@Controller
public class HomeController {
	
	@Autowired
	private OrdensServico ordensServico;
	
	@Autowired
	private Clientes clientes;
	
	@Autowired
	private Equipamentos equipamentos;
	
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
	
	@RequestMapping(value = "/testeSelect", method = RequestMethod.GET)
    public @ResponseBody List<Equipamento> adicionados(@RequestParam Long id, Model model) {

        Cliente c = clientes.findOne(id);
		List<Equipamento> e = equipamentos.findByCliente(c);
		model.addAttribute("equipamentos", e);
        return e;

    }
}
