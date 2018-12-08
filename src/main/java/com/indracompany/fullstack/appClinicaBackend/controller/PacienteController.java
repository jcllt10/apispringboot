package com.indracompany.fullstack.appClinicaBackend.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.hateoas.*;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.indracompany.fullstack.appClinicaBackend.exception.ModeloNotFoundException;
import com.indracompany.fullstack.appClinicaBackend.model.Paciente;
import com.indracompany.fullstack.appClinicaBackend.service.IPacienteService;

@RestController
@RequestMapping("/pacientes")
//@CrossOrigin
public class PacienteController {

	@Autowired
	private IPacienteService service;

	@GetMapping(produces = "application/json")
	public ResponseEntity<List<Paciente>> listar() {//se una la Clase ResponseEntity para manipular la peticion HTTP
		List<Paciente> pacientes = new ArrayList<>();
		pacientes = service.listar();
		return new ResponseEntity<List<Paciente>>(pacientes, HttpStatus.OK);
	}
	
	@GetMapping(value="/pageable", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<Paciente>> listarPageable(Pageable pageable){
		Page<Paciente> pacientes;
		pacientes = service.listarPageable(pageable);
		return new ResponseEntity<Page<Paciente>>(pacientes, HttpStatus.OK);
	}

	@GetMapping(value = "/{id}", produces = "application/json")
	public /*org.springframework.hateoas.Resource<*/Paciente/*>*/ listarPorId(@PathVariable("id") Integer id) {
		Paciente pac = service.listarId(id);
		if (pac == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO: " + id);
		}
		
		//localhost:8080/pacientes/1
		org.springframework.hateoas.Resource<Paciente> resource = new org.springframework.hateoas.Resource<Paciente>(pac);
		ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).listarPorId(id));
		resource.add(linkTo.withRel("paciente-resource"));
		
		return service.listarId(id);
		//return resource;
	}
	
	@PostMapping(produces = "application/json", consumes = "application/json")
	public ResponseEntity<Object> registrar(@RequestBody Paciente paciente) {
		Paciente pac = new Paciente();
		pac = service.registrar(paciente);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")//devuelve la url del recurso creado
				.buildAndExpand(pac.getIdPaciente()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@PutMapping(produces = "application/json", consumes = "application/json")
	public ResponseEntity<Object> modificar(@RequestBody Paciente paciente) {
		service.modificar(paciente);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/{id}")
	public void eliminar(@PathVariable("id") Integer id) {
		Paciente pac = service.listarId(id);
		if (pac == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO: " + id);
		} else {
			service.eliminar(id);
		}
	}
	
}
