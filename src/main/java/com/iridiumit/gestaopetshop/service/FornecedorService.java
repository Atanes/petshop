package com.iridiumit.gestaopetshop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iridiumit.gestaopetshop.model.Fornecedor;
import com.iridiumit.gestaopetshop.repository.Fornecedores;

@Service
public class FornecedorService {
	
	@Autowired
	private Fornecedores fornecedores;
	
	public List<Fornecedor> filtrar(String nome) {
		
		String pesquisa = nome == null ? "%" : nome;
		return fornecedores.findByNomeContainingIgnoreCase(pesquisa);
	}
	
	public void excluir(Long codigo) {
		fornecedores.delete(codigo);
	}
	
	public Fornecedor localizar(long id){
		return fornecedores.findOne(id);
	}
	
	public Fornecedor localizarCnpj(String cnpj){
		return fornecedores.findByCnpj(cnpj);
	}
	
	public Fornecedor localizarContato(String contato){
		return fornecedores.findByContato(contato);
	}
	
	public void salvar(Fornecedor fornecedor) {
		
		fornecedores.save(fornecedor);
		
	}

}
