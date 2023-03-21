package com.wordpress.faeldi.UserRegister.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

@Data
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
