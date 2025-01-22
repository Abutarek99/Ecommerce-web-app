package com.ecommerce.ecommerceapp.security.jwt;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import com.ecommerce.ecommerceapp.security.user.LocalUserDetails;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {
	@Value("${auth.token.jwtSecret}")
	private String jwtSecret;
	@Value("${auth.token.expirationInMiles}")
	private int expirationTime;
	
	public String generateTokenForUser(Authentication authentication) {
		LocalUserDetails userPrinciple = (LocalUserDetails)authentication.getPrincipal();
		Set<String> roles = userPrinciple.getAuthorities().stream()
		             .map(GrantedAuthority :: getAuthority)
		             .collect(Collectors.toSet());
		return Jwts.builder()
				.setSubject(userPrinciple.getEmail())
				.claim("id", userPrinciple.getId())
				.claim("roles", roles)
				.setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + expirationTime))
				.signWith(key(), SignatureAlgorithm.HS256).compact();
	}
	
	private Key key() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
	}
	
	public String getUsernameFromToken(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(key())
				.build()
				.parseClaimsJws(token)
				.getBody().getSubject();
	}
	
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder()
		     .setSigningKey(key())
		     .build()
		     .parseClaimsJws(token);
			 return true;
		}
		catch(UnsupportedJwtException | MalformedJwtException 
				| IllegalArgumentException ex) {
			throw new JwtException(ex.getMessage());
		}
	}
}
