package com.axisb.ews.model;

import java.io.Serializable;

public class JwtResponse implements Serializable {
	
	private static final long serialVersionUID = -8660201796989358408L;
	private final String accesstoken;
	private final String refreshtoken;
	private final String name;
	private final String role;

	public String getName() {
		return name;
	}

	
	

	public String getToken() {
		return this.accesstoken;
	}
	public JwtResponse(String accesstoken, String refreshtoken, String name,String role) {
		super();
		this.accesstoken = accesstoken;
		this.refreshtoken = refreshtoken;
		this.name = name;
		this.role=role;
	}




	public String getRole() {
		return role;
	}




	public String getRefreshToken() {
		return this.refreshtoken;
	}
}