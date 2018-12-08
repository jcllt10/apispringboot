package com.indracompany.fullstack.appClinicaBackend.service;

import java.util.List;

//implementamos una interface generica que aceptara cualquier tipo de objeto, que gestionara los metodos crud
public interface ICRUD<T> {

	T registrar(T t);

	T modificar(T t);

	void eliminar(int id);

	T listarId(int id);

	List<T> listar();

}
