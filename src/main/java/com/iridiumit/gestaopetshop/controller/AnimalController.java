package com.iridiumit.gestaopetshop.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.iridiumit.gestaopetshop.model.Animal;
import com.iridiumit.gestaopetshop.model.Cliente;
import com.iridiumit.gestaopetshop.model.Raca;
import com.iridiumit.gestaopetshop.repository.Animais;
import com.iridiumit.gestaopetshop.repository.Clientes;
import com.iridiumit.gestaopetshop.repository.Racas;
import com.iridiumit.gestaopetshop.repository.filtros.FiltroGeral;
import com.iridiumit.gestaopetshop.service.FotoService;

@Controller
@RequestMapping("/clientes/animais")
public class AnimalController {

	@Autowired
	private Clientes clientes;

	@Autowired
	private Animais animais;

	@Autowired
	private Racas racas;
	
	@Autowired
	private FotoService fotoService;

	@GetMapping
	public ModelAndView listar(@ModelAttribute("filtro") FiltroGeral filtro) {

		ModelAndView modelAndView = new ModelAndView("animais/lista-animais");

		String nome = "";

		if (filtro.getTextoFiltro() == null) {
			nome = "%";
		} else {
			nome = filtro.getTextoFiltro();
		}

		modelAndView.addObject("animais", animais.findByNomeContainingIgnoreCase(nome));
		return modelAndView;
	}

	@PostMapping("/excluir/{id}")
	public String remover(@PathVariable Long id, RedirectAttributes attributes) {

		Cliente c = animais.findOne(id).getCliente();

		animais.delete(id);

		attributes.addFlashAttribute("mensagem", "Animal excluido com sucesso!!");

		return "redirect:/atendimento/clientes/selecao/" + c.getId();
	}

	@GetMapping("/{id}")
	public ModelAndView editar(@PathVariable Long id) {

		return novo(animais.findOne(id));
	}

	@GetMapping("/novo")
	public ModelAndView novo(Animal animal) {
		ModelAndView modelAndView = new ModelAndView("animais/cadastro-animal");

		modelAndView.addObject(animal);

		return modelAndView;
	}

	@GetMapping("/incluirAnimal/{id}")
	public ModelAndView incluirAnimal(@PathVariable Long id) {

		ModelAndView modelAndView = new ModelAndView("animais/cadastro-animal");

		Cliente c = clientes.findOne(id);

		Animal a = new Animal();

		a.setCliente(c);

		modelAndView.addObject(a);

		return modelAndView;
	}

	@PostMapping("/salvar")
	public ModelAndView salvar(@RequestParam("file") MultipartFile file, @Valid Animal animal, BindingResult result,
			RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return novo(animal);
		}
		
		if(!file.isEmpty()) {
			String arquivoFoto = fotoService.doUpload(file, animal);
			
			if(arquivoFoto.equals("erro")){
				attributes.addFlashAttribute("mensagem", "Problemas para salvar a foto do animal!!");
				return novo(animal);
			}else {
				animal.setFoto(arquivoFoto);
			}
		}
		
		animal.setData_cadastro(new Date());

		animais.save(animal);

		attributes.addFlashAttribute("mensagem", "Animal salvo com sucesso!!");

		return new ModelAndView("redirect:/atendimento/clientes/selecao/" + animal.getCliente().getId());
	}

	// Método que recebe uma especie e devolve as raças relecionadas para o select
	@RequestMapping(value = "/listaRacas", method = RequestMethod.GET)
	public @ResponseBody List<Raca> adicionados(@RequestParam String especie, Model model) {

		List<Raca> r = racas.findByEspecieIgnoreCaseOrderByNome(especie);

		model.addAttribute("adicionados", r);
		return r;

	}
	
	@GetMapping("/fotos/{nome:.*}")
	public @ResponseBody byte[] recuperarFoto(@PathVariable String nome) throws IOException {

		System.out.println(nome);
		
		return fotoService.recuperarFoto(nome);
	}

}
