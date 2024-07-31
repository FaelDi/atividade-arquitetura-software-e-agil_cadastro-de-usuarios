package com.wordpress.faeldi.UserRegister.model.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.wordpress.faeldi.UserRegister.model.dto.UsuarioDTO;
import com.wordpress.faeldi.UserRegister.model.entity.Usuario;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
	List<UsuarioDTO> parseListDTO(List<Usuario> clientes);
	List<Usuario> parseListEntity(List<UsuarioDTO> clientes);

	UsuarioDTO parseDTO(Usuario cliente);

	Usuario parseEntity(UsuarioDTO cliente);
}
