package com.ecommerce.ecommerceapp.security.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ecommerce.ecommerceapp.security.jwt.AuthTokenFilter;
import com.ecommerce.ecommerceapp.security.jwt.JwtAuthEntryPoint;
import com.ecommerce.ecommerceapp.security.user.LocalUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true) 
public class EcommerceConfig {
	@Autowired
	private LocalUserDetailsService userDetailsService;
	@Autowired
	private JwtAuthEntryPoint authEntryPoint;
	private final static List<String> SECURED_URLS = List.of("/cart_items/**", "/carts/**", "/products/**", "/categories/**",
			"/users/**");

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthTokenFilter authTokenFilter() {
		return new AuthTokenFilter();
	}
	
	@Bean
	public AuthenticationManager authManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}
	
	@Bean
	public DaoAuthenticationProvider daoAuthProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
			http.csrf(AbstractHttpConfigurer::disable)
			      .exceptionHandling(exception->exception.authenticationEntryPoint(authEntryPoint))
			      .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			      .authorizeHttpRequests(auth->auth
			    		  .requestMatchers(SECURED_URLS.toArray(String[]::new)).authenticated()
			    		  .anyRequest().permitAll());
		
		http.authenticationProvider(daoAuthProvider());
		http.addFilterBefore(authTokenFilter(), UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
}
