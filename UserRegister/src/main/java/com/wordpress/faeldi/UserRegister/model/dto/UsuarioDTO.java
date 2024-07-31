package com.wordpress.faeldi.UserRegister.model.dto;

import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.GenerationType;

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
