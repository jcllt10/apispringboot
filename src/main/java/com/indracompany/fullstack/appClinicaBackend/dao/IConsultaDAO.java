package com.indracompany.fullstack.appClinicaBackend.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.indracompany.fullstack.appClinicaBackend.model.Consulta;

public interface IConsultaDAO extends JpaRepository<Consulta, Integer>{

	@Query("from Consulta c where c.paciente.dni =:dni or LOWER(c.paciente.nombres) like %:nombreCompleto% or LOWER(c.paciente.apellidos) like %:nombreCompleto%")
	List<Consulta> buscar(@Param("dni")String dni, @Param("nombreCompleto")String nombreCompleto);
	
	@Query("from Consulta c where c.fecha between :fechaConsulta and :fechaSgte")
	List<Consulta> buscarFecha(@Param("fechaConsulta") LocalDateTime fechaConsulta, @Param("fechaSgte") LocalDateTime fechaSgte);
	
	//invocamos al procedimento almacenado de potsgres
	@Query(value = "select * from fn_listarResumen()", nativeQuery = true)
	List<Object[]> listarResumen();
	
	//cantidad    fecha
	//[1		,		27/11/2018]
	//[2		,		28/11/2018]
}
