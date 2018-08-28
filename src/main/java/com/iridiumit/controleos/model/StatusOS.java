package com.iridiumit.controleos.model;

public enum StatusOS {
	
	ANALISE("Em análise"), 
	ORCAMENTO("Aguardando Orçamento"), 
	REPARO("Em concerto"), 
	FINALIZADO("Finalizado"), 
	CANCELADO("Cancelado"), 
	STANDBY("Aguardando peças/componentes");
	
	private String descricao;
	
	StatusOS(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao(){
		return descricao;
	}

}
