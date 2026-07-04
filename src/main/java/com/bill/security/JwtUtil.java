package com.bill.security;

import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil
{

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration-ms}")
	private long expirationMs;

	private SecretKey getSigningKey()
	{
		// Uses the configured secret to build the HMAC signing key
		return Keys.hmacShaKeyFor(secret.getBytes());
	}

	// Generate a token for a user, embedding email, name and role as claims
	public String generateToken(String email, String name, String role)
	{
		Date now = new Date();
		Date expiry = new Date(now.getTime() + expirationMs);

		return Jwts.builder().subject(email).claims(Map.of("name", name, "role", role)).issuedAt(now).expiration(expiry)
				.signWith(getSigningKey()).compact();
	}

	public Claims extractAllClaims(String token)
	{
		return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();
	}

	public String extractEmail(String token)
	{
		return extractAllClaims(token).getSubject();
	}

	public String extractRole(String token)
	{
		return extractAllClaims(token).get("role", String.class);
	}

	public String extractName(String token)
	{
		return extractAllClaims(token).get("name", String.class);
	}

	public boolean isTokenValid(String token)
	{
		try
		{
			Claims claims = extractAllClaims(token);
			return claims.getExpiration().after(new Date());
		} catch (Exception e)
		{
			// Invalid signature, malformed token, or expired token
			return false;
		}
	}
}