package com.iridiumit.gestaopetshop.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.iridiumit.gestaopetshop.model.Raca;
import com.iridiumit.gestaopetshop.repository.Racas;
import com.iridiumit.gestaopetshop.repository.filtros.FiltroGeral;

@Controller
@RequestMapping("/raca_especie")
public class RacaEspecieController {
	
	@Autowired
	private Racas racas;
	
	@GetMapping
	public ModelAndView listar(@ModelAttribute("filtro") FiltroGeral filtro) {
		
		ModelAndView modelAndView = new ModelAndView("raca/lista-raca_especie");
		
		if(filtro.getTextoFiltro() == null) {
			modelAndView.addObject("racas", racas.findAll());
		}else {
			modelAndView.addObject("racas", racas.findByNomeContainingIgnoreCase(filtro.getTextoFiltro()));
		}
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/excluir/{id}", method = RequestMethod.POST)
	public String excluir(@PathVariable Long id, RedirectAttributes attributes) {

		racas.delete(id);

		attributes.addFlashAttribute("mensagem", "Raça excluida com sucesso!!");

		return "redirect:/raca_especie";
	}
	
	@GetMapping("/novo")
	public ModelAndView novo(Raca raca) {
		return new ModelAndView("raca/cadastro-raca_especie");
	}

	@PostMapping("/salvar")
	public ModelAndView cadastrar(@Valid Raca raca, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return novo(raca);
		}
		
		if(racas.findByNomeIgnoreCase(raca.getNome())== null) {
			racas.save(raca);
		}else {
			result.rejectValue("nome", "Já existe uma raça cadastrada com esse nome!");
			return novo(raca);
		}
		
		attributes.addFlashAttribute("mensagem", "Raça salva com sucesso!!");
		return new ModelAndView("redirect:/raca_especie/novo");
	}
	
	@RequestMapping(value = "/incluirRaca", method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> salvar(@RequestParam("raca") String raca,
            @RequestParam("especie") String especie) {
		
		if(raca.isEmpty() || especie.isEmpty()) {
			return ResponseEntity.badRequest().body("Nome da Raça deve ser preenchido!!");
		}
		
		Raca r = new Raca();
		
		System.out.println(racas.findByNomeIgnoreCase(raca).isEmpty());
		
		if(racas.findByNomeIgnoreCase(raca).isEmpty()) {
			r.setEspecie(especie);
			r.setNome(raca);
			
			racas.save(r);
		}else {
			return ResponseEntity.badRequest().body("Já existe uma raça com esse nome cadastrada na base de dados!!");
		}
		
		return ResponseEntity.ok(r);
	}
	
	@GetMapping("/{id}")
	public ModelAndView alterar(@PathVariable Long id) {

		ModelAndView modelAndView = new ModelAndView("raca/cadastro-raca_especie");

		Raca r = racas.findOne(id);

		modelAndView.addObject(r);

		return modelAndView;
	}

}
