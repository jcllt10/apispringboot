package com.indracompany.fullstack.appClinicaBackend.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.indracompany.fullstack.appClinicaBackend.dao.IConsultaExamenDAO;
import com.indracompany.fullstack.appClinicaBackend.model.ConsultaExamen;
import com.indracompany.fullstack.appClinicaBackend.service.IConsultaExamenService;

@Service
public class ConsultaExamenServiceImpl implements IConsultaExamenService{

	@Autowired
	private IConsultaExamenDAO dao;
	
	@Override
	public List<ConsultaExamen> listarExamenesPorConsulta(Integer idconsulta) {
		return dao.listarExamenesPorConsulta(idconsulta);
	}

}
