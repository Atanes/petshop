package com.iridiumit.controleos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iridiumit.controleos.model.Cliente;
import com.iridiumit.controleos.model.Equipamento;

public interface Equipamentos extends JpaRepository<Equipamento, Long>{

	List<Equipamento> findByCliente(Cliente c);
}
