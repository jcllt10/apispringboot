package com.indracompany.fullstack.appClinicaBackend.service;

import com.indracompany.fullstack.appClinicaBackend.model.ResetToken;

public interface IResetTokenService {

    ResetToken findByToken(String token);
	
	void guardar(ResetToken token);
	
	void eliminar(ResetToken token);
	
}
