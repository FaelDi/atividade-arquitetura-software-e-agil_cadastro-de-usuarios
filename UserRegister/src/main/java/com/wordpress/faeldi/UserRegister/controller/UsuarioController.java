package com.wordpress.faeldi.UserRegister.controller;

import com.wordpress.faeldi.UserRegister.model.dto.UsuarioDTO;
import com.wordpress.faeldi.UserRegister.service.UsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
@Slf4j
public class UsuarioController extends BaseController<UsuarioDTO, UsuarioService>  {
	
	public UsuarioController(UsuarioService service) {
		super(service);
	}

}
