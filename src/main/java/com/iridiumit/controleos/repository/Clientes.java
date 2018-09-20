package com.iridiumit.controleos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iridiumit.controleos.model.Cliente;

public interface Clientes extends JpaRepository<Cliente, Long>{

	Cliente findByCpfcnpj(String cpf_cnpj);

}
