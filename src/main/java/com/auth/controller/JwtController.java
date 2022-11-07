package com.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.auth.Services.CustomUserDetailsService;
import com.auth.helper.JwtUtil;
import com.auth.models.JwtRequest;
import com.auth.models.JwtResponse;

@RestController
public class JwtController {

	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
private 	JwtUtil jwtUtil;
	
	
	
	
	@RequestMapping(value="/token",method = RequestMethod.POST)
	public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest) throws Exception
	{
		System.out.print(jwtRequest);
		try
		{
			this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(),jwtRequest.getPassword()));
			
		}catch(UsernameNotFoundException e)
		{
			e.printStackTrace();
			throw new Exception("Bad Crendisial");
		}
		
	UserDetails details	=this.customUserDetailsService.loadUserByUsername(jwtRequest.getUsername());
	String token=	this.jwtUtil.generateToken(details);
	System.out.println(token);
	
	return ResponseEntity.ok(new JwtResponse(token));
	
	}
	
}
