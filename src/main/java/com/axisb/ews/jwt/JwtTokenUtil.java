package com.axisb.ews.jwt;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.axisb.ews.entity.EWS_User;
import com.axisb.ews.services.ApplicationService;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil implements Serializable {
	
	public static final long JWT_TOKEN_VALIDITY = 60*60*12;    //12 hr             //in second
	public static final long JWT_REFRESH_TOKEN_VALIDITY = JWT_TOKEN_VALIDITY+30; 
	@Value("${jwt.secret}")
	private String secret;
	

//retrieve username from jwt token
	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}
	
	public boolean isAccessToken(String token)
	{
		return (boolean) getAllClaimsFromToken(token).get("isAccessToken");
	}
	
	//get user Object which was retrieved from db
	public EWS_User getUser(String token)
	{
		return new EWS_User((String)getAllClaimsFromToken(token).get("id"), 
				(String)getAllClaimsFromToken(token).get("role"), 
				(int)getAllClaimsFromToken(token).get("active"), 
				(int)getAllClaimsFromToken(token).get("force_active"));
		
	}
	
	public Long getLoginLogId(String token)
	{
		return (Long)getAllClaimsFromToken(token).get("logid");
	}
	
//retrieve expiration date from jwt token
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	// for retrieveing any information from token we will need the secret key
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

//check if the token has expired
	private Boolean isTokenExpired(String token) {

		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	private int Integer(long l) {
		// TODO Auto-generated method stub
		return 0;
	}


//generate token for user
	public String generateToken(String username,String password,EWS_User user,Long logid) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("isAccessToken", true);
		//claims.put("user", user);
		claims.put("id", user.getId());
		claims.put("role", user.getRole());
		claims.put("force_active", user.getForce_active());
		claims.put("active", user.getActive());
		claims.put("logid", logid);
		
		return doGenerateToken(claims, username,false);
	}
	
	public String generateRefreshToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("isAccessToken", false);
		return doGenerateToken(claims, userDetails.getUsername(),true);
	}

//while creating the token -
//1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
//2. Sign the JWT using the HS512 algorithm and secret key.
//3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
//   compaction of the JWT to a URL-safe string 
	private String doGenerateToken(Map<String, Object> claims, String subject, boolean generateRefreshToken) {
		
		Date validity=null;
		if(generateRefreshToken)
			validity=new Date(System.currentTimeMillis() + JWT_REFRESH_TOKEN_VALIDITY* 1000); //extra 5 minutes to refresh
		else 
			validity=new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000);
		
			
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(validity)
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}

//validate token
	
	public boolean validateRefreshToken(String token)
	{
		return !isTokenExpired(token);
	}
	
	public boolean validateToken(String token, String userDetails) {
		final String username = getUsernameFromToken(token);
		
		if(username.equals(userDetails) && !isTokenExpired(token) && isAccessToken(token)) 
			return true;
		
		return false;
	}

	public String getPasswordFromToken(String jwtToken) {
		// TODO Auto-generated method stub
		return null;
	}
}

