package com.iridiumit.gestaopetshop.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
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

import com.iridiumit.gestaopetshop.model.Cliente;
import com.iridiumit.gestaopetshop.model.Equipamento;
import com.iridiumit.gestaopetshop.model.GruposTrabalho;
import com.iridiumit.gestaopetshop.model.OrdemServico;
import com.iridiumit.gestaopetshop.model.StatusOS;
import com.iridiumit.gestaopetshop.repository.Clientes;
import com.iridiumit.gestaopetshop.repository.Produtos;
import com.iridiumit.gestaopetshop.repository.OrdensServico;
import com.iridiumit.gestaopetshop.security.OSUserDetails;

@Controller
@RequestMapping("/atendimento")
public class OrdemServicoController {
	
	@Autowired
	private OrdensServico ordensServico;
	
	@Autowired
	private Clientes clientes;
	
	@Autowired
	private Produtos produtos;
		
	
	@GetMapping
	public ModelAndView listar() {
		ModelAndView modelAndView = new ModelAndView("tecnico/lista-ordemServico");

		modelAndView.addObject("ordensServico", ordensServico.findAll());
		return modelAndView;
	}
	
	@DeleteMapping("/{id}")
	public String remover(@PathVariable Long id, RedirectAttributes attributes) {
		
		ordensServico.delete(id);

		attributes.addFlashAttribute("mensagem", "Ordem de Servico excluida com sucesso!!");
		
		return "redirect:/atendimento";
	}

	@GetMapping("/{id}")
	public ModelAndView editar(@PathVariable Long id) {
		ModelAndView modelAndView = new ModelAndView("atendimento/cadastro-ordemServico");
		
		modelAndView.addObject(ordensServico.findOne(id));
		modelAndView.addObject("clientes", clientes.findAll());
		modelAndView.addObject("idcliente", ordensServico.findOne(id).getCliente().getId());
		modelAndView.addObject("produtos", produtos.findByCliente(ordensServico.findOne(id).getCliente()));

		return modelAndView;
	}

	@GetMapping("/novo")
	public ModelAndView novo(OrdemServico ordemServico) {
		ModelAndView modelAndView = new ModelAndView("atendimento/cadastro-ordemServico");

		modelAndView.addObject(ordemServico);
		modelAndView.addObject("clientes", clientes.findAll());
		modelAndView.addObject("idcliente", 0);
		modelAndView.addObject("produtos", produtos.findAll());

		return modelAndView;
	}

	@PostMapping("/salvar")
	public ModelAndView salvar(@Valid OrdemServico ordemServico, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return novo(ordemServico);
		}
		Date dataOS = new Date();
		ordemServico.setData_emissao(dataOS);
		
		OSUserDetails principal = (OSUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String emissor = principal.getUsername();
		
		ordemServico.setEmissor(emissor);
		ordensServico.save(ordemServico);

		attributes.addFlashAttribute("mensagem", "Ordem de Servico salva com sucesso!!");

		return new ModelAndView("redirect:/atendimento/novo");
	}
	
	@ModelAttribute("ListaStatus")
	public List<StatusOS> ListaStatus(){
		return Arrays.asList(StatusOS.values());
	}
	
	@ModelAttribute("GruposTrabalho")
	public List<GruposTrabalho> GruposTrabalho(){
		return Arrays.asList(GruposTrabalho.values());
	}
	
	//Método que recebe os produtos de um determinado cliente para preencher um select com esses dados
	@RequestMapping(value = "/cboEquipamentos", method = RequestMethod.GET)
    public @ResponseBody List<Equipamento> adicionados(@RequestParam Long valor, Model model) {

		Cliente c = clientes.findOne(valor);
		List<Equipamento> cboEquipamentos = produtos.findByCliente(c);
        model.addAttribute("adicionados", cboEquipamentos);
        model.addAttribute("idcliente", c.getId());
        return cboEquipamentos;

    }
}
