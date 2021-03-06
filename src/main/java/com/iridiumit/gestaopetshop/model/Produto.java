package com.iridiumit.gestaopetshop.model;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

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
	
	@NotNull (message = "{qtdproduto.not.null}")
	private Float qtd;
	
	@NotNull (message = "{valorcompra.not.null}")
	@DecimalMin(value = "0.01", message = "Valor de compra minimo do produto não pode ser menor que R$ 0,01")
	@NumberFormat(style = Style.NUMBER, pattern = "#,##0.00")
	private BigDecimal valorCompra;
	
	@NotNull (message = "{valorvenda.not.null}")
	@DecimalMin(value = "0.50", message = "Valor de venda minimo do produto não pode ser menor que R$ 0,50")
	@NumberFormat(style = Style.NUMBER, pattern = "#,##0.00")
	private BigDecimal valorVenda;
	
	@Column(name="data_validade")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Future (message = "{data_validade.mustbe.future}")
	private Date data_validade;
	
	private String lote;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fornecedor_id", nullable = false)
    private Fornecedor fornecedor;

	public Produto(){
		
	}
	
	public Produto(Long id, String descricao, String tipo, String unidadeMedida, Float qtd, BigDecimal valorCompra,
			BigDecimal valorVenda, Date data_validade, String lote, Fornecedor fornecedor) {
		this.id = id;
		this.descricao = descricao;
		this.tipo = tipo;
		this.unidadeMedida = unidadeMedida;
		this.qtd = qtd;
		this.valorCompra = valorCompra;
		this.valorVenda = valorVenda;
		this.data_validade = data_validade;
		this.lote = lote;
		this.fornecedor = fornecedor;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getUnidadeMedida() {
		return unidadeMedida;
	}

	public void setUnidadeMedida(String unidadeMedida) {
		this.unidadeMedida = unidadeMedida;
	}

	public Float getQtd() {
		return qtd;
	}

	public void setQtd(Float qtd) {
		this.qtd = qtd;
	}

	public BigDecimal getValorCompra() {
		return valorCompra;
	}

	public void setValorCompra(BigDecimal valorCompra) {
		this.valorCompra = valorCompra;
	}

	public BigDecimal getValorVenda() {
		return valorVenda;
	}

	public void setValorVenda(BigDecimal valorVenda) {
		this.valorVenda = valorVenda;
	}

	public Date getData_validade() {
		return data_validade;
	}

	public void setData_validade(Date data_validade) {
		this.data_validade = data_validade;
	}
	
	public String getDataFormatada(Date data) {
		Date d = data; 
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy"); 
		return formato.format(d);
	}

	public String getLote() {
		return lote;
	}

	public void setLote(String lote) {
		this.lote = lote;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
			return false;
		return true;
	}
	
	
}
