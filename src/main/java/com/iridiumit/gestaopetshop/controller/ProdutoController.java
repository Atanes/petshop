package com.iridiumit.gestaopetshop.controller;

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

import com.iridiumit.gestaopetshop.model.Fornecedor;
import com.iridiumit.gestaopetshop.model.Produto;
import com.iridiumit.gestaopetshop.repository.Fornecedores;
import com.iridiumit.gestaopetshop.repository.Produtos;


@Controller
@RequestMapping("/fornecedores/produtos")
public class ProdutoController {
	
	@Autowired
	private Fornecedores fornecedores;
	
	@Autowired
	private Produtos produtos;
	
	@GetMapping
	public ModelAndView listar() {
		ModelAndView modelAndView = new ModelAndView("produtos/lista-produtos");

		modelAndView.addObject("produtos", produtos.findAll());
		return modelAndView;
	}

	@DeleteMapping("/excluir/{id}")
	public String remover(@PathVariable Long id, RedirectAttributes attributes) {

		Fornecedor f = produtos.findOne(id).getFornecedor();

		produtos.delete(id);

		attributes.addFlashAttribute("mensagem", "Produto excluido com sucesso!!");

		return "redirect:/fornecedores/produtos/selecao/" + f.getId();
	}

	@GetMapping("/{id}")
	public ModelAndView editar(@PathVariable Long id) {

		return novo(produtos.findOne(id));
	}

	@GetMapping("/novo")
	public ModelAndView novo(Produto produto) {
		ModelAndView modelAndView = new ModelAndView("produtos/cadastro-produto");

		modelAndView.addObject(produto);
		modelAndView.addObject("fornecedores", fornecedores.findAll());

		return modelAndView;
	}

	@GetMapping("/novo/{id}")
	public ModelAndView incluir(Produto produto, @PathVariable("id") Long id) {
		ModelAndView modelAndView = new ModelAndView("produtos/cadastro-produto");

		modelAndView.addObject(produto);
		modelAndView.addObject("fornecedores", fornecedores.findOne(id));

		return modelAndView;
	}
	
	@PostMapping("/salvar")
	public ModelAndView salvar(@Valid Produto produto, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return novo(produto);
		}

		produtos.save(produto);

		attributes.addFlashAttribute("mensagem", "Produto salvo com sucesso!!");

		return new ModelAndView("redirect:/fornecedores/produtos/novo");
	}

}
