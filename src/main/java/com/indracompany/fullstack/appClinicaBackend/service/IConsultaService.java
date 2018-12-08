package com.indracompany.fullstack.appClinicaBackend.service;

import java.util.List;

import com.indracompany.fullstack.appClinicaBackend.dto.ConsultaListaExamenDTO;
import com.indracompany.fullstack.appClinicaBackend.dto.ConsultaResumenDTO;
import com.indracompany.fullstack.appClinicaBackend.dto.FiltroConsultaDTO;
import com.indracompany.fullstack.appClinicaBackend.model.Consulta;

public interface IConsultaService extends ICRUD<Consulta>{

	Consulta registrarTransaccional(ConsultaListaExamenDTO consultaDTO);
	
	List<Consulta> buscar(FiltroConsultaDTO filtro);

	List<Consulta> buscarfecha(FiltroConsultaDTO filtro);

	List<ConsultaResumenDTO> listarResumen();
	
	byte[] generarReporte();
}
