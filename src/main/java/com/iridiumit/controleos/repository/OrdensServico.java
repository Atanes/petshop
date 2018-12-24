package com.iridiumit.controleos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iridiumit.controleos.model.OrdemServico;

public interface OrdensServico extends JpaRepository<OrdemServico, Long>{
	
	List<OrdensServico> findByStatus(String status);

}
