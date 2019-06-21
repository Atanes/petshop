package com.iridiumit.gestaopetshop.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.NotBlank;

@Entity
public class Consulta {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;
	
	@Column(name="data_registro")
	@Temporal(TemporalType.DATE)
	private Date data_registro;
	
	@Column(name="data_confirmacao")
	@Temporal(TemporalType.DATE)
	private Date data_confirmacao;
	
	@Column(name="data_atendimento")
	@Temporal(TemporalType.DATE)
	private Date data_atendimento;
	
	@NotBlank (message = "{sintomas.not.blank}")
	private String sintomas;
	
	@NotBlank (message = "{diagnostico.not.blank}")
	private String diagnostico;
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "animal_id", nullable = false)
    private Animal animal;
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "colaborador_matricula", nullable = false)
    private Colaborador colaborador;

	public Consulta(){
		
	}
	
	public Consulta(Long codigo, Date data_registro, Date data_confirmacao, Date data_atendimento, String sintomas,
			String diagnostico, Animal animal, Colaborador colaborador) {
		
		this.codigo = codigo;
		this.data_registro = data_registro;
		this.data_confirmacao = data_confirmacao;
		this.data_atendimento = data_atendimento;
		this.sintomas = sintomas;
		this.diagnostico = diagnostico;
		this.animal = animal;
		this.colaborador = colaborador;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Date getData_registro() {
		return data_registro;
	}

	public void setData_registro(Date data_registro) {
		this.data_registro = data_registro;
	}

	public Date getData_confirmacao() {
		return data_confirmacao;
	}

	public void setData_confirmacao(Date data_confirmacao) {
		this.data_confirmacao = data_confirmacao;
	}

	public Date getData_atendimento() {
		return data_atendimento;
	}

	public void setData_atendimento(Date data_atendimento) {
		this.data_atendimento = data_atendimento;
	}

	public String getSintomas() {
		return sintomas;
	}

	public void setSintomas(String sintomas) {
		this.sintomas = sintomas;
	}

	public String getDiagnostico() {
		return diagnostico;
	}

	public void setDiagnostico(String diagnostico) {
		this.diagnostico = diagnostico;
	}

	public Animal getAnimal() {
		return animal;
	}

	public void setAnimal(Animal animal) {
		this.animal = animal;
	}

	public Colaborador getColaborador() {
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((animal == null) ? 0 : animal.hashCode());
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
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
		Consulta other = (Consulta) obj;
		if (animal == null) {
			if (other.animal != null)
				return false;
		} else if (!animal.equals(other.animal))
			return false;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

}
