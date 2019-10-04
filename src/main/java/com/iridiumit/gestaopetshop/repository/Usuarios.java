package com.iridiumit.gestaopetshop.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.iridiumit.gestaopetshop.model.Permissao;
import com.iridiumit.gestaopetshop.model.Usuario;

public interface Usuarios extends JpaRepository<Usuario, Long>, PagingAndSortingRepository<Usuario, Long> {
	
	Usuario findByLogin(String login);
	
	Usuario findByCpf(String cpf);
	
	List<Usuario> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
	
	List<Usuario> findByPermissoes(Permissao p);
	
	Page<Usuario> findAll(Pageable pageable);

}
