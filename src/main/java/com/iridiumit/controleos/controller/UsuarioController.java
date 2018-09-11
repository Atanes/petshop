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

import com.iridiumit.controleos.model.Usuario;
import com.iridiumit.controleos.repository.UsuarioDAO;
import com.iridiumit.controleos.repository.Usuarios;

@Controller
@RequestMapping("/administracao/usuarios")
public class UsuarioController {
	
	@Autowired
	private Usuarios usuarios;
	
	@Autowired
	private UsuarioDAO usuarioDAO;
	
	@GetMapping
	public ModelAndView listar() {
		ModelAndView modelAndView = new ModelAndView("administracao/lista-usuarios");

		modelAndView.addObject("usuarios", usuarios.findAll());
		return modelAndView;
	}
	
	@DeleteMapping("/{id}")
	public String remover(@PathVariable Long id, RedirectAttributes attributes) {
		
		usuarios.delete(id);

		attributes.addFlashAttribute("mensagem", "Usuário excluido com sucesso!!");
		
		return "redirect:/administracao/usuarios";
	}

	@GetMapping("/{id}")
	public ModelAndView editar(@PathVariable Long id) {

		return novo(usuarios.findOne(id));
	}

	@GetMapping("/novo")
	public ModelAndView novo(Usuario usuario) {
		ModelAndView modelAndView = new ModelAndView("/administracao/cadastro-usuario");

		modelAndView.addObject(usuario);
		modelAndView.addObject("usuarios", usuarios.findAll());

		return modelAndView;
	}

	@PostMapping("/salvar")
	public ModelAndView salvar(@Valid Usuario usuario, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return novo(usuario);
		}

		usuarioDAO.adicionaUsuario(usuario);

		attributes.addFlashAttribute("mensagem", "Usuario salvo com sucesso!!");

		return new ModelAndView("redirect:/administracao/usuarios/novo");
	}
}