package com.indracompany.fullstack.appClinicaBackend.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.indracompany.fullstack.appClinicaBackend.model.Medico;

public interface IMedicoDAO extends JpaRepository<Medico, Integer>{

}
