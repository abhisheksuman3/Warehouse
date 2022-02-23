package com.axisb.ews.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "EWS_FILE_UPLOAD_LOG")
public class EWS_File_Upload_Log {
	public EWS_File_Upload_Log(String user_id, Date action_time, String action_type, String ad_info, String error_info,int deleted) {
		super();
		this.user_id = user_id;
		this.action_time = action_time;
		this.action_type = action_type;
		this.ad_info = ad_info;
		this.error_info = error_info;
		this.deleted=deleted;
	}
	
	
	public int getDeleted() {
		return deleted;
	}


	public void setDeleted(int display) {
		this.deleted = display;
	}


	public EWS_File_Upload_Log(String user_id, Date action_time, String action_type, String ad_info, String error_info) {
		super();
		this.user_id = user_id;
		this.action_time = action_time;
		this.action_type = action_type;
		this.ad_info = ad_info;
		this.error_info = error_info;
	
	}

	public EWS_File_Upload_Log() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	@Override
	public String toString() {
		return "EWS_File_Upload_Log [id=" + id + ", user_id=" + user_id + ", action_time=" + action_time
				+ ", action_type=" + action_type + ", ad_info=" + ad_info + ", error_info=" + error_info + ", display="
				+ deleted + "]";
	}



	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name="USER_ID",nullable = false)
	private String user_id;
	
	@Column(name="ACTION_TIME", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date action_time;
	
	@Column(name="ACTION_TYPE")
	private String action_type;
	
	@Column(name="AD_INFO",length = 1023)
	private String ad_info;
	
	@Column(name="ERROR_INFO",length = 1023)
	private String error_info;
	
	@Column(name="deleted",length=1)
	private int deleted=1;

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


	
	
	
}
