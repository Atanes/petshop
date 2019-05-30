package com.iridiumit.gestaopetshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iridiumit.gestaopetshop.model.Fornecedor;

public interface Fornecedores extends JpaRepository<Fornecedor, Long>{
	
	List<Fornecedor> findByNome(String nome);
	
	List<Fornecedor> findByNomeContainingIgnoreCase(String nome);
	
	Fornecedor findByCnpj(String cnpj);
	
	Fornecedor findByContato(String contato);

}
