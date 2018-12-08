package com.indracompany.fullstack.appClinicaBackend.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.indracompany.fullstack.appClinicaBackend.model.ResetToken;

public interface IResetTokenDAO extends JpaRepository<ResetToken, Long> {

	//from RestToken where token = :? 	
	ResetToken findByToken(String token);
		
}
