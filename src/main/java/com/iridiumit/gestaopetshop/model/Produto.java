package com.iridiumit.gestaopetshop.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.NotBlank;

@Entity
public class Produto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank (message = "{descricaoproduto.not.blank}")
	private String descricao;
	
	@NotBlank (message = "{tipoproduto.not.blank}")
	private String tipo;
	
	@NotBlank (message = "{unidademedida.not.blank}")
	private String unidadeMedida;
	
	@NotBlank (message = "{qtdproduto.not.blank}")
	private Float qtd;
	
	@NotBlank (message = "{valorcompra.not.blank}")
	private Double valorCompra;
	
	@NotBlank (message = "{valorvenda.not.blank}")
	private Double valorVenda;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fornecedor_id", nullable = false)
    private Fornecedor fornecedor;
}
