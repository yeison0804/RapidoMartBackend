package com.backend.springboot.app.services.impl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.springboot.app.dto.RespuestaDTO;
import com.backend.springboot.app.dto.UsuarioDTO;
import com.backend.springboot.app.model.Usuario;
import com.backend.springboot.app.services.UsuarioService;
import com.backend.springboot.app.util.EmailService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	EmailService emailService;

	/**
	 * Usuarios predeterminados
	 */
	List<Usuario> usuarios = Stream.of(new Usuario("Yeison Andres Marin", "yeison0804@hotmail.com", "12345"),
			new Usuario("Mauricio Marin Martinez", "maomm26@gmail.com", "4321")).collect(Collectors.toList());

	/**
	 * Buscar en el arraylist si existe un usuario con el correo y contraseña
	 * devuelve un usuario logeado si se encontro algun usuario con
	 */
	@Override
	public UsuarioDTO login(UsuarioDTO usuarioDTO) {
		// TODO Auto-generated method stub
		System.out.println("Servicio de login");
		try {
			Usuario encontrado = usuarios.stream().filter(us -> us.getCorreo().equals(usuarioDTO.getCorreo())
					&& us.getContrasena().equals(usuarioDTO.getContrasena())).findFirst().orElse(null);
			if (encontrado != null) {
				UsuarioDTO usuarioLogeado = new UsuarioDTO();
				usuarioLogeado.setNombre(encontrado.getNombre());
				usuarioLogeado.setContrasena(encontrado.getContrasena());
				usuarioLogeado.setCorreo(encontrado.getCorreo());
				System.out.println("Usuario logeado " + encontrado);
				return usuarioLogeado;
			} else {
				return null;
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			
		}
		return null;

	}

	/**
	 * Crea un usuario, valida que no exista un usuario con ese correo
	 */
	@Override
	public RespuestaDTO crearUsuario(UsuarioDTO usuarioDTO) {
		// TODO Auto-generated method stub
		try {
			RespuestaDTO respuestaDTO = new RespuestaDTO();
			Usuario encontrado = usuarios.stream().filter(us -> us.getCorreo().equals(usuarioDTO.getCorreo())).findFirst()
					.orElse(null);
			if (encontrado != null) {
				respuestaDTO.setMensaje("Ya existe un usuario con ese correo");
				System.out.println("Usuario encontrado " + encontrado);
				return respuestaDTO;
			} else {
				Usuario usuarioNuevo = new Usuario(usuarioDTO.getNombre(), usuarioDTO.getCorreo(), usuarioDTO.getContrasena());
				usuarios.add(usuarioNuevo);
				respuestaDTO.setMensaje("Usuario agregado con exito");
				return respuestaDTO;

			}
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return null;

	}

	
	/**
	 * Envia correo de recuperacion de contraseña
	 */
	@Override
	public RespuestaDTO recordarContrasena(UsuarioDTO usuarioDTO) {
		try {
			RespuestaDTO respuestaDTO = new RespuestaDTO();
			Usuario encontrado = usuarios.stream().filter(us -> us.getCorreo().equals(usuarioDTO.getCorreo())).findFirst()
					.orElse(null);
			if (encontrado != null) {
				System.out.println("Usuario encontrado " + encontrado);
				emailService.enviarCorreo(usuarioDTO.getCorreo(), "Olvido su contraseña",
						" Su contraseña es " + encontrado.getContrasena());
				respuestaDTO.setMensaje("Se envio la contraseña al correo " + usuarioDTO.getCorreo());
				return respuestaDTO;
			} else {
				respuestaDTO.setMensaje("No existe un usuario con el correo " + usuarioDTO.getCorreo());
				return respuestaDTO;

			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

}
