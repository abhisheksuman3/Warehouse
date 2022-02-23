package com.axisb.ews.repository;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.axisb.ews.entity.EWS_Login_Log;
import com.axisb.ews.entity.EWS_User;

@Repository

public interface EWS_Login_Log_Repository  extends CrudRepository<EWS_Login_Log, String>
{
	@Modifying
    @Query(value ="insert into EWS_LOGIN_LOG (USER_ID,NAME,LOGIN_SUCCESS,MESSAGE) VALUES (:user_id,:name,:login_success,:message)", nativeQuery = true)
    @Transactional
    void logUserLogin(@Param("user_id") String user_id,@Param("name") String name,@Param("login_success") int login_success,@Param("message") String message);
	
	@Modifying
    @Query(value ="update EWS_LOGIN_LOG set LOGOUT_TIME= :logout_time where ID=:id", nativeQuery = true)
    @Transactional
    int logLogout(@Param("id") long user_id,@Param("logout_time") Date logout_time);
	
	
}
