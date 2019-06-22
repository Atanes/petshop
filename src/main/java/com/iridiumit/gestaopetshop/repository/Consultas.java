package com.iridiumit.gestaopetshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iridiumit.gestaopetshop.model.Consulta;

public interface Consultas extends JpaRepository<Consulta, Long>{
	
	List<Consulta> findByOrderByDataAtendimento();

}
