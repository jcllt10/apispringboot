package com.indracompany.fullstack.appClinicaBackend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.indracompany.fullstack.appClinicaBackend.dao.IArchivoDAO;
import com.indracompany.fullstack.appClinicaBackend.model.Archivo;
import com.indracompany.fullstack.appClinicaBackend.service.IArchivoService;

@Service
public class ArchivoServiceImpl implements IArchivoService {

	@Autowired
	private IArchivoDAO dao;

	@Override
	public int guardar(Archivo archivo) {
		Archivo ar = dao.save(archivo);
		return ar.getIdArchivo() > 0 ? 1 : 0;
	}

	@Override
	public byte[] leerArchivo(Integer idArchivo) {
		return dao.findOne(idArchivo).getValue();
	}

}
