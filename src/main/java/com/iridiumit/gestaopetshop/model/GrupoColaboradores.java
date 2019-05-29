package com.iridiumit.gestaopetshop.model;

public enum GrupoColaboradores {
	
	CAIXA("Caixa"), 
	ATENDENTE("Atendente"), 
	VETERINARIO("Veterinário"), 
	APOIO("Apoio"),
	GERENTE("Gerente");
	
	private String descricao;
	
	GrupoColaboradores(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao(){
		return descricao;
	}

}
