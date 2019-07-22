package com.iridiumit.gestaopetshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iridiumit.gestaopetshop.model.Raca;

public interface Racas extends JpaRepository<Raca, Long> {
	
	List<Raca> findAllByOrderByNome();

}
