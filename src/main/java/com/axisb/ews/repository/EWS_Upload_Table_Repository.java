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
import com.axisb.ews.entity.EWS_upload_table;

@Repository
public interface EWS_Upload_Table_Repository extends CrudRepository<EWS_upload_table, Integer>
{
    
    
}
