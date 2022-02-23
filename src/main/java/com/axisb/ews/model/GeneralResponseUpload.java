package com.axisb.ews.model;

import java.io.Serializable;
import java.util.Date;


public class GeneralResponseUpload implements Serializable
{

	private static final long serialVersionUID = 2062717934606998719L;
	private String timestamp;
	private int status;
	private String message;
	private String no_row;
	
	
	public String getNo_row() {
		return no_row;
	}
	public void setNo_row(String no_row) {
		this.no_row = no_row;
	}
	public GeneralResponseUpload(int status, String message,String no_row) {
		super();
		this.timestamp = new Date().toString();
		this.status = status;
		this.message = message;
		this.no_row=no_row;
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
