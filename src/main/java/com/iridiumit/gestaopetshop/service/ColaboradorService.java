package com.iridiumit.gestaopetshop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iridiumit.gestaopetshop.model.Colaborador;
import com.iridiumit.gestaopetshop.repository.Colaboradores;
import com.iridiumit.gestaopetshop.repository.filtros.ColaboradorFiltro;

@Service
public class ColaboradorService{
	
	@Autowired
	private Colaboradores colaboradores;
		
	public List<Colaborador> filtrar(ColaboradorFiltro filtro) {
		
		String nome = filtro.getNome() == null ? "%" : filtro.getNome();
		return colaboradores.findByNomeContainingIgnoreCase(nome);
	}
	
	public List<Colaborador> filtrar(String nome) {
		
		String pesquisa = nome == null ? "%" : nome;
		return colaboradores.findByNomeContainingIgnoreCase(pesquisa);
	}
	
	public void excluir(Long codigo) {
		colaboradores.delete(codigo);
	}
	
	public Colaborador localizarMatricula(long matricula){
		return colaboradores.findByMatricula(matricula);
	}
	
	public Colaborador localizarCpf(String cpf){
		return colaboradores.findByCpf(cpf);
	}
	
	public void incluir(Colaborador colaborador){
               
		colaboradores.save(colaborador);
    }
	
	public void salvar(Colaborador colaborador) {
		
		colaboradores.save(colaborador);
		
	}
	
}
