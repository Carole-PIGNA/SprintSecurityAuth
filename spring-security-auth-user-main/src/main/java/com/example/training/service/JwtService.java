package com.example.training.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Service
public class JwtService {

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	private Claims extractAllClaims(String token) {
		return Jwts
				.parserBuilder()
				.setSigningKey(getSigninKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
	private Key getSigninKey() {
		String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367569";
		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	public String generateToken(Map<String, Object> extraClaims,UserDetails userDetails) {
		return Jwts
				.builder()
				.setClaims(extraClaims).
				setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(24)))
				.signWith(getSigninKey(),SignatureAlgorithm.HS256)
				.compact();
	}
	public Boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
	}
	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

}