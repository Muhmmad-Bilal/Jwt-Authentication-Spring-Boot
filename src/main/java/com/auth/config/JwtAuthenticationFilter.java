package com.auth.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth.Services.CustomUserDetailsService;
import com.auth.helper.JwtUtil;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	@Autowired
	CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String requestHeaderToken=request.getHeader("Authorization");
		String username=null;
		String jwtToken=null;
		if(requestHeaderToken!=null && requestHeaderToken.startsWith("Bearer "))
		{
			jwtToken=	requestHeaderToken.substring(7);
			
			try
			{
				
			username=	jwtUtil.extractUsername(jwtToken);
				
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			
			UserDetails details=this.customUserDetailsService.loadUserByUsername(username);
			if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null)
			{
			UsernamePasswordAuthenticationToken authenticationToken=	new UsernamePasswordAuthenticationToken(details,null,details.getAuthorities());
			
			authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);;
			
			
			}
			else {
				System.out.println("Token is not  Valided");
			}
		
			
			
		}
		filterChain.doFilter(request, response);
		
		
		
	}

}
