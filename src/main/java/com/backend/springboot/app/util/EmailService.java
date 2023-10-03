package com.backend.springboot.app.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	
	private final JavaMailSender javaMailSender;

	@Autowired
	public EmailService(JavaMailSender javaMailSender) {
		super();
		this.javaMailSender = javaMailSender;
	}
	
	public void enviarCorreo(String destinatario, String asunto, String mensaje ) {
		
		try {
			SimpleMailMessage mailMessage= new SimpleMailMessage();
			mailMessage.setFrom("maomm25@hotmail.com");
			mailMessage.setTo(destinatario);
			mailMessage.setSubject(asunto);
			mailMessage.setText(mensaje);
			System.out.println(destinatario);
			javaMailSender.send(mailMessage);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		
	}
	
	
	
	
	
}
