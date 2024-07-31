package com.wordpress.faeldi.UserRegister.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.wordpress.faeldi.UserRegister.model.dto.UsuarioDTO;
import com.wordpress.faeldi.UserRegister.model.entity.Usuario;
import com.wordpress.faeldi.UserRegister.model.mapper.UsuarioMapper;
import com.wordpress.faeldi.UserRegister.repository.UsuarioRepository;

import jakarta.persistence.EntityNotFoundException;

@SpringBootTest
@ActiveProfiles("test")
public class UsuarioServiceImplTest {

    @Mock
    private UsuarioRepository repository;

    @Mock
    private UsuarioMapper mapper;

    @InjectMocks
    private UsuarioServiceImpl service;

    private Usuario usuario;
    private UsuarioDTO usuarioDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        usuario = new Usuario();
        usuario.setNome("Fulano");
        usuario.setEmail("fulano@teste.com");
        usuario.setEndereco("Rua Teste, 123");
        usuario.setCpf("123.456.789-00");
        usuario.setId("1");

        usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome("Fulano");
        usuarioDTO.setEmail("fulano@teste.com");
        usuarioDTO.setEndereco("Rua Teste, 123");
        usuarioDTO.setCpf("123.456.789-00");
    }

    @Test
    void buscarTodos() {
        List<Usuario> usuarios = new ArrayList<>();
        usuarios.add(usuario);

        when(repository.findAll()).thenReturn(usuarios);
        when(mapper.parseListDTO(usuarios)).thenReturn(List.of(usuarioDTO));

        List<UsuarioDTO> result = service.buscarTodos();

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0)).isEqualTo(usuarioDTO);
    }

    @Test
    void buscarUm() {
        when(repository.findById(usuario.getId())).thenReturn(Optional.of(usuario));
        when(mapper.parseDTO(usuario)).thenReturn(usuarioDTO);

        UsuarioDTO result = service.buscarUm(usuario.getId());

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(usuarioDTO);
    }

    @Test
    void buscarUm_UsuarioNaoEncontrado() {
        when(repository.findById(usuario.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            service.buscarUm(usuario.getId());
        });
    }

    @Test
    void criar() {
        when(mapper.parseEntity(any(UsuarioDTO.class))).thenReturn(usuario);
        when(repository.save(any(Usuario.class))).thenReturn(usuario);
        when(mapper.parseDTO(usuario)).thenReturn(usuarioDTO);

        UsuarioDTO result = service.criar(usuarioDTO);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(usuarioDTO);
    }
    
    @Test
    public void testEditar() {
        // given
        String id = "1";
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome("João");
        usuarioDTO.setEmail("joao@teste.com");
        usuarioDTO.setEndereco("Rua A, 123");
        usuarioDTO.setCpf("12345678910");

        Usuario usuario = new Usuario();
        usuario.setNome("João");
        usuario.setEmail("joao@teste.com");
        usuario.setEndereco("Rua A, 123");
        usuario.setCpf("12345678910");
        usuario.setId(id);

        when(repository.findById(id)).thenReturn(Optional.of(usuario));
        when(repository.save(usuario)).thenReturn(usuario);
        when(mapper.parseDTO(usuario)).thenReturn(usuarioDTO);

        // when
        UsuarioDTO result = service.editar(id, usuarioDTO);

        // then
        Assertions.assertEquals(usuarioDTO, result);
        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).save(usuario);
        verify(mapper, times(1)).parseDTO(usuario);
    }

    @Test
    public void testEditarComUsuarioInexistente() {
        // given
        String id = "1";
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome("João");
        usuarioDTO.setEmail("joao@teste.com");
        usuarioDTO.setEndereco("Rua A, 123");
        usuarioDTO.setCpf("12345678910");

        when(repository.findById(id)).thenReturn(Optional.empty());

        // when
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            service.editar(id, usuarioDTO);
        });

        // then
        verify(repository, times(1)).findById(id);
        verify(repository, never()).save(any());
        verify(mapper, never()).parseDTO(any());
    }

    @Test
    public void testExcluir() {
        // given
        String id = "1";
        when(repository.existsById(id)).thenReturn(true);

        // when
        service.excluir(id);

        // then
        verify(repository, times(1)).existsById(id);
        verify(repository, times(1)).deleteById(id);
    }


}