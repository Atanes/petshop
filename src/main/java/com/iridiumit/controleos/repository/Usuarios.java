package com.iridiumit.controleos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iridiumit.controleos.model.Usuario;

public interface Usuarios extends JpaRepository<Usuario, Long> {
	
	Usuario findByLogin(String login);
}
