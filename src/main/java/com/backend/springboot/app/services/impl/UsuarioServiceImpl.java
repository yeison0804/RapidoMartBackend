package com.backend.springboot.app.services.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.springboot.app.dto.CodigoDTO;
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
	List<Usuario> usuarios = Stream
			.of(new Usuario("Yeison Andres Marin", "yeison_584@hotmail.com", "12345", "hombre", "medellin"),
					new Usuario("oscar valencia", "cheviotin2001@gmail.com", "4321", "hombre", "bogota"))
			.collect(Collectors.toList());
	HashMap<String, Integer> clavesTemporales = new HashMap<>();

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
				usuarioLogeado.setGenero(encontrado.getGenero());
				usuarioLogeado.setCiudad(encontrado.getCiudad());
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
		try {
			RespuestaDTO respuestaDTO = new RespuestaDTO();

			// Validar campos, por ejemplo:
			if (usuarioDTO.getNombre() == null || usuarioDTO.getNombre().isEmpty() || usuarioDTO.getCorreo() == null
					|| usuarioDTO.getCorreo().isEmpty() || usuarioDTO.getContrasena() == null
					|| usuarioDTO.getContrasena().isEmpty() || usuarioDTO.getGenero() == null
					|| usuarioDTO.getGenero().isEmpty() || usuarioDTO.getCiudad() == null
					|| usuarioDTO.getCiudad().isEmpty()) {
				respuestaDTO.setMensaje("Todos los campos son obligatorios");
				return respuestaDTO;
			}

			// Verificar si ya existe un usuario con el mismo correo
			Usuario encontrado = usuarios.stream().filter(us -> us.getCorreo().equals(usuarioDTO.getCorreo()))
					.findFirst().orElse(null);

			if (encontrado != null) {
				respuestaDTO.setMensaje("Ya existe un usuario con ese correo");
				System.out.println("Usuario encontrado " + encontrado);
				return respuestaDTO;
			} else {
				Usuario usuarioNuevo = new Usuario(usuarioDTO.getNombre(), usuarioDTO.getCorreo(),
						usuarioDTO.getContrasena(), usuarioDTO.getGenero(), usuarioDTO.getCiudad());

				// Aquí puedes llamar a tu capa de persistencia para guardar el usuario en la
				// base de datos.
				usuarios.add(usuarioNuevo);

				respuestaDTO.setMensaje("Usuario agregado con exito");
				return respuestaDTO;
			}
		} catch (Exception e) {
			e.printStackTrace(); // Agrega logs o manejo de excepciones según sea necesario

			// Si ocurre una excepción, devolver un mensaje de error
			RespuestaDTO respuestaDTO = new RespuestaDTO();
			respuestaDTO.setMensaje("Ocurrió un error al procesar la solicitud");
			return respuestaDTO;
		}
	}

	/**
	 * Envia correo de recuperacion de contraseña
	 */
	@Override
	public RespuestaDTO recordarContrasena(UsuarioDTO usuarioDTO) {
		try {
			RespuestaDTO respuestaDTO = new RespuestaDTO();
			Usuario encontrado = usuarios.stream().filter(us -> us.getCorreo().equals(usuarioDTO.getCorreo()))
					.findFirst().orElse(null);
			if (encontrado != null) {
				// Crear una instancia de la clase Random
				Random random = new Random();

				// Generar un número aleatorio de 4 dígitos
				int numeroAleatorio = random.nextInt(9000) + 1000;

				System.out.println("Usuario encontrado " + encontrado);
				emailService.enviarCorreo(usuarioDTO.getCorreo(), "Codigo temporal para recuperacion de contraseña",
						" Su contraseña es " + numeroAleatorio);
				respuestaDTO.setMensaje("Ok. Se envio el codigo temporal al correo " + usuarioDTO.getCorreo());
				clavesTemporales.put(encontrado.getCorreo(), numeroAleatorio);
				System.out.println(clavesTemporales);
				return respuestaDTO;
			} else {
				respuestaDTO.setMensaje("Error. No existe un usuario con el correo " + usuarioDTO.getCorreo());
				return respuestaDTO;

			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public RespuestaDTO validarClaveTemporal(CodigoDTO codigoDTO) {
		Integer clave = clavesTemporales.get(codigoDTO.getCorreo());
		RespuestaDTO respuestaDTO = new RespuestaDTO();
		System.out.println(clave);
		if (clave == null) {
			respuestaDTO.setMensaje("Error. No existe un codigo de recuperacion asociado al correo");
		} else {
			if (clave == codigoDTO.getCodigo()) {

				respuestaDTO.setMensaje("Ok. se ha validado el codigo con exito");

			} else {

				respuestaDTO.setMensaje("Error. codigo incorrecto");

			}

		}

		return respuestaDTO;

	}

	@Override
	public RespuestaDTO actualizarContrasena(UsuarioDTO usuarioDTO) {
		RespuestaDTO respuestaDTO = new RespuestaDTO();

		usuarios.stream().filter(usuario -> usuario.getCorreo().equals(usuarioDTO.getCorreo())).findFirst()
				.ifPresentOrElse(usuario -> {
					usuario.setContrasena(usuarioDTO.getContrasena());
					respuestaDTO.setMensaje("Ok. Contraseña actualizada");
				},

						() -> {
							System.out.println("Correo no encontrado: " + usuarioDTO.getCorreo());
							respuestaDTO.setMensaje(
									"Error. No se encontro usuario con el correo " + usuarioDTO.getCorreo());

						});

		return respuestaDTO;
	}

}
