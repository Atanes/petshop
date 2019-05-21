package com.iridiumit.gestaopetshop.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.iridiumit.gestaopetshop.model.Fornecedor;
import com.iridiumit.gestaopetshop.repository.Orcamentos;
import com.iridiumit.gestaopetshop.repository.OrdensServico;
import com.iridiumit.gestaopetshop.security.OSUserDetails;
import com.iridiumit.gestaopetshop.service.UsuarioService;

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
		
		return "redirect:/orcamento/lista-orcamento";
	}

	/*@GetMapping("editar/{id}")
	public ModelAndView editar(@PathVariable Long id) {

		return novo(orcamentos.findOne(id), orcamentos.getOne(id).getOs().getId());
	}*/

	@GetMapping("/novo/{id}")
	public ModelAndView novo(Fornecedor fornecedor, @PathVariable("id") Long id) {
		ModelAndView modelAndView = new ModelAndView("tecnico/cadastro-orcamento");
		
		OSUserDetails principal = (OSUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		fornecedor.setOs(ordensServico.findOne(id));
		fornecedor.setUsuario(usuarios.localizarLogin(principal.getLogin()));
		modelAndView.addObject(fornecedor);

		return modelAndView;
	}

	@PostMapping("/salvar")
	public ModelAndView salvar(@Valid Fornecedor fornecedor, BindingResult result, RedirectAttributes attributes) {
		
		if (result.hasErrors()) {
			return novo(fornecedor, fornecedor.getOs().getId());
		}
		
		ordensServico.getOne(fornecedor.getId()).setStatus("ORCAMENTO");

		orcamentos.save(fornecedor);

		attributes.addFlashAttribute("mensagem", "Equipamento salvo com sucesso!!");
		
		return new ModelAndView("redirect:/orcamento");
	}
	
}
