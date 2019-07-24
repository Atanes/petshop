package com.iridiumit.gestaopetshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iridiumit.gestaopetshop.model.Usuario;

public interface Usuarios extends JpaRepository<Usuario, Long> {
	
	Usuario findByLogin(String login);
	
	Usuario findByCpf(String cpf);
	
	List<Usuario> findByNomeContainingIgnoreCase(String nome);
}
