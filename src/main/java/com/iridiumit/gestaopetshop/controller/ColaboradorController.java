package com.iridiumit.gestaopetshop.controller;

import java.util.Arrays;
import java.util.List;

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

import com.iridiumit.gestaopetshop.model.Colaborador;
import com.iridiumit.gestaopetshop.model.GrupoColaboradores;
import com.iridiumit.gestaopetshop.repository.Colaboradores;
import com.iridiumit.gestaopetshop.repository.filtros.ColaboradorFiltro;
import com.iridiumit.gestaopetshop.service.ColaboradorService;

@Controller
@RequestMapping("/administracao/colaboradores")
public class ColaboradorController {
	
	@Autowired
	private ColaboradorService colaboradorService;
	
	@Autowired
	private Colaboradores colaboradores;
	
	@GetMapping
	public ModelAndView listar(@ModelAttribute("filtro") ColaboradorFiltro filtro) {
		
		String nome = filtro.getNome() == null ? "%" : filtro.getNome();
		
		ModelAndView modelAndView = new ModelAndView("administracao/colaborador/lista-colaboradores");

		modelAndView.addObject("colaboradores", colaboradores.findByNomeContainingIgnoreCase(nome));
		return modelAndView;
	}
	
	@DeleteMapping("/excluir/{id}")
	public String excluir(@PathVariable Long id, RedirectAttributes attributes) {
		
		colaboradorService.excluir(id);

		attributes.addFlashAttribute("mensagem", "Colaborador excluido com sucesso!!");
		
		return "redirect:/administracao/colaboradores";
	}

	@GetMapping("/editar/{id}")
	public ModelAndView editar(@PathVariable Long id) {

		return novo(colaboradorService.localizarMatricula(id));
	}

	@GetMapping("/novo")
	public ModelAndView novo(Colaborador colaborador) {
		ModelAndView modelAndView = new ModelAndView("administracao/colaborador/cadastro-colaborador");

		modelAndView.addObject(colaborador);

		return modelAndView;
	}

	@PostMapping("/salvar")
	public ModelAndView salvar(@Valid Colaborador colaborador, BindingResult result, RedirectAttributes attributes) {
		
		if (colaborador.getMatricula() == null) {
			Colaborador c = colaboradorService.localizarCpf(colaborador.getCpf());
			if(c != null){
				result.rejectValue("cpf", "cpf.existente");
			}
        }
		
		if (colaborador.getFuncao().equals("")) {
			result.rejectValue("funcao", "funcao.vazia");
        }
		
		if (result.hasErrors()) {
			return novo(colaborador);
		}

		colaboradorService.salvar(colaborador);

		attributes.addFlashAttribute("mensagem", "Colaborador salvo com sucesso!!");

		return new ModelAndView("redirect:/administracao/colaboradores/novo");
	}
	
	@ModelAttribute("ListaFuncoes")
	public List<GrupoColaboradores> ListaFuncoes(){
		return Arrays.asList(GrupoColaboradores.values());
	}
	
}
