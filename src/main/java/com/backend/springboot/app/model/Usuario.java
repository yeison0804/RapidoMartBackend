package com.backend.springboot.app.model;

public class Usuario {

	public String nombre;
	public String correo;
	public String contrasena;
	public String genero;
	public String ciudad;
	
	
	
	
	public Usuario(String nombre, String correo, String contrasena, String genero, String ciudad) {
		super();
		this.nombre = nombre;
		this.correo = correo;
		this.contrasena = contrasena;
		this.genero = genero;
		this.ciudad = ciudad;
	}
	public String getNombre() {
		return nombre;
	}
	public String getGenero() {
		return genero;
	}
	public void setGenero(String genero) {
		this.genero = genero;
	}
	public String getCiudad() {
		return ciudad;
	}
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
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
		return "Usuario [nombre=" + nombre + ", correo=" + correo + ", contrasena=" + contrasena + ", genero=" + genero
				+ ", ciudad=" + ciudad + "]";
	}
	
	
}
