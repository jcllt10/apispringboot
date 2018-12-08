package com.indracompany.fullstack.appClinicaBackend.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.indracompany.fullstack.appClinicaBackend.dao.IUsuarioDAO;
import com.indracompany.fullstack.appClinicaBackend.model.Usuario;


@Service("userDetailsService")
public class UserServiceImpl implements UserDetailsService{

	@Autowired
	private IUsuarioDAO userDAO;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario user = userDAO.findOneByUsername(username); //from usuario where username := username
		
		if (user == null) {
			throw new UsernameNotFoundException(String.format("Usuario no existe", username));
		}
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		
		//al usar el comando EAGER en la declaracion del modelo, spring nos trae todos lo roles del usuario y eso lo usamos
		//en esta parte del codigo
		user.getRoles().forEach( role -> {
			//luego enviamos todos lo roles del usuario encontrado
			authorities.add(new SimpleGrantedAuthority(role.getNombre()));
		});
		
		//la clase UserDetailses propia de spring
		UserDetails userDetails = new User(user.getUsername(), user.getPassword(), authorities);
		
		return userDetails;
	}
	
}
