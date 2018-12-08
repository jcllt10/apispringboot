package com.indracompany.fullstack.appClinicaBackend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.indracompany.fullstack.appClinicaBackend.dao.ILoginDAO;
import com.indracompany.fullstack.appClinicaBackend.model.Usuario;
import com.indracompany.fullstack.appClinicaBackend.service.ILoginService;



@Service
public class LoginServiceImpl implements ILoginService{

	@Autowired
	private ILoginDAO dao;

	@Override
	public int cambiarClave(String clave, String nombre) {
		int rpta = 0;
		try {
			dao.cambiarClave(clave, nombre);
			rpta = 1;
		} catch (Exception e) {
			rpta = 0;
		}
		return rpta;
	}
	
	@Override
	public Usuario verificarNombreUsuario(String usuario) throws Exception {
		Usuario us = null;
		try {
			us = dao.verificarNombreUsuario(usuario);
			us = us != null ? us : new Usuario();
		} catch (Exception e) {
			us = new Usuario();
		}
		return us;
	}
	
}
