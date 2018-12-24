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

import com.iridiumit.controleos.model.Orcamento;
import com.iridiumit.controleos.repository.Orcamentos;
import com.iridiumit.controleos.repository.OrdensServico;
import com.iridiumit.controleos.service.UsuarioService;

@Controller
@RequestMapping("/orcamento")
public class OrcamentoController {
	
	@Autowired
	private Orcamentos orcamentos;
	
	@Autowired
	private OrdensServico ordensServico;
	
	@Autowired
	private UsuarioService usuarios;
	
	@GetMapping
	public ModelAndView listar() {
		
		ModelAndView modelAndView = new ModelAndView("orcamento/lista-orcamento");

		modelAndView.addObject("orcamentos", orcamentos.findAll());
		return modelAndView;
	}
	
	@DeleteMapping("/excluir/{id}")
	public String excluir(@PathVariable Long id, RedirectAttributes attributes) {
		
		orcamentos.delete(id);

		attributes.addFlashAttribute("mensagem", "Orçamento excluido com sucesso!!");
		
		return "redirect:/lista-orcamento";
	}

	/*@GetMapping("editar/{id}")
	public ModelAndView editar(@PathVariable Long id) {

		return novo(orcamentos.findOne(id), orcamentos.getOne(id).getOs().getId());
	}*/

	@GetMapping("/novo/{login}/{id}")
	public ModelAndView novo(Orcamento orcamento, @PathVariable("login") String login, @PathVariable("id") Long id) {
		ModelAndView modelAndView = new ModelAndView("tecnico/cadastro-orcamento");

		orcamento.setOs(ordensServico.findOne(id));
		orcamento.setUsuario(usuarios.localizarLogin(login));
		modelAndView.addObject(orcamento);

		return modelAndView;
	}

	@PostMapping("/salvar")
	public ModelAndView salvar(@Valid Orcamento orcamento, BindingResult result, RedirectAttributes attributes) {
		
		if (result.hasErrors()) {
			return novo(orcamento, orcamento.getUsuario().getLogin(), orcamento.getOs().getId());
		}
		
		ordensServico.getOne(orcamento.getId()).setStatus("ORCAMENTO");

		orcamentos.save(orcamento);

		attributes.addFlashAttribute("mensagem", "Equipamento salvo com sucesso!!");
		
		return new ModelAndView("redirect:/orcamento");
	}
	
}
