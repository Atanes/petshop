package com.iridiumit.controleos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iridiumit.controleos.model.Cliente;

public interface Clientes extends JpaRepository<Cliente, Long>{

	List<Cliente> findByNomeContainingIgnoreCase(String nome);
	
	Cliente findByCpfcnpj(String cpf_cnpj);

}
