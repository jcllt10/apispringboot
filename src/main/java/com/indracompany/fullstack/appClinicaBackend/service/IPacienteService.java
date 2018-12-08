package com.indracompany.fullstack.appClinicaBackend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.indracompany.fullstack.appClinicaBackend.model.Paciente;

public interface IPacienteService extends ICRUD<Paciente>{

	Page<Paciente> listarPageable(Pageable pageable);
}
