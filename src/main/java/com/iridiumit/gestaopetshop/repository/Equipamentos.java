package com.iridiumit.gestaopetshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iridiumit.gestaopetshop.model.Cliente;
import com.iridiumit.gestaopetshop.model.Equipamento;

public interface Equipamentos extends JpaRepository<Equipamento, Long>{

	List<Equipamento> findByCliente(Cliente c);
}
