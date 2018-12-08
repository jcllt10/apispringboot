package com.indracompany.fullstack.appClinicaBackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.indracompany.fullstack.appClinicaBackend.model.Medico;
import com.indracompany.fullstack.appClinicaBackend.service.IMedicoService;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

	@Autowired
	private IMedicoService service;

	//este metodo devuelve true o false, y de acuerdo a eso accedera o o no al recurso
	//@PreAuthorize("@restAuthService.hasAccess('listar')")
	@GetMapping(produces = "application/json")
	public List<Medico> listar() {
		return service.listar();
	}

	//solo los usuarios que tengan el rol de ADMIN o USER en la bd pueden acceder a este recurso
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	@GetMapping(value = "/{id}", produces = "application/json")
	public Medico listarPorId(@PathVariable("id") Integer id) {
		return service.listarId(id);
	}
	
	@PostMapping(produces = "application/json", consumes = "application/json")
	public Medico registrar(@RequestBody Medico medico) {
		return service.registrar(medico);
	}
	
	@PutMapping(produces = "application/json", consumes = "application/json")
	public Medico modificar(@RequestBody Medico medico) {
		return service.modificar(medico);
	}
	
	@DeleteMapping(value = "/{id}")
	public void eliminar(@PathVariable("id") Integer id) {
		service.eliminar(id);
	}
	
}
