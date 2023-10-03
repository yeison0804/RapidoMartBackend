package com.backend.springboot.app.services;

import com.backend.springboot.app.dto.RespuestaDTO;
import com.backend.springboot.app.dto.UsuarioDTO;

public interface UsuarioService {

	public UsuarioDTO login(UsuarioDTO usuarioDTO);
	
	public RespuestaDTO crearUsuario(UsuarioDTO usuarioDTO);
	
	public RespuestaDTO recordarContrasena(UsuarioDTO usuarioDTO);
}
