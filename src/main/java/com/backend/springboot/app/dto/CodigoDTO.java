package com.backend.springboot.app.dto;

public class CodigoDTO {
	
	String correo;
	int codigo;
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	@Override
	public String toString() {
		return "CodigoDTO [correo=" + correo + ", codigo=" + codigo + "]";
	}
	
	
	
	
	

}
