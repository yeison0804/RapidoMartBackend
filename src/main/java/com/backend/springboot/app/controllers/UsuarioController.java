package com.backend.springboot.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.backend.springboot.app.dto.CodigoDTO;
import com.backend.springboot.app.dto.RespuestaDTO;
import com.backend.springboot.app.dto.UsuarioDTO;
import com.backend.springboot.app.services.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	UsuarioService usuarioService;

	@PostMapping("/enviarcorreo")
	@CrossOrigin(origins = "*")
	@ResponseBody
	public ResponseEntity<?> enviarCorreo(@RequestBody UsuarioDTO usuarioDTO) {
		RespuestaDTO respuestaDTO = usuarioService.recordarContrasena(usuarioDTO);
		return ResponseEntity.ok(respuestaDTO);
	}

	@PostMapping("/login")
	@CrossOrigin(origins = "*")
	@ResponseBody
	public ResponseEntity<?> login(@RequestBody UsuarioDTO usuarioDTO) {
		UsuarioDTO usuarioLogeado = usuarioService.login(usuarioDTO);
		if (usuarioLogeado == null) {
			RespuestaDTO respuestaDTO = new RespuestaDTO();
			respuestaDTO.setMensaje("Correo o Contraseña incorrectos por favor verifique sus datos");
			return ResponseEntity.ok(respuestaDTO);
		} else {
			return ResponseEntity.ok(usuarioLogeado);
		}

	}
	
	@PostMapping("/create")
	@CrossOrigin(origins = "*")
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody UsuarioDTO usuarioDTO) {
			RespuestaDTO respuestaDTO = usuarioService.crearUsuario(usuarioDTO);
			return ResponseEntity.ok(respuestaDTO);		

	}
	
	
	@PostMapping("/validarcodigo")
	@CrossOrigin(origins = "*")
	@ResponseBody
	public ResponseEntity<?> validarCodigo(@RequestBody CodigoDTO codigoDTO) {
		RespuestaDTO respuestaDTO = usuarioService.validarClaveTemporal(codigoDTO);
		return ResponseEntity.ok(respuestaDTO);
	}
	
	
	@PostMapping("/actualizarcontrasena")
	@CrossOrigin(origins = "*")
	@ResponseBody
	public ResponseEntity<?> actualizarContrasena(@RequestBody UsuarioDTO usuarioDTO) {
		RespuestaDTO respuestaDTO = usuarioService.actualizarContrasena(usuarioDTO);
		return ResponseEntity.ok(respuestaDTO);
	}


}
