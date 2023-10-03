package com.backend.springboot.app.dto;

public class UsuarioDTO {
	
	public String nombre;
	public String correo;
	public String contrasena;
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getContrasena() {
		return contrasena;
	}
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	@Override
	public String toString() {
		return "UsuarioDTO [nombre=" + nombre + ", correo=" + correo + ", contrasena=" + contrasena + "]";
	}
	
	
	
	

}
