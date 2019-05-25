package com.iridiumit.gestaopetshop.model;

public enum GruposTrabalho {
	
	CAIXA("Caixa"), 
	ATENDENTE("Atendente"), 
	VETERINARIO("Veterinário"), 
	APOIO("Apoio");
	
	private String descricao;
	
	GruposTrabalho(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao(){
		return descricao;
	}

}
