package com.wordpress.faeldi.UserRegister.model.dto;

import org.hibernate.validator.constraints.br.CPF;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioDTO {

    private String id;
    private String nome;
    private String email;

    @CPF
    private String cpf;

    private String telefones;

    private String endereco;

}
