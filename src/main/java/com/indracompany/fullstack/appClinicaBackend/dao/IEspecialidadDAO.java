package com.indracompany.fullstack.appClinicaBackend.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.indracompany.fullstack.appClinicaBackend.model.Especialidad;

public interface IEspecialidadDAO extends JpaRepository<Especialidad, Integer>{

}
