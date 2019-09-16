package com.iridiumit.gestaopetshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iridiumit.gestaopetshop.model.Animal;
import com.iridiumit.gestaopetshop.model.Cliente;
import com.iridiumit.gestaopetshop.model.Raca;

public interface Animais extends JpaRepository<Animal, Long>{
	
	List<Animal> findByNome(String nome);
	
	List<Animal> findByCliente (Cliente c);

	List<Animal> findByNomeContainingIgnoreCase(String nome);
	
	List<Animal> findByRaca(Raca raca);

}
