package com.indracompany.fullstack.appClinicaBackend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.indracompany.fullstack.appClinicaBackend.dao.IResetTokenDAO;
import com.indracompany.fullstack.appClinicaBackend.model.ResetToken;
import com.indracompany.fullstack.appClinicaBackend.service.IResetTokenService;


@Service
public class ResetTokenServiceImpl implements IResetTokenService{

	@Autowired
	private IResetTokenDAO dao;

	@Override
	public ResetToken findByToken(String token) {
		return dao.findByToken(token);
	}

	@Override
	public void guardar(ResetToken token) {
		dao.save(token);

	}

	@Override
	public void eliminar(ResetToken token) { 
		dao.delete(token);
	}
	
}
