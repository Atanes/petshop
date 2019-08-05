package com.iridiumit.gestaopetshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iridiumit.gestaopetshop.model.Servico;

public interface Servicos extends JpaRepository<Servico, Long> {
	
	List<Servico> findAllByOrderByDescricao();

}
