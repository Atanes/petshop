package com.iridiumit.gestaopetshop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iridiumit.gestaopetshop.model.Cliente;
import com.iridiumit.gestaopetshop.repository.Clientes;
import com.iridiumit.gestaopetshop.repository.filtros.ClienteFiltro;

@Service
public class ClienteService{
	
	@Autowired
	private Clientes clientes;
		
	public List<Cliente> filtrar(ClienteFiltro filtro) {
		
		String nome = filtro.getNome() == null ? "%" : filtro.getNome();
		return clientes.findByNomeContainingIgnoreCaseAndAtivo(nome, true);
	}
	
	public void excluir(Long codigo) {
		clientes.delete(codigo);
	}
	
	public Cliente localizar(Long id){
		return clientes.findOne(id);
	}
	
	public Cliente localizarLogin(String cpf){
		return clientes.findByCpf(cpf);
	}
	
	public void incluir(Cliente cliente){
               
        clientes.save(cliente);
    }
	
	public void salvar(Cliente cliente) {
		
		clientes.save(cliente);
		
	}
	
}
