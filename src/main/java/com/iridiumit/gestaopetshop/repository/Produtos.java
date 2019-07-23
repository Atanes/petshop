package com.iridiumit.gestaopetshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iridiumit.gestaopetshop.model.Fornecedor;
import com.iridiumit.gestaopetshop.model.Produto;

public interface Produtos extends JpaRepository<Produto, Long>{

	List<Produto> findByFornecedor(Fornecedor f);
	
	List<Produto> findByFornecedorAndDescricaoContainingIgnoreCase(Fornecedor f, String descricao);
	
	List<Produto> findByDescricao(String descricao);
	
	List<Produto> findByDescricaoContainingIgnoreCase(String descricao);
}
