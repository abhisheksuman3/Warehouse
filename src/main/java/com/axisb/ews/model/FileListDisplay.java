package com.axisb.ews.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;

public class FileListDisplay implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7059171945298968603L;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public Date getAction_time() {
		return action_time;
	}

	public void setAction_time(Date action_time) {
		this.action_time = action_time;
	}

	public String getAction_type() {
		return action_type;
	}

	public void setAction_type(String action_type) {
		this.action_type = action_type;
	}

	public String getAd_info() {
		return ad_info;
	}

	public void setAd_info(String ad_info) {
		this.ad_info = ad_info;
	}

	public String getError_info() {
		return error_info;
	}

	public void setError_info(String error_info) {
		this.error_info = error_info;
	}

	public int getDeleted() {
		return deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	private long id;
	
	private String user_id;
	
	private Date action_time;
	
	private String action_type;
	
	private String ad_info;
	
	private String error_info;
	
	private int deleted=1;
	
	private String size;

	public FileListDisplay(long id, String user_id, Date action_time, String action_type, String ad_info,
			String error_info, int deleted, String size) {
		super();
		this.id = id;
		this.user_id = user_id;
		this.action_time = action_time;
		this.action_type = action_type;
		this.ad_info = ad_info;
		this.error_info = error_info;
		this.deleted = deleted;
		this.size = size;
	}
	
	
}
