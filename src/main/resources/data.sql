insert into EWS_USER(id,active) values('187376',1);
insert into EWS_USER(id,role,active,auth) values('4577','ADMIN',1,1);


insert into EWS_UPLOAD_TABLE(excel_template,table_name,query) values('c:/abc.excel','EWS_TEST2','select ''Table EWS_TEST1 contains ''||count(1)||'' Rows'' from EWS_TEST1');

insert into EWS_UPLOAD_TABLE(excel_template,table_name) values('c:/abc.excel','EWS_TEST1');