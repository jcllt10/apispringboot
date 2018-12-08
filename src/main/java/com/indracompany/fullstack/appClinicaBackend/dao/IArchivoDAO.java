package com.indracompany.fullstack.appClinicaBackend.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.indracompany.fullstack.appClinicaBackend.model.Archivo;

public interface IArchivoDAO extends JpaRepository<Archivo, Integer>{

}
