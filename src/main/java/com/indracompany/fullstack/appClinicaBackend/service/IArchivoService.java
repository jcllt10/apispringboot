package com.indracompany.fullstack.appClinicaBackend.service;

import com.indracompany.fullstack.appClinicaBackend.model.Archivo;

public interface IArchivoService {
	
	int guardar(Archivo archivo);
	byte[] leerArchivo(Integer idArchivo);

}
