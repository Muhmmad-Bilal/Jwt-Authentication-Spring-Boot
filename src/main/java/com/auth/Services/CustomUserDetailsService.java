package com.auth.Services;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		if(userName.equals("Bilal"))
		{
			return new User("Bilal","Bilal",new ArrayList<>());
		}
		else
		{
			throw new UsernameNotFoundException("User not Found");
		}
		
		
	}

}
