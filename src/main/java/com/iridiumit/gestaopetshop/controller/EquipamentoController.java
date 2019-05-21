package com.iridiumit.gestaopetshop.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.iridiumit.gestaopetshop.model.Cliente;
import com.iridiumit.gestaopetshop.model.Equipamento;
import com.iridiumit.gestaopetshop.repository.Clientes;
import com.iridiumit.gestaopetshop.repository.Produtos;
import com.iridiumit.gestaopetshop.utils.Disco;

@Controller
@RequestMapping("/produtos")
public class EquipamentoController {
	
	public static String uploadDirectory = "/public/imagens_produtos/";

	@Autowired
	private Produtos produtos;

	@Autowired
	private Clientes clientes;
	
	@Autowired
	private Disco disco;
	
	@GetMapping
	public ModelAndView listar() {
		ModelAndView modelAndView = new ModelAndView("tecnico/lista-produtos");

		modelAndView.addObject("produtos", produtos.findAll());
		return modelAndView;
	}

	@DeleteMapping("/excluir/{id}")
	public String remover(@PathVariable Long id, RedirectAttributes attributes) {

		Cliente c = produtos.findOne(id).getCliente();

		produtos.delete(id);

		attributes.addFlashAttribute("mensagem", "Equipamento excluido com sucesso!!");

		return "redirect:/produtos/selecao/" + c.getId();
	}

	@GetMapping("/{id}")
	public ModelAndView editar(@PathVariable Long id) {

		return novo(produtos.findOne(id));
	}

	@GetMapping("/novo")
	public ModelAndView novo(Equipamento equipamento) {
		ModelAndView modelAndView = new ModelAndView("atendimento/produtos/cadastro-equipamento");

		modelAndView.addObject(equipamento);
		modelAndView.addObject("clientes", clientes.findAll());

		return modelAndView;
	}

	@GetMapping("/novo/{id}")
	public ModelAndView incluir(Equipamento equipamento, @PathVariable("id") Long id) {
		ModelAndView modelAndView = new ModelAndView("atendimento/produtos/cadastro-equipamento");

		modelAndView.addObject(equipamento);
		modelAndView.addObject("clientes", clientes.findOne(id));

		return modelAndView;
	}
	
	@PostMapping("/salvando")
	public ModelAndView salvando(@Valid Equipamento equipamento, BindingResult result, RedirectAttributes attributes,
			MultipartFile imagem) {
		if (result.hasErrors()) {
			return novo(equipamento);
		}
		
		disco.salvarFoto(imagem);
		
		equipamento.setUrl_imagen("/public/imagens_produtos/" + imagem.getOriginalFilename());
		
		//Em uma aplicação publicada na WEB esse endereço deve ser trocado para um caminho no servidor de aplicações
		//ou um servidor de arquivos
		/*String path = new File("").getAbsolutePath() + "/src/main/resources/static/imagens_produtos/" + imagem.getOriginalFilename();
		
		System.out.println(path);

		File saveFile = new File(path);
		try {
			FileUtils.writeByteArrayToFile(saveFile, imagem.getBytes());
			equipamento.setUrl_imagen("/imagens_produtos/" + imagem.getOriginalFilename());
		} catch (IOException e) {
			e.printStackTrace();
		}*/

		produtos.save(equipamento);

		attributes.addFlashAttribute("mensagem", "Equipamento salvo com sucesso!!");

		return new ModelAndView("redirect:/produtos/novo");
	}

	@PostMapping("/salvar")
	public ModelAndView salvar(@Valid Equipamento equipamento, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return novo(equipamento);
		}

		produtos.save(equipamento);

		attributes.addFlashAttribute("mensagem", "Equipamento salvo com sucesso!!");

		return new ModelAndView("redirect:/produtos/novo");
	}

	// Teste para preencher lista de produtos na tela - sem uso no momento
	@GetMapping("/selecao/{id}")
	public ModelAndView SelecaoPorCliente(@PathVariable Long id) {

		Cliente c = clientes.findOne(id);
		ModelAndView modelAndView = new ModelAndView("tecnico/lista-produtos");

		modelAndView.addObject("produtos", produtos.findByCliente(c));
		modelAndView.addObject("mensagem", "Produtos do cliente " + c.getNome());
		return modelAndView;

	}

	// Teste para preencher lista de produtos na tela - sem uso no momento
	@RequestMapping(value = "/lista/{id}", method = RequestMethod.GET)
	public String showGuestList(Model model, @PathVariable("id") Long id) {
		Cliente c = clientes.findOne(id);
		model.addAttribute("produtos", produtos.findByCliente(c));

		return "orcamento/lista-clientes :: resultsList";
	}
}
