package com.iridiumit.gestaopetshop.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

@Entity
public class Cliente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank (message = "{name.not.blank}")
	private String nome;
	@NotBlank(message = "{CPF_CNPJ.not.blank}")
	private String cpfcnpj;
	
	private String telefone;
	@NotBlank(message = "{celular.not.blank}")
	private String celular;
	@NotBlank(message = "{email.not.blank}")
	@Email(message = "{email.not.valid}")
	private String email;
	
	@OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "cliente")
    private Set<Animal> animal = new HashSet<>();
	
	public Cliente(){
		
	}
	
	public Cliente(Long id, String nome, String cpfcnpj, String email, Set<Animal> animal) {
		super();
		this.id = id;
		this.nome = nome;
		this.cpfcnpj = cpfcnpj;
		this.email = email;
		this.animal = animal;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome.toUpperCase();
	}
	
	public String getCpfcnpj() {
		return cpfcnpj;
	}

	public void setCpfcnpj(String cpf_cnpj) {
		this.cpfcnpj = cpf_cnpj;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email.toUpperCase();
	}

	public Set<Animal> getAnimal() {
		return animal;
	}

	public void setAnimal(Set<Animal> animal) {
		this.animal = animal;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Cliente other = (Cliente) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}