package com.iridiumit.gestaopetshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iridiumit.gestaopetshop.model.OrdemServico;

public interface OrdensServico extends JpaRepository<OrdemServico, Long>{
	
	List<OrdemServico> findByStatus(String status);

}
