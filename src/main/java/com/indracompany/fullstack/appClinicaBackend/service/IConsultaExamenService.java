package com.indracompany.fullstack.appClinicaBackend.service;

import java.util.List;

import com.indracompany.fullstack.appClinicaBackend.model.ConsultaExamen;;

public interface IConsultaExamenService {

	List<ConsultaExamen> listarExamenesPorConsulta(Integer idconsulta);

}
