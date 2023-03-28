package com.wordpress.faeldi.UserRegister.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.wordpress.faeldi.UserRegister.model.dto.UsuarioDTO;
import com.wordpress.faeldi.UserRegister.model.entity.Usuario;
import com.wordpress.faeldi.UserRegister.service.UsuarioService;

import jakarta.persistence.EntityNotFoundException;

@SpringBootTest
public class UsuarioControllerTest {

	@Mock
	private UsuarioService usuarioServiceMock;

	@InjectMocks
	private UsuarioController usuarioController;

	private Usuario usuario = new Usuario();
	private UsuarioDTO usuarioDTO = new UsuarioDTO();

	@BeforeEach
	public void setup() {
		usuario.setNome("Fulano");
		usuario.setEmail("fulano@test.com");

		usuarioDTO.setNome("João");
		usuarioDTO.setEmail("joao@teste.com");
		usuarioDTO.setEndereco("Rua A, 123");
		usuarioDTO.setCpf("12345678910");
	}

	@Test
	public void testBuscarTodos() {
		Mockito.when(usuarioServiceMock.buscarTodos()).thenReturn(List.of(usuarioDTO));
		ResponseEntity<List<UsuarioDTO>> response = usuarioController.buscarTodos();
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	public void testBuscarUmComIdExistente() {
		String id = "123";

		Mockito.when(usuarioServiceMock.buscarUm(id)).thenReturn(usuarioDTO);
		ResponseEntity<UsuarioDTO> response = usuarioController.buscarUm(id);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	public void testBuscarUmComIdInexistente() {
		String id = "456";
		Mockito.when(usuarioServiceMock.buscarUm(id)).thenThrow(new EntityNotFoundException());
		ResponseEntity<UsuarioDTO> response = usuarioController.buscarUm(id);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
	}

	@Test
	public void testCriarComDadosValidos() {
		Mockito.when(usuarioServiceMock.criar(usuarioDTO)).thenReturn(usuarioDTO);
		ResponseEntity<UsuarioDTO> response = usuarioController.criar(usuarioDTO);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
	}

	@Test
	public void testEditarComIdExistenteEDadosValidos() {
		String id = "123";
		Mockito.when(usuarioServiceMock.editar(id, usuarioDTO)).thenReturn(usuarioDTO);
		ResponseEntity<UsuarioDTO> response = usuarioController.editar(id, usuarioDTO);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	public void testEditarComIdInexistenteEDadosValidos() {
		String id = "456";
		Mockito.when(usuarioServiceMock.editar(id, usuarioDTO)).thenThrow(new EntityNotFoundException());
		ResponseEntity<UsuarioDTO> response = usuarioController.editar(id, usuarioDTO);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(response.getBody()).isNull();
	}

	@Test
	public void removerUsuarioExistente() {
		doNothing().when(usuarioServiceMock).excluir("1");
		ResponseEntity<Object> response = usuarioController.remover("1");
		assertEquals(HttpStatus.OK, response.getStatusCode());
		verify(usuarioServiceMock, times(1)).excluir("1");
	}

	@Test
	public void removerUsuarioInexistente() {
		doThrow(EntityNotFoundException.class).when(usuarioServiceMock).excluir("1");
		ResponseEntity<Object> response = usuarioController.remover("1");
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		verify(usuarioServiceMock, times(1)).excluir("1");
	}

}