package com.axisb.ews.model;

import java.io.Serializable;

public class GeneralRequest implements Serializable{

	@Override
	public String toString() {
		return "GeneralRequest [key=" + key + ", value=" + value + "]";
	}
	private static final long serialVersionUID = 7357189601260945238L;
	private String key;
	private String value;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}
