package com.iridiumit.controleos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iridiumit.controleos.model.Permissao;

public interface Permissoes extends JpaRepository<Permissao, Long>{
	
	Permissao findByNome(String nome);

}
