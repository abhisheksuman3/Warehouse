package com.axisb.ews.model;

import java.io.Serializable;
import java.util.Date;


public class GeneralRequestUpload implements Serializable
{

	private static final long serialVersionUID = 2062717934606998719L;
	private String table_id;
	private String file_id;
	public String getTable_id() {
		return table_id;
	}
	public void setTable_name(String table_name) {
		this.table_id = table_name;
	}
	public GeneralRequestUpload(String table_name, String file_id) {
		super();
		this.table_id = table_name;
		this.file_id = file_id;
	}
	@Override
	public String toString() {
		return "GeneralRequestUpload [table_name=" + table_id + ", file_name=" + file_id + "]";
	}
	public String getFile_id() {
		return file_id;
	}
	public void setFile_name(String file_id) {
		this.file_id = file_id;
	}
	
	

	
}
