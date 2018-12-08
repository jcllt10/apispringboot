package com.indracompany.fullstack.appClinicaBackend;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

//esta clase lee el archivo application.properties y usa las varialbe declaradas en ese archivo
//establece los criterios para conectarse con los tokens, como sera la creacion de los tokens
@Configuration //indicamos que es una clase de configuracion
@EnableWebSecurity // habilitamos el spring security
@EnableGlobalMethodSecurity(prePostEnabled = true) // habilitamos el spring security
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	//obtenemos los valores del application.properties
	@Value("${security.signing-key}")
	private String signingKey;

	@Value("${security.encoding-strength}")
	private Integer encodingStrength;

	@Value("${security.security-realm}")
	private String securityRealm;
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired	
	private UserDetailsService userDetailsService;
	
	//para codificar las claves
	@Autowired
	private BCryptPasswordEncoder bcrypt;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}
	
	
	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	
	//verificamos si el password ingresado es el correcto
	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userDetailsService).passwordEncoder(bcrypt);
	}
	
	//configuramos las peticiones http
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http		
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .httpBasic()
        .realmName(securityRealm)
        .and()
        //deshabilitamos el csrf, la generacion automatica de tokens de spring security
        .csrf()
        .disable();        
	}
	
	//para indicar como se firmaran los tokens
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey(signingKey);		
		return converter;
	}
	
	//indicamos donde se almacenaran los tokens
	@Bean
	public TokenStore tokenStore() {
		//return new JwtTokenStore(accessTokenConverter());//indicamos que los tokens seran gestionados a nivel de memoria
		return new JdbcTokenStore(this.dataSource);//indicamos que los tokens se gestionaran a nivel de base de datos
	}
	
	@Bean
	//indicamos que se cree este bean antes que todos los demas
	@Primary
	public DefaultTokenServices tokenServices() {
		DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(tokenStore());
		defaultTokenServices.setSupportRefreshToken(true);	
		defaultTokenServices.setReuseRefreshToken(false);	
		return defaultTokenServices;
	}
	
}
