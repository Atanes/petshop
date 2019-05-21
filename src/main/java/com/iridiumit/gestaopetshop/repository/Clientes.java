package com.iridiumit.gestaopetshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iridiumit.gestaopetshop.model.Cliente;

public interface Clientes extends JpaRepository<Cliente, Long>{

	List<Cliente> findByNomeContainingIgnoreCase(String nome);
	
	Cliente findByCpfcnpj(String cpf_cnpj);

}
