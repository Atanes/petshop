package com.iridiumit.gestaopetshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iridiumit.gestaopetshop.model.Permissao;

public interface Permissoes extends JpaRepository<Permissao, Long>{
	
	Permissao findByNome(String nome);

}
