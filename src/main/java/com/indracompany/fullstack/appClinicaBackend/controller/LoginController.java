package com.indracompany.fullstack.appClinicaBackend.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.indracompany.fullstack.appClinicaBackend.model.ResetToken;
import com.indracompany.fullstack.appClinicaBackend.model.Usuario;
import com.indracompany.fullstack.appClinicaBackend.service.ILoginService;
import com.indracompany.fullstack.appClinicaBackend.service.IResetTokenService;
import com.indracompany.fullstack.appClinicaBackend.util.EmailService;
import com.indracompany.fullstack.appClinicaBackend.util.Mail;



@RestController
@RequestMapping("/login")
public class LoginController {

	@Autowired
	private ILoginService service;

	@Autowired
	private IResetTokenService tokenService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private BCryptPasswordEncoder bcrypt;
	//indicamos que el cliente enviara el coreeo que es un texto con MediaType.TEXT_PLAIN_VALUE
	@PostMapping(value = "/enviarCorreo", consumes = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<Integer> enviarCorreo(@RequestBody String correo) {
		int rpta = 0;
		try {

			Usuario us = service.verificarNombreUsuario(correo);
			System.out.println("el valor del usuario es:" + us.getIdUsuario());
			if (us != null && us.getIdUsuario() > 0) {

				ResetToken token = new ResetToken();
				token.setToken(UUID.randomUUID().toString());
				token.setUsuario(us);
				token.setExpiracion(1);//indicamos que el token expirara en 1min
				tokenService.guardar(token);

				Mail mail = new Mail();
				//mail.setFrom("email.prueba.demo@gmail.com");
				mail.setFrom("josearmin824@gmail.com");
				mail.setTo(us.getUsername());
				mail.setSubject("RESTABLECER CONTRASEÑA - MEDIAPP");

				Map<String, Object> model = new HashMap<>();
				String url = "http://localhost:4200/recuperar/" + token.getToken();
				model.put("user", token.getUsuario().getUsername());
				model.put("resetUrl", url);
				mail.setModel(model);
				System.out.println("valores del email :" + mail.getFrom() + " " + mail.getTo() + " " + token.getUsuario().getUsername());
				emailService.sendEmail(mail);
				rpta = 1;
				System.out.println("valor de respuesta :" + rpta);
			}

		} catch (Exception e) {
			return new ResponseEntity<Integer>(rpta, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Integer>(rpta, HttpStatus.OK);
	}
	
}
