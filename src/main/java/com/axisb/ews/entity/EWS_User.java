package com.axisb.ews.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "EWS_USER")
public class EWS_User {
	@Id
	private String id;
	
	@Column(name="ROLE",columnDefinition = "varchar(100) default 'TEAM'")
	private String role;
	
	@Column(name="ACTIVE",columnDefinition = "number(1) default 0")
	private int active;
	
	@Column(name="FORCE_ACTIVE",columnDefinition = "number(1) default 0")
	private int force_active;
	
	@Column(name="AUTH",columnDefinition = "number(1) default 0")
	private int auth;


	public int getAuth() {
		return auth;
	}



	public void setAuth(int auth) {
		this.auth = auth;
	}



	public EWS_User() {
		super();
	}

	

	@Override
	public String toString() {
		return "User [id=" + id + ", role=" + role + ", active=" + active + ", force_active=" + force_active + "]";
	}



	public EWS_User(String id, String role, int active, int force_active) {
		super();
		this.id = id;
		this.role = role;
		this.active = active;
		this.force_active = force_active;
	}



	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public int getForce_active() {
		return force_active;
	}

	public void setForce_active(int force_active) {
		this.force_active = force_active;
	}
	
	
}
