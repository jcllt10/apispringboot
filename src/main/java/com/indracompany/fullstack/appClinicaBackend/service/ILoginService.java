package com.indracompany.fullstack.appClinicaBackend.service;

import com.indracompany.fullstack.appClinicaBackend.model.Usuario;

public interface ILoginService {

	Usuario verificarNombreUsuario(String usuario) throws Exception;
	int cambiarClave(String clave, String nombre) throws Exception;
	
}
