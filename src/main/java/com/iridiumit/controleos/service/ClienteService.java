package com.iridiumit.controleos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iridiumit.controleos.model.Cliente;
import com.iridiumit.controleos.repository.Clientes;
import com.iridiumit.controleos.repository.filtros.ClienteFiltro;

@Service
public class ClienteService{
	
	@Autowired
	private Clientes clientes;
		
	public List<Cliente> filtrar(ClienteFiltro filtro) {
		
		String nome = filtro.getCpf_nome() == null ? "%" : filtro.getCpf_nome();
		return clientes.findByNomeContaining(nome);
	}
	
	public List<Cliente> filtrar(String cpf_nome) {
		
		String pesquisa = cpf_nome == null ? "%" : cpf_nome;
		return clientes.findByNomeContaining(pesquisa);
	}
	
	public void excluir(Long codigo) {
		clientes.delete(codigo);
	}
	
	public Cliente localizar(Long id){
		return clientes.findOne(id);
	}
	
	public Cliente localizarLogin(String cpf){
		return clientes.findByCpfcnpj(cpf);
	}
	
	public void incluir(Cliente cliente){
               
        clientes.save(cliente);
    }
	
	public void salvar(Cliente cliente) {
		
		clientes.save(cliente);
		
	}
	
}
