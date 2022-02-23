package com.axisb.ews.model;

import java.io.Serializable;

public class JwtRequest_RT implements Serializable {
	private static final long serialVersionUID = 5926468583005150707L;
	
	private String refreshToken;

	public JwtRequest_RT()
	{
	}
	public JwtRequest_RT(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}
