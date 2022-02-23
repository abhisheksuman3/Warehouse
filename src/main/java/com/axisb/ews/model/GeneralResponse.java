package com.axisb.ews.model;

import java.io.Serializable;
import java.util.Date;


public class GeneralResponse implements Serializable
{

	private static final long serialVersionUID = 2062717934606998719L;
	private String timestamp;
	private int status;
	private String message;
	
	
	public GeneralResponse(int status, String message) {
		super();
		this.timestamp = new Date().toString();
		this.status = status;
		this.message = message;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
