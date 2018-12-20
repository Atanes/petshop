package com.iridiumit.controleos.model;

public enum GruposTrabalho {
	
	INFORMATICA("Informática"), 
	AUDIO("Áudio"), 
	JBL("JBL"), 
	TVMICRO("Televisão/Microondas"), 
	RECCAIXA("Receiver/Caixa"), 
	CELULAR("Celular");
	
	private String descricao;
	
	GruposTrabalho(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao(){
		return descricao;
	}

}
