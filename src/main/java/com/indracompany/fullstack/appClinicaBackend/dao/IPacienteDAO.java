package com.indracompany.fullstack.appClinicaBackend.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.indracompany.fullstack.appClinicaBackend.model.Paciente;

public interface IPacienteDAO extends JpaRepository<Paciente, Integer> {

}
