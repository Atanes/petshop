package com.iridiumit.controleos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iridiumit.controleos.model.Usuario;

public interface Usuarios extends JpaRepository<Usuario, Long> {
	
	Usuario findByLogin(String login);
	
	List<Usuario> findByNomeContainingIgnoreCase(String nome);
}
