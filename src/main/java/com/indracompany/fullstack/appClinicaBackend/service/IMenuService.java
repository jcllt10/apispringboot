package com.indracompany.fullstack.appClinicaBackend.service;

import java.util.List;

import com.indracompany.fullstack.appClinicaBackend.model.Menu;

public interface IMenuService extends ICRUD<Menu>{
	
	List<Menu> listarMenuPorUsuario(String nombre);
}
