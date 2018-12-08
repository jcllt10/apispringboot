package com.indracompany.fullstack.appClinicaBackend.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.indracompany.fullstack.appClinicaBackend.model.Examen;

public interface IExamenDAO extends JpaRepository<Examen, Integer>{

}
