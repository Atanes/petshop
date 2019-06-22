package com.iridiumit.gestaopetshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iridiumit.gestaopetshop.model.Colaborador;

public interface Colaboradores extends JpaRepository<Colaborador, Long>{

	List<Colaborador> findByNomeContainingIgnoreCase(String nome);
	
	List<Colaborador> findByFuncao(String funcao);
	
	Colaborador findByCpf(String cpf);
	
	Colaborador findByMatricula(Long matricula);

}
