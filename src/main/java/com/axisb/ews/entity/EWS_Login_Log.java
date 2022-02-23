package com.axisb.ews.entity;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "EWS_LOGIN_LOG")
public class EWS_Login_Log {
	@Id
    private long id;
	
	@Column(name="USER_ID")
	private String user_id;
	
	@Column(name="NAME")
	private String name;
	
//	@Basic(optional = false)
//	@Column(name="LOGIN_TIME",insertable = false, updatable = false)
//	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="LOGIN_TIME", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date login_time=new Date();
	
	@Column(name="LOGIN_SUCCESS")
	private int login_success;
	
	@Column(name="LOGOUT_TIME")
	private Date logout_time;
	
	@Column(name="MESSAGE")
	private String message;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getLogin_time() {
		return login_time;
	}

	public void setLogin_time(Date login_time) {
		this.login_time = login_time;
	}

	public int getLogin_success() {
		return login_success;
	}

	public void setLogin_success(int login_success) {
		this.login_success = login_success;
	}

	public Date getLogout_time() {
		return logout_time;
	}

	public void setLogout_time(Date logout_time) {
		this.logout_time = logout_time;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public EWS_Login_Log() {
		super();
	}

	public EWS_Login_Log(long logid, String user_id, String name, int login_success,
			String message) {
		super();
		this.id=logid;
		this.user_id = user_id;
		this.name = name;
		this.login_success = login_success;
		this.message = message;
	}


	
	
}
