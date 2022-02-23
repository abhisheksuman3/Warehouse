package com.axisb.ews.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.axisb.ews.entity.EWS_File_Upload_Log;
import com.axisb.ews.entity.EWS_User;

@Repository
public interface EWS_Activity_Log_Repository extends CrudRepository<EWS_File_Upload_Log, Long>
{
	@Query("SELECT v FROM EWS_File_Upload_Log v WHERE v.user_id=:id and v.deleted=0 and v.action_type='FILE_UPLOAD'")
    ArrayList<EWS_File_Upload_Log> findByUserId(@Param("id") String Id);
    
    @Query("SELECT v FROM EWS_File_Upload_Log v WHERE v.action_type='FILE_UPLOAD' and error_info is null")
    ArrayList<EWS_File_Upload_Log> findAllFiles();
    
    
    @Query("SELECT v FROM EWS_File_Upload_Log v WHERE v.ad_info=:filename and deleted=0 and ACTION_TYPE='FILE_UPLOAD'" )
    ArrayList<EWS_File_Upload_Log> findLogWithFileName(@Param("filename") String filename);
    
    @Query("SELECT v FROM EWS_File_Upload_Log v WHERE v.user_id=:usrId and v.id=:id and  ACTION_TYPE='FILE_UPLOAD' and deleted=0")
    ArrayList<EWS_File_Upload_Log> findFileById(String usrId,Long id);
    
    @Query("SELECT v FROM EWS_File_Upload_Log v WHERE v.id=:id and ACTION_TYPE='FILE_UPLOAD'")
    ArrayList<EWS_File_Upload_Log> findFileByIdAdmin(Long id);
}
