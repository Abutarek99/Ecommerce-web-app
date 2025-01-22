package com.ecommerce.ecommerceapp.security.jwt;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import com.ecommerce.ecommerceapp.security.user.LocalUserDetails;
import com.ecommerce.ecommerceapp.security.user.LocalUserDetailsService;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthTokenFilter extends OncePerRequestFilter{
	@Autowired
	private JwtUtils jwtUtils;
	@Autowired
	private LocalUserDetailsService userDetailsService;
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String userJwtToken = parseJwt(request);
			if(StringUtils.hasText(userJwtToken) && jwtUtils.validateToken(userJwtToken)) {
				String userName = jwtUtils.getUsernameFromToken(userJwtToken);
				UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
				var auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		}
	    catch(JwtException ex) {
	    	response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	    	response.getWriter().write(ex.getMessage());
	    	return;
	    }
		catch(Exception ex) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().write(ex.getMessage());
			return;
		}
		filterChain.doFilter(request, response);
	}
	
	public String parseJwt(HttpServletRequest request) {
		String authHeader = request.getHeader("Authorization");
		if(StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) 
			return authHeader.substring(7);
		else
			return null;
	}

}
