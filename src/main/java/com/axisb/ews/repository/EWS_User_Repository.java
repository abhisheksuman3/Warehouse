package com.axisb.ews.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.axisb.ews.entity.EWS_File_Upload_Log;
import com.axisb.ews.entity.EWS_User;

@Repository
public interface EWS_User_Repository extends CrudRepository<EWS_User, String>
{
	@Query("SELECT v FROM EWS_User v WHERE v.id=:Id ")
    List<EWS_User> findByUserId(String Id);
    
    @Query("SELECT v FROM EWS_User v WHERE v.id!=:Id and v.auth=0")
    List<EWS_User> findUnauthUserId(String Id);
    
    
}
