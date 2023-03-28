package com.wordpress.faeldi.UserRegister.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wordpress.faeldi.UserRegister.model.dto.UsuarioDTO;
import com.wordpress.faeldi.UserRegister.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController extends BaseController<UsuarioDTO, UsuarioService>  {
	
	public UsuarioController(UsuarioService service) {
		super(service);
	}

}
