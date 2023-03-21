package com.wordpress.faeldi.UserRegister.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import java.util.List;

@Entity
@Data
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
