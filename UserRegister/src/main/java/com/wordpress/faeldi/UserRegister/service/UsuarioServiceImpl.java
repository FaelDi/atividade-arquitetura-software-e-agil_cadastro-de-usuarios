package com.wordpress.faeldi.UserRegister.service;


import com.wordpress.faeldi.UserRegister.model.dto.UsuarioDTO;
import com.wordpress.faeldi.UserRegister.model.entity.Usuario;
import com.wordpress.faeldi.UserRegister.model.mapper.UsuarioMapper;
import com.wordpress.faeldi.UserRegister.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService{

	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
	private UsuarioMapper mapper;
	
	
	public List<UsuarioDTO> buscarTodos() {
		return mapper.parseListDTO(repository.findAll());
	}

	public UsuarioDTO buscarUm(String id) {
		Optional<Usuario> usuarioOp = repository.findById(id);
		if(usuarioOp.isPresent()) {
			Usuario usuario = usuarioOp.get();
			return mapper.parseDTO(usuario);
		}

		throw new EntityNotFoundException();
	}

	public UsuarioDTO criar(UsuarioDTO clienteDTO) {
		Usuario usuario = mapper.parseEntity(clienteDTO);
		usuario.setNome(clienteDTO.getNome());
		usuario.setEmail(clienteDTO.getEmail());
		usuario.setEndereco(clienteDTO.getEndereco());
		usuario.setCpf(clienteDTO.getCpf());
		usuario.setId(null);
		repository.save(usuario);
		return mapper.parseDTO(usuario);
	}

	public UsuarioDTO editar(String id, UsuarioDTO usuarioDTO) {

		Optional<Usuario> usuarioOp = repository.findById(id);

		if(usuarioOp.isPresent()) {
			Usuario usuario = usuarioOp.get();
			usuario.setNome(usuarioDTO.getNome());
			usuario.setEmail(usuarioDTO.getEmail());
			usuario.setId(id);
			usuario = repository.save(usuario);
			return mapper.parseDTO(usuario);
		}

		throw new EntityNotFoundException();
	}

	public void excluir(String id) {
		if(!repository.existsById(id)) {
			throw new EntityNotFoundException();
		}

		repository.deleteById(id);
	}

}
