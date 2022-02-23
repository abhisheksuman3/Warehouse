package com.axisb.ews.rest;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.axisb.ews.entity.EWS_User;
import com.axisb.ews.jwt.JwtTokenUtil;
import com.axisb.ews.model.GeneralResponse;
import com.axisb.ews.model.JwtRequest;
import com.axisb.ews.model.JwtRequest_RT;
import com.axisb.ews.model.JwtResponse;
import com.axisb.ews.services.ApplicationService;

@RestController
@CrossOrigin
public class JwtAuthenticationController {
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	ApplicationService appService;
	@Value("${ldap.test}")
	private boolean ldap;
	
	
	@RequestMapping(value = "/api/login", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
		
		
		HashMap<String,String> detail=authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		if (detail!=null) {

			List<com.axisb.ews.entity.EWS_User> userDetailFromDB = appService
					.authorizeUser(authenticationRequest.getUsername().toUpperCase());
			
			System.out.println("userDetailFromDB "+userDetailFromDB.size());
			
			if (userDetailFromDB != null && userDetailFromDB.size() != 0
					&& userDetailFromDB.get(0).getId().equalsIgnoreCase(authenticationRequest.getUsername())) {
				
				String refreshToken = null;
				if(userDetailFromDB.get(0).getAuth()!=1)
				{
					appService.logLogin(userDetailFromDB.get(0),detail,0,"User is not authorized by approver, please contact admin");
					return new ResponseEntity<GeneralResponse>(
							new GeneralResponse(401, "User is not authorized by approver, please contact admin"),
							HttpStatus.UNAUTHORIZED);
				}
				else if(userDetailFromDB.get(0).getActive()!=1)
				{
					appService.logLogin(userDetailFromDB.get(0),detail,0,"User is locked, please contact admin");
					return new ResponseEntity<GeneralResponse>(
							new GeneralResponse(401, "User is locked, please contact admin"),
							HttpStatus.UNAUTHORIZED);
				}
				else
				{
					Long logid=appService.logLogin(userDetailFromDB.get(0),detail,1,"Logged in");
					final String token = jwtTokenUtil.generateToken(authenticationRequest.getUsername(),
							authenticationRequest.getPassword(), userDetailFromDB.get(0),logid);
					
					
					if(logid!=null)
					{
						return ResponseEntity.ok(new JwtResponse(token, refreshToken,detail.get("name"),userDetailFromDB.get(0).getRole()));
					}
					else
					{
						return new ResponseEntity<GeneralResponse>(
								new GeneralResponse(401, "User unable to log in database"),
								HttpStatus.UNAUTHORIZED);
					}
				}
			} else
				
			{
				appService.logLogin(new EWS_User(authenticationRequest.getUsername(), "", 0, 0),detail,0,"User is Unauthorized to access, please contact admin.");
				return new ResponseEntity<GeneralResponse>(
						new GeneralResponse(401, "User is Unauthorized to access, please contact admin"),
						HttpStatus.UNAUTHORIZED);
			}
		} else {
			appService.logLogin(new EWS_User(authenticationRequest.getUsername(), "", 0, 0),detail,0,"Wrong username or password");
			return new ResponseEntity<GeneralResponse>(new GeneralResponse(401, "Wrong username or password"),
					HttpStatus.UNAUTHORIZED);
		}
	}

	private HashMap<String, String> authenticate(String username, String password) throws Exception {
		
		HashMap<String, String> details=null;
		try {
			
			details= LDAP.authenticate(username, password,ldap);
			System.out.println("hello "+details);
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
		return details;
	}
}