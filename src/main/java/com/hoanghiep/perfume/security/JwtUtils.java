package com.hoanghiep.perfume.security;

import java.util.Base64;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.hoanghiep.perfume.exception.JwtAuthenticationException;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtUtils {

//	@Qualifier("userDetailsServiceImpl")
//	@Lazy
//	private final UserDetailsService userDetailsServive;

//	@Value("${com.hoanghiep.jwt.header}")
//	private String authorizationHeader;

	@Value("${com.hoanghiep.jwt.secretKey}")
	private String secretKey;

	@Value("${com.hoanghiep.jwt.expiration}")
	private long expirationInMilliSeconds;

	@PostConstruct
	protected void init() {
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
	}

	public String createToken(String username, String role) {
		Claims claims = Jwts.claims().setSubject(username);
		claims.put("role", role);
		Date now = new Date();
		Date validity = new Date(now.getTime() + expirationInMilliSeconds);

		return Jwts.builder().setClaims(claims).setIssuedAt(now).setExpiration(validity)
				.signWith(SignatureAlgorithm.HS256, secretKey).compact();
	}

	public boolean validateJwtToken(String token) {
		try {
			Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
			//System.err.println("validate token return: " + !claimsJws.getBody().getExpiration().before(new Date()));
			return !claimsJws.getBody().getExpiration().before(new Date());
		} catch (SignatureException e) {
			log.error("Invalid JWT signature: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			log.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			log.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			log.error("JWT token is unsupported: {}", e.getMessage());
		} catch (JwtException | IllegalArgumentException exception) {
			throw new JwtAuthenticationException("JWT token is expired or invalid", HttpStatus.UNAUTHORIZED);
		}
		return false;
	}

//    public Authentication getAuthentication(String token) {
//        UserDetails userDetails = this.userDetailsServive.loadUserByUsername(getUserNameFromJwtToken(token));
//        return new UsernamePasswordAuthenticationToken(userDetails, " ", userDetails.getAuthorities());
//    }

	public String getUserNameFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
	}

//    public String resolveToken(HttpServletRequest request) {
//    	System.err.println("request: "+ request+ "resolve token:" + request.getHeader(authorizationHeader));
//        return request.getHeader(authorizationHeader);
//    }
}
