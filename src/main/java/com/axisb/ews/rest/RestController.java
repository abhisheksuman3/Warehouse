package com.axisb.ews.rest;

import java.awt.PageAttributes.MediaType;
import java.io.BufferedOutputStream;
import java.io.Console;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.axisb.ews.entity.EWS_File_Upload_Log;
import com.axisb.ews.entity.EWS_User;
import com.axisb.ews.jwt.JwtTokenUtil;
import com.axisb.ews.model.GeneralRequest;
import com.axisb.ews.model.GeneralRequestUpload;
import com.axisb.ews.model.GeneralResponse;
import com.axisb.ews.model.GeneralResponseUpload;
import com.axisb.ews.repository.EWS_Activity_Log_Repository;
import com.axisb.ews.services.ApplicationService;
import com.axisb.ews.services.ExcelDBLoader;

@org.springframework.web.bind.annotation.RestController
@RequestMapping(value = "/api")
public class RestController {

	@Autowired
	ApplicationService appService;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private EWS_Activity_Log_Repository ews_Activity_Log_Repository;

	/*************************************************************************
	 * @author Abhishek Suman
	 * @apiNote for downloading files
	 */
	@RequestMapping(path = "/download", method = RequestMethod.GET)
	public Object download(HttpServletRequest request) throws IOException {
		String file_id = request.getParameter("id");

		final String requestTokenHeader = request.getHeader("Authorization").substring(7);
		System.out.println(requestTokenHeader);
		String filepath = appService.getFilePath(file_id, jwtTokenUtil.getUser(requestTokenHeader));

		if (filepath == null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new GeneralResponse(400, "Something is wrong with your request"));

		try {
			File file = new File(filepath);
			InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

			return ResponseEntity.ok()/*
										 * .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\\"+"abc.png")
										 */
					.contentLength(file.length())
					.contentType(org.springframework.http.MediaType.APPLICATION_OCTET_STREAM).body(resource);
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new GeneralResponse(400, "Exception"));
		}
	}

	/*************************************************************************
	 * @author Abhishek Suman
	 * @apiNote for downloading files
	 */
	@RequestMapping(path = "/downloadTemplate", method = RequestMethod.GET)
	public Object downloadTemplate(HttpServletRequest request) throws IOException {
		String file_id = request.getParameter("id");

		final String requestTokenHeader = request.getHeader("Authorization").substring(7);
		System.out.println(requestTokenHeader);
		String filepath = appService.getFilePathTemplate(file_id);

		if (filepath == null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new GeneralResponse(400, "Something is wrong with your request"));

		try {
			File file = new File(filepath);
			InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

			return ResponseEntity.ok()/*
										 * .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\\"+"abc.png")
										 */
					.contentLength(file.length())
					.contentType(org.springframework.http.MediaType.APPLICATION_OCTET_STREAM).body(resource);
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new GeneralResponse(400, "Exception"));
		}
	}

	/**************************************************************************
	 * @author ABHISHEK_SUMAN
	 * @apiNote For test purpose only
	 * 
	 */
	@RequestMapping(value = { "/hello" }, method = RequestMethod.GET)
	public Object testPage(HttpServletRequest request) {

		// String[] result=ExcelDBLoader.executer().split("~");

		/*
		 * if(result.length==1) return ResponseEntity.status(HttpStatus.OK).body(new
		 * GeneralResponseUpload(200, "Data Upload Successful",result[0]));
		 * 
		 * else return ResponseEntity.status(HttpStatus.OK).body(new
		 * GeneralResponseUpload(200, result[1],result[0]));
		 * 
		 */
		return "sad";
	}

	@RequestMapping(value = { "/uploadExcelToDb" }, method = RequestMethod.POST)
	public Object uploadExcelToDB(@RequestBody GeneralRequestUpload generalRequestUpload, HttpServletRequest request) 
	{

		System.out.println(generalRequestUpload);

		final String requestTokenHeader = request.getHeader("Authorization").substring(7);
		System.out.println(requestTokenHeader);

		EWS_File_Upload_Log ews_File_Upload_Log = null;
		try
		{
			EWS_User user=jwtTokenUtil.getUser(requestTokenHeader);
			
			ews_File_Upload_Log=ews_Activity_Log_Repository.save(new EWS_File_Upload_Log(user.getId(), new Date(),
					"FILE_DB_LOAD", "started" , "", 0));
				
		
		String[] result = appService.uploadExcelToDb(generalRequestUpload.getTable_id(),
				generalRequestUpload.getFile_id(), user);

		if (result.length == 1)
		{
			ews_File_Upload_Log.setAd_info("ROW="+result[0]+",Data Upload Successful,END="+new Date());
			ews_Activity_Log_Repository.save(ews_File_Upload_Log);
			return ResponseEntity.status(HttpStatus.OK)
					.body(new GeneralResponseUpload(200, "Data Upload Successful", result[0]));
		}

		else
		{
			ews_File_Upload_Log.setAd_info("END="+new Date());
			ews_File_Upload_Log.setError_info("ROW="+result[0]+","+(result[1].length()>851? result[1].substring(850):result[1]));
			ews_Activity_Log_Repository.save(ews_File_Upload_Log);
			return ResponseEntity.status(HttpStatus.OK).body(new GeneralResponseUpload(200, result[1], result[0]));
		}
		}catch (Exception e) {
			ews_File_Upload_Log.setAd_info("END="+new Date());
			ews_File_Upload_Log.setError_info("ROW="+0+","+(e.getMessage().length()>851? e.getMessage().substring(850):e.getMessage()));
			ews_Activity_Log_Repository.save(ews_File_Upload_Log);
			return ResponseEntity.status(HttpStatus.OK)
					.body(new GeneralResponseUpload(200, "Exception: "+e.getMessage(), "0"));
		}
	}
	
	
	
	
	@RequestMapping(value = { "/executeQuery" }, method = RequestMethod.GET)
	public Object executeQuery(HttpServletRequest request) {

		final String requestTokenHeader = request.getHeader("Authorization").substring(7);
		System.out.println(requestTokenHeader);
		
		
		String response=appService.executeQuery(request.getParameter("id"));
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	
	

	@RequestMapping(value = { "/getUserDetail" }, method = RequestMethod.GET)
	public Object getDetail(HttpServletRequest request) {

		final String requestTokenHeader = request.getHeader("Authorization").substring(7);
		System.out.println(requestTokenHeader);
		
		return ResponseEntity.status(HttpStatus.OK).body(jwtTokenUtil.getUser(requestTokenHeader));
	}

	/****************************************************************************
	 * @author ABHISHEK_SUMAN
	 * @apiNote Use to upload multiple files in server upload path is in
	 *          application.properties file
	 * 
	 */
	@RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
	public Object uploadFiles(@RequestParam("files") MultipartFile[] files, HttpServletRequest request) {
		System.out.println(files + " " + files.length);

		final String requestTokenHeader = request.getHeader("Authorization").substring(7);
		System.out.println(requestTokenHeader);
		return appService.fileUploadService(files, jwtTokenUtil.getUser(requestTokenHeader));

	}

	/**************************************************************************
	 * @author ABHISHEK SUMAN
	 * @apiNote Use to delete files
	 * 
	 * @param generalRequest example:{ "key":"upload_id", "value":"2" }
	 */
	@RequestMapping(value = "/fileDelete", method = RequestMethod.POST)
	public Object fileDelete(@RequestBody GeneralRequest generalRequest, HttpServletRequest request) {
		System.out.println(generalRequest);
		final String requestTokenHeader = request.getHeader("Authorization").substring(7);
		return appService.fileDeleteService(generalRequest, jwtTokenUtil.getUser(requestTokenHeader));

	}

	/**************************************************************************
	 * @author ABHISHEK SUMAN
	 * @apiNote Use to get file list
	 */
	@RequestMapping(value = "/fileList", method = RequestMethod.GET)
	public Object getFileList(HttpServletRequest request) {

		final String requestTokenHeader = request.getHeader("Authorization").substring(7);
		return appService.displayFileListOfUser(jwtTokenUtil.getUser(requestTokenHeader));

	}

	/**************************************************************************
	 * @author ABHISHEK_SUMAN
	 * @apiNote For logout
	 * 
	 */
	@RequestMapping(value = { "/logout" }, method = RequestMethod.GET)
	public Object logout(HttpServletRequest request) {
		final String requestTokenHeader = request.getHeader("Authorization").substring(7);
		if (appService.logLogout(jwtTokenUtil.getLoginLogId(requestTokenHeader)))

			return ResponseEntity.status(HttpStatus.OK).body(new GeneralResponse(200, "Successfully logout"));

		else
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new GeneralResponse(400, "Fail to logout"));

	}

	/**************************************************************************
	 * @author ABHISHEK SUMAN
	 * @apiNote to add new user
	 */
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public Object addNewUser(@RequestBody EWS_User userRequest, HttpServletRequest request) {

		final String requestTokenHeader = request.getHeader("Authorization").substring(7);

		return appService.addUser(jwtTokenUtil.getUser(requestTokenHeader), userRequest);

	}

	/**************************************************************************
	 * @author ABHISHEK SUMAN
	 * @apiNote to delete user
	 */
	@RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
	public Object deleteUser(@RequestBody EWS_User userRequest, HttpServletRequest request) {

		final String requestTokenHeader = request.getHeader("Authorization").substring(7);

		return appService.deleteUser(jwtTokenUtil.getUser(requestTokenHeader), userRequest);

	}

	/**************************************************************************
	 * @author ABHISHEK SUMAN
	 * @apiNote to alter role
	 */
	@RequestMapping(value = "/alterUserRole", method = RequestMethod.POST)
	public Object alterUserRole(@RequestBody EWS_User userRequest, HttpServletRequest request) {

		final String requestTokenHeader = request.getHeader("Authorization").substring(7);

		return appService.updateUserRole(jwtTokenUtil.getUser(requestTokenHeader), userRequest);

	}

	/**************************************************************************
	 * @author ABHISHEK SUMAN
	 * @apiNote to auth user
	 */
	@RequestMapping(value = "/authUser", method = RequestMethod.POST)
	public Object authUser(@RequestBody EWS_User userRequest, HttpServletRequest request) {

		final String requestTokenHeader = request.getHeader("Authorization").substring(7);

		return appService.authUser(jwtTokenUtil.getUser(requestTokenHeader), userRequest);

	}

	/**************************************************************************
	 * @author ABHISHEK SUMAN
	 * @apiNote to alter Active
	 */
	@RequestMapping(value = "/alterUserActive", method = RequestMethod.POST)
	public Object alterUserActive(@RequestBody EWS_User userRequest, HttpServletRequest request) {

		final String requestTokenHeader = request.getHeader("Authorization").substring(7);

		return appService.updateUserActive(jwtTokenUtil.getUser(requestTokenHeader), userRequest);

	}

	/**************************************************************************
	 * @author ABHISHEK SUMAN
	 * @apiNote find all user
	 */
	@RequestMapping(value = "/getUsers", method = RequestMethod.GET)
	public Object findAllUser(HttpServletRequest request) {

		final String requestTokenHeader = request.getHeader("Authorization").substring(7);

		return appService.findAllUser(jwtTokenUtil.getUser(requestTokenHeader));

	}

	/**************************************************************************
	 * @author ABHISHEK SUMAN
	 * @apiNote get all upload tables
	 */
	@RequestMapping(value = "/getUploadTables", method = RequestMethod.GET)
	public Object getUploadTables(HttpServletRequest request) {

		final String requestTokenHeader = request.getHeader("Authorization").substring(7);

		return appService.getUploadTables(jwtTokenUtil.getUser(requestTokenHeader));

	}

	/**************************************************************************
	 * @author ABHISHEK SUMAN
	 * @apiNote find all user
	 */
	@RequestMapping(value = "/getUserForAuthorization", method = RequestMethod.GET)
	public Object getUserForAuthorization(HttpServletRequest request) {

		final String requestTokenHeader = request.getHeader("Authorization").substring(7);

		return appService.findUnauthUser(jwtTokenUtil.getUser(requestTokenHeader));

	}
	
	
	/**************************************************************************
	 * @author ABHISHEK SUMAN
	 * @apiNote get table count
	 */
	@RequestMapping(value = "/getTableCount", method = RequestMethod.GET)
	public Object getTableCount(HttpServletRequest request) {
		String table_name=request.getParameter("table").toUpperCase();
		final String requestTokenHeader = request.getHeader("Authorization").substring(7);
		String response_t=""+ExcelDBLoader.hashMapStatus.get(jwtTokenUtil.getUser(requestTokenHeader).getId()+table_name);
		
		return ResponseEntity.status(HttpStatus.OK).body(new GeneralResponse(200, response_t));

	}

}
