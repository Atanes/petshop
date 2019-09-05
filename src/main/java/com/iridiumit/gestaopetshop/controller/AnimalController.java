package com.iridiumit.gestaopetshop.controller;

import static java.nio.file.FileSystems.getDefault;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
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

@Controller
@RequestMapping("/clientes/animais")
public class AnimalController {

	@Autowired
	private Clientes clientes;

	@Autowired
	private Animais animais;

	@Autowired
	private Racas racas;

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
			String arquivoFoto = doUpload(file, animal);
			
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

	private String doUpload(MultipartFile file, Animal animal) {

		// Root Directory.
		String uploadRootPath = getDefault().getPath(System.getenv("HOMEPATH"), "animaisfotos").toString();
		System.out.println("uploadRootPath=" + uploadRootPath);

		File uploadRootDir = new File(uploadRootPath);
		// Create directory if it not exists.
		if (!uploadRootDir.exists()) {
			uploadRootDir.mkdirs();
		}

		String nomeArquivo = animal.getCliente().getId() + "_" + animal.getNome() + "_" + file.getOriginalFilename();
		System.out.println("Nome do arquivo: " + nomeArquivo);

		// Client File Content Type
		String contentType = file.getContentType();
		System.out.println("Tipo do arquivo: " + contentType);

		if (nomeArquivo != null && nomeArquivo.length() > 0) {
			try {
				// Create the file at server
				File serverFile = new File(uploadRootDir.getAbsolutePath() + File.separator + nomeArquivo);

				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(file.getBytes());
				stream.close();
				//
				System.out.println("Arquivo gravado: " + serverFile);
			} catch (Exception e) {
				System.out.println("Erro ao gravar o arquivo: " + nomeArquivo);
				return "erro";
			}
		}

		return nomeArquivo;
	}

}
