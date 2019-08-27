package com.iridiumit.gestaopetshop.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.iridiumit.gestaopetshop.model.Raca;
import com.iridiumit.gestaopetshop.repository.Racas;

@Controller
public class RacaEspecieController {
	
	@Autowired
	private Racas racas;
	
	@RequestMapping("/raca_especie/novo")
	public ModelAndView novo(Raca raca) {
		return new ModelAndView("raca/cadastro-raca_especie");
	}

	@RequestMapping(value = "/raca_especie/novo", method = RequestMethod.POST)
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
	
	@RequestMapping(value = "/incluirRaca", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> salvar(@RequestParam("raca") String raca,
            @RequestParam("especie") String especie, Model model) {
		
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

}
