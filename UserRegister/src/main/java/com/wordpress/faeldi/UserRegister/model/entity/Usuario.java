package com.wordpress.faeldi.UserRegister.model.entity;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Usuario {
	
	@Id
	@GeneratedValue(strategy=GenerationType.UUID)
	private String id;

	@CPF
	@Column(name="cpf", nullable = false,unique = true)
	private String cpf;

	@Column(name="nome", nullable=false, unique=true)
	private String nome;

	@Column(name="email", nullable=false, unique=true)
	private String email;

	@Column(name="telefones", nullable=false, unique=true)
	private String telefones;

	@Column(name="endereco", nullable=false, unique=true)
	private String endereco;
}
