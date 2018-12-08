package com.indracompany.fullstack.appClinicaBackend.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.indracompany.fullstack.appClinicaBackend.model.Usuario;

public interface IUsuarioDAO extends JpaRepository<Usuario, Integer> {

	//al usar el findOneBy , por defecto spring hace un select * from usuario where username='al valor que se le envia por parametro'
	Usuario findOneByUsername(String username);
	
}

