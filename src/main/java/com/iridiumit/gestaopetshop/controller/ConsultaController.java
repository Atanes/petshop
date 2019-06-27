package com.iridiumit.gestaopetshop.controller;

import java.util.Arrays;
import java.util.Date;
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

import com.iridiumit.gestaopetshop.model.Animal;
import com.iridiumit.gestaopetshop.model.Consulta;
import com.iridiumit.gestaopetshop.model.TipoConsulta;
import com.iridiumit.gestaopetshop.model.StatusConsulta;
import com.iridiumit.gestaopetshop.repository.Animais;
import com.iridiumit.gestaopetshop.repository.Colaboradores;
import com.iridiumit.gestaopetshop.repository.Consultas;

@Controller
@RequestMapping("/atendimento/consultas")
public class ConsultaController {

	@Autowired
	private Consultas consultas;
	
	@Autowired
	private Animais animais;

	@Autowired
	private Colaboradores colaboradores;

	@GetMapping
	public ModelAndView listar() {

		ModelAndView modelAndView = new ModelAndView("atendimento/consulta/lista-consultas");

		modelAndView.addObject("consultas", consultas.findByOrderByDataAtendimento());
		return modelAndView;
	}

	@DeleteMapping("/excluir/{codigo}")
	public String remover(@PathVariable Long codigo, RedirectAttributes attributes) {

		consultas.delete(codigo);

		attributes.addFlashAttribute("mensagem", "Consulta excluida com sucesso!!");

		return "redirect:/atendimento/consultas";
	}

	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable Long codigo) {
		
		Consulta c = consultas.findOne(codigo);
		
		return incluir(c, c.getAnimal().getId());

	}

	@GetMapping("/novo/{id}")
	public ModelAndView incluir(Consulta consulta, @PathVariable("id") Long idAnimal) {
		ModelAndView modelAndView = new ModelAndView("atendimento/consulta/cadastro-consulta");

		Animal a = animais.findOne(idAnimal);
		
		consulta.setAnimal(a);

		modelAndView.addObject(consulta);
		modelAndView.addObject("colaboradores", colaboradores.findByFuncao("VETERINARIO"));

		return modelAndView;
	}
	
	@PostMapping("/agendar")
	public ModelAndView agendar(Consulta consulta, RedirectAttributes attributes) {
		
		Date dataRegistro = new Date();
		
		consulta.setDataRegistro(dataRegistro);
		consulta.setDiagnostico("-");
		consulta.setSintomas("-");
		
		consultas.save(consulta);

		attributes.addFlashAttribute("mensagem", "Consulta agendada com sucesso!!");

		return new ModelAndView("redirect:/atendimento/consultas");
	}

	@PostMapping("/salvar")
	public ModelAndView salvar(@Valid Consulta consulta, BindingResult result, RedirectAttributes attributes) {
		
		if (result.hasErrors()) {
			return incluir(consulta, consulta.getAnimal().getId());
		}

		consultas.save(consulta);

		attributes.addFlashAttribute("mensagem", "Consulta salva com sucesso!!");

		return new ModelAndView("redirect:/atendimento/consultas/lista-consultas");
	}
	
	@ModelAttribute("StatusConsulta")
	public List<StatusConsulta> StatusConsulta(){
		return Arrays.asList(StatusConsulta.values());
	}
	
	@ModelAttribute("TipoConsulta")
	public List<TipoConsulta> TiposConsulta(){
		return Arrays.asList(TipoConsulta.values());
	}

}
