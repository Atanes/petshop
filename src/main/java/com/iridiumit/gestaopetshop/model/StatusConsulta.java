package com.iridiumit.gestaopetshop.model;

public enum StatusConsulta {
	
	AGENDADA("Agendada"), 
	ATENDIMENTO("Em atendimento"), 
	FINALIZADA("Finalizada"), 
	RETORNO("Retorno");
	
	private String descricao;
	
	StatusConsulta(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao(){
		return descricao;
	}

}
