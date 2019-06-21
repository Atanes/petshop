package com.iridiumit.gestaopetshop.model;

public enum TipoConsulta {
	
	PRIMEIRA("1ª Consulta"),
	NOVA("Nova"),
	RETORNO("Retorno");
	
	private String descricao;
	
	TipoConsulta(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao(){
		return descricao;
	}

}
