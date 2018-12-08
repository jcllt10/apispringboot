package com.indracompany.fullstack.appClinicaBackend.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.indracompany.fullstack.appClinicaBackend.dto.ConsultaDTO;
import com.indracompany.fullstack.appClinicaBackend.dto.ConsultaListaExamenDTO;
import com.indracompany.fullstack.appClinicaBackend.dto.ConsultaResumenDTO;
import com.indracompany.fullstack.appClinicaBackend.dto.FiltroConsultaDTO;
import com.indracompany.fullstack.appClinicaBackend.model.Archivo;
import com.indracompany.fullstack.appClinicaBackend.model.Consulta;
import com.indracompany.fullstack.appClinicaBackend.service.IArchivoService;
import com.indracompany.fullstack.appClinicaBackend.service.IConsultaService;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

	@Autowired
	private IConsultaService service;
	
	@Autowired
	private IArchivoService serviceArchivo;

	@GetMapping(produces = "application/json")
	public List<Consulta> listar() {
		return service.listar();
	}

	@GetMapping(value = "/{id}", produces = "application/json")
	public Consulta listarPorId(@PathVariable("id") Integer id) {
		return service.listarId(id);
	}
	
	@PostMapping(produces = "application/json", consumes = "application/json")
	public Consulta registrar(@RequestBody ConsultaListaExamenDTO consultaDTO) {
		return service.registrarTransaccional(consultaDTO);
	}
	
	@PutMapping(produces = "application/json", consumes = "application/json")
	public Consulta modificar(@RequestBody Consulta consulta) {
		return service.modificar(consulta);
	}
	
	@DeleteMapping
	public void eliminar(@PathVariable("id") Integer id) {
		service.eliminar(id);
	}
	
	@GetMapping(value = "/hateoas", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ConsultaDTO> listarHateoas() {
		List<Consulta> consultas = new ArrayList<>();
		List<ConsultaDTO> consultasDTO = new ArrayList<>();
		consultas = service.listar();

		for (Consulta c : consultas) {
			ConsultaDTO d = new ConsultaDTO();
			d.setIdConsulta(c.getIdConsulta());
			d.setMedico(c.getMedico());
			d.setPaciente(c.getPaciente());

			ControllerLinkBuilder linkTo = linkTo(methodOn(ConsultaController.class).listarPorId((c.getIdConsulta())));
			d.add(linkTo.withSelfRel());
			consultasDTO.add(d);

			//pacientes/2
			ControllerLinkBuilder linkTo1 = linkTo(methodOn(PacienteController.class).listarPorId((c.getPaciente().getIdPaciente())));
			d.add(linkTo1.withSelfRel());
			consultasDTO.add(d);

			ControllerLinkBuilder linkTo2 = linkTo(methodOn(MedicoController.class).listarPorId((c.getMedico().getIdMedico())));
			d.add(linkTo2.withSelfRel());
			consultasDTO.add(d);
		}
		return consultasDTO;
	}
	
	@PostMapping(value = "/buscar", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Consulta>> buscar(@RequestBody FiltroConsultaDTO filtro) {
		List<Consulta> consultas = new ArrayList<>();

		if (filtro != null) {
			if(filtro.getFechaConsulta() != null) {
				consultas = service.buscarfecha(filtro);	
			}else {
				consultas = service.buscar(filtro);
			}			
		}
		return new ResponseEntity<List<Consulta>>(consultas, HttpStatus.OK);
	}
	
	@GetMapping(value = "/listarResumen", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ConsultaResumenDTO>> listarResumen() {
		List<ConsultaResumenDTO> consultas = new ArrayList<>();
		consultas = service.listarResumen();
		return new ResponseEntity<List<ConsultaResumenDTO>>(consultas, HttpStatus.OK);
	}
	
	//el mediaType soporta cualquier tipo de archivo multimedia
	@GetMapping(value = "/generarReporte", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<byte[]> generarReporte() {
		byte[] data = null;
		data = service.generarReporte();
		return new ResponseEntity<byte[]>(data, HttpStatus.OK);
	}
	
	//usamos MediaType.APPLICATION_OCTET_STREAM_VALUE para recuperar el arreglo de bytes(archivo) enviados desde el cliente
	@PostMapping(value = "/guardarArchivo", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<Integer> guardarArchivo(@RequestParam("file") MultipartFile file) throws IOException{
		int rpta = 0;
		Archivo ar = new Archivo();
		ar.setFilename(file.getName());
		ar.setValue(file.getBytes());
		rpta = serviceArchivo.guardar(ar);
		
		return new ResponseEntity<Integer>(rpta, HttpStatus.OK);
	}
	
	@GetMapping(value = "/leerArchivo/{idArchivo}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<byte[]> leerArchivo(@PathVariable("idArchivo") Integer idArchivo) throws IOException {
				
		byte[] arr = serviceArchivo.leerArchivo(idArchivo); 

		return new ResponseEntity<byte[]>(arr, HttpStatus.OK);
	}
	
	
}
