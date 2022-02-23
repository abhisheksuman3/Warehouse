package com.axisb.ews.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "EWS_UPLOAD_TABLE")
public class EWS_upload_table {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="TABLE_NAME",columnDefinition = "varchar(100)")
	private String table_name;
	
	@Column(name="EXCEL_TEMPLATE",columnDefinition = "varchar(100)")
	private String excel_template;
	
	@Column(name="QUERY",columnDefinition = "varchar(1000)")
	private String query;
	


	public String getQuery() {
		return query;
	}






	public void setQuery(String query) {
		this.query = query;
	}






	@Override
	public String toString() {
		return "EWS_upload_table [id=" + id + ", table_name=" + table_name + ", excel_template=" + excel_template + "]";
	}






	public EWS_upload_table(String table_name, String excel_template) {
		super();
		this.table_name = table_name;
		this.excel_template = excel_template;
	}






	





	public int getId() {
		return id;
	}






	public void setId(int id) {
		this.id = id;
	}






	public String getTable_name() {
		return table_name;
	}






	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}






	public String getExcel_template() {
		return excel_template;
	}






	public void setExcel_template(String excel_template) {
		this.excel_template = excel_template;
	}






	public EWS_upload_table() {
		super();
	}

	

	
}
