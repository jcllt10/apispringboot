package com.indracompany.fullstack.appClinicaBackend.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(ConsultaExamenPK.class)
//camelCase 
//UpperCamelCase   NombreCompleto
//lowerCamelCase   nombreCompleto
public class ConsultaExamen {  //consulta_examen

	@Id
	private Examen examen;

	@Id
	private Consulta consulta;

	public Examen getExamen() {
		return examen;
	}

	public void setExamen(Examen examen) {
		this.examen = examen;
	}

	public Consulta getConsulta() {
		return consulta;
	}

	public void setConsulta(Consulta consulta) {
		this.consulta = consulta;
	}
	
}
