package com.wordpress.faeldi.UserRegister.model.mapper;

import com.wordpress.faeldi.UserRegister.model.dto.UsuarioDTO;
import com.wordpress.faeldi.UserRegister.model.entity.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
	List<UsuarioDTO> parseListDTO(List<Usuario> clientes);
	List<Usuario> parseListEntity(List<UsuarioDTO> clientes);

	UsuarioDTO parseDTO(Usuario cliente);

	Usuario parseEntity(UsuarioDTO cliente);
}
