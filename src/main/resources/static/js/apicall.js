var masterUrl="";
var loginPage="/public/login";
var homePage="/public/home";

var loginUrl = masterUrl+"/api/login";
var logoutUrl=masterUrl+"/api/logout";
var fileList=masterUrl+"/api/fileList";
var deleteUrl=masterUrl+"/api/fileDelete";
var fileUploadUrl=masterUrl+"/api/fileUpload";
var downloadUrl=masterUrl+"/api/download";
var getUploadTables=masterUrl+"/api/getUploadTables";
var downloadTemplateUrl=masterUrl+"/api/downloadTemplate";
var uploadExcelToDb=masterUrl+"/api/uploadExcelToDb";
var getTableCount=masterUrl+"/api/getTableCount";
var getDynamicQO=masterUrl+"/api/executeQuery";


function loading()
{
	document.getElementById("axisbar").classList.add("blink_me");
}
function loadingdone()
{
	document.getElementById("axisbar").classList.remove("blink_me");
}


function uploadFile(x)
{
	x.disabled=true;
	loading();

	
	let filedom=document.getElementById("fileinput");
	let photo = filedom.files;  // file from input
	
	console.log(photo);
	let req = new XMLHttpRequest();
	let formData = new FormData();
	for(var i=0;i<photo.length;i++){
		formData.append("files", photo[i]);
	}
	//req.responseType = 'json';
	req.open("POST", fileUploadUrl);

	req.setRequestHeader("Authorization", 'Bearer '+sessionStorage.token);
	req.send(formData);
	req.onloadend = function() {
		filedom.value =  filedom.defaultValue;
		  let responseObj = JSON.parse(req.response);
		  loadingdone();
		  x.disabled=false;
		  console.log(responseObj);
		  
		  if(req.status==200)
		  {
			  
			  var msg="<b><i>Error while uploading</i></b>";
			  let error=0;
				for(var i=0;i<responseObj.length;i++)
				{
					if(responseObj[i].status!=200)
						{
						error=1;
						msg+=("<br>"+responseObj[i].message);
						}
				}
				if(error==1)
					{
						$('#exampleModal').modal({
						      show: true,
						      closeOnEscape: true
						  });
						  $('#modal-body').html(msg);
					}
			  getFileList();  
		  }
		  else
		  {
			  $('#exampleModal').modal({
			      show: true,
			      closeOnEscape: true
			  });
			  $('#modal-body').html("Something wrong from server or with request.");
		  }
		  
		};
}


function deleteFile(x,id)
{
	x.disabled = true;
	loading();
	
	
	
	var request = {
			key:"upload_id",
			value: id
		};
	console.log(request);
	
	
	
	
let req = new XMLHttpRequest();
	
	//req.responseType = 'json';
	req.open("POST", deleteUrl);

	req.setRequestHeader('Accept', 'application/json, text/plain');
	req.setRequestHeader('Content-Type', 'application/json');
	req.setRequestHeader('Authorization','Bearer '+sessionStorage.token);
	req.send(JSON.stringify(request));
	req.onloadend = function() {
		loadingdone();
		
		let json=JSON.parse(req.response);
		
		
		if(json.status==200)
			{
				console.log(json);
				if(json.status!=200)
				{
					console.log("fail");
					x.disabled = false;
					$('#exampleModal').modal({
				      show: true,
				      closeOnEscape: true
				  });
				  $('#modal-body').html(json.message);
				}
				else
				{
					getFileList();
				}
			}
		else
			{
				console.log("fail");
				x.disabled = false;
				$('#exampleModal').modal({
			      show: true,
			      closeOnEscape: true
			  });
			  $('#modal-body').html("Something is wrong");
			}
		
		
		
	};

}


function getFileList()
{
	// fileList
	var inputUserId = document.getElementById("fileList");
	loading();
	
	/*
	 * fetch(fileList, { method: "GET", headers: { 'Accept': 'application/json,
	 * text/plain', 'Authorization': 'Bearer '+sessionStorage.token }
	 * }).then(res => { loadingdone(); return res.json(); }).then(json=>{
	 * 
	 * 
	 * var table =$( "#summary-table" ).DataTable(); console.log("json");
	 * console.log(json);
	 * 
	 * 
	 * table.clear().draw(); for(let s=0;s<json.length;s++) { var disabled="";
	 * var name=json[s].ad_info; if(json[s].deleted) { disabled="disabled";
	 * name+=" <b style='color: indianred;'>(DELETED)</b>"; } table.row.add( [
	 * name, json[s].user_id, (json[s].action_time.replace("T","
	 * ")).substring(0,(json[s].action_time.replace("T"," ")).indexOf(".")),
	 * json[s].size, "<button class='btn btn-danger' "+disabled+"
	 * onclick='deleteFile(this,"+json[s].id+")'><img src='/image/bin.png'
	 * width='15px' class='center1'></button>"+ "<button class='btn btn-info'
	 * style='margin-left:2px' disabled><img src='/image/edit.png' width='15px'
	 * class='center1'></button>"+ "<button class='btn btn-secondary'
	 * style='margin-left:2px' disabled><img src='/image/settings.png'
	 * width='15px' class='center1'></button>" ] ).order( [ 2, 'desc' ]
	 * ).draw();
	 *  }
	 * 
	 * });
	 */
	
	let req = new XMLHttpRequest();
	
	//req.responseType = 'json';
	req.open("GET", fileList);

	req.setRequestHeader('Authorization','Bearer '+sessionStorage.token);
	req.setRequestHeader('Accept', 'application/json, text/plain');
	req.send();
	req.onloadend = function() {
		loadingdone();
		let json = JSON.parse(req.response);
		loadingdone();
		
		if(req.status==200)
			{
				var table =$( "#summary-table" ).DataTable();
				console.log("json");
				console.log(json);
				
				
				table.clear().draw();
				for(let s=0;s<json.length;s++)
					{
					var disabled="";
					var disabledUpload="";
					
					var name=json[s].ad_info;
					var dwn=("downloadFile(this,").concat(json[s].id,",'",name,"')").replace(/ /g, "_");
					var up=("uploadFileToDb(").concat(json[s].id,",'",name,"')").replace(/ /g, "_");
					if(json[s].deleted)
					{
						disabled="disabled";
						disabledUpload="disabled";
						
					name+=" <b style='color: indianred;'>(DELETED)</b>";	
					}
					
					
					if(json[s].ad_info.trim().substr(json[s].ad_info.trim().length - 5).toLowerCase()!=".xlsx")
						disabledUpload="disabled";
					
					console.log(dwn)
				table.row.add( [
			        name,
			        json[s].user_id,
			        (json[s].action_time.replace("T"," ")).substring(0,(json[s].action_time.replace("T"," ")).indexOf(".")),
			        json[s].size,
			        
			        "<button id='del' class='btn btn-danger' "+disabled+" onclick='deleteFile(this,"+json[s].id+")'><img src='/image/bin.png' width='15px' class='center1'></button>"+
		            "<!--<button class='btn btn-info' style='margin-left:2px' disabled><img src='/image/edit.png' width='15px' class='center1'></button>-->"+
		            "<button onclick="+up+" class='btn btn-secondary' "+disabledUpload+" style='margin-left:2px'><img src='/image/up-arrow.png' width='15px' class='center1'></button>"+
		            "<button class='btn btn-success' "+disabled+" style='margin-left:2px' onclick="+dwn+"><img src='/image/down-arrow.png' width='15px' class='center1'></button>"
			    ] ).order( [ 2, 'desc' ] ).draw();
				
				}
			}
		else
			{
			console.log("fail");
			$('#exampleModal').modal({
		      show: true,
		      closeOnEscape: true
		  });
		  $('#modal-body').html("Fail to load List");
			
			}
	};
	
}

function logout()
{
	
	loading();
	
	/*
	 * fetch(logoutUrl, { method: "GET", headers: { 'Accept': 'application/json,
	 * text/plain', 'Authorization': 'Bearer '+sessionStorage.token }
	 * }).then(res => { loadingdone(); sessionStorage.removeItem('token');
	 * window.location=loginPage; });
	 */
	
	let req=new XMLHttpRequest();
	//req.responseType = 'json';
	req.open("GET", logoutUrl);

	req.setRequestHeader('Authorization', 'Bearer '+sessionStorage.token);
	req.setRequestHeader('Accept', 'application/json, text/plain');
	req.send();
	req.onloadend = function() {
			loadingdone();
		  let responseObj = req.response;
		  sessionStorage.removeItem('token');
		window.location=loginPage;
	};
}


function login() {
	// inputUserId
	// inputPassword
	// inputRemeber
	var inputUserId = document.getElementById("inputUserId");
	var inputPassword = document.getElementById("inputPassword");
	var submitButton=document.getElementById("submit-button");

	var request = {
		username: inputUserId.value,
		password: inputPassword.value
	}
	submitButton.disabled = true;
	

	try
	{
	let req = new XMLHttpRequest();
	//req.responseType = 'json';
	req.open("POST", loginUrl);
	req.setRequestHeader('Content-Type', 'application/json');
	req.setRequestHeader('Accept', 'application/json, text/plain');
	req.send(JSON.stringify(request));
	req.onloadend = function() {
		
		
		  let responseObj = JSON.parse(req.response);
		  console.log(responseObj);
		  submitButton.disabled = false;
		  
		  inputUserId.value = "";
		  inputPassword.value = "";
		  
		  if(req.status==200)
			{
			  	sessionStorage.token=responseObj.token;
				sessionStorage.name=responseObj.name;
				sessionStorage.role=responseObj.role;
				window.location=homePage;
			 }
		  else
			  {
			  console.log("fail");
				$('#exampleModal').modal({
			      show: true,
			      closeOnEscape: true
			  });
			  $('#modal-body').html(responseObj.message);
			  }
		  
		};
	}catch(e)
	{
		console.log("error");
		console.log(e);
	}
}	



function uploadFileToDb(fileId,fileName)
{
	getSetUploadTable();
	$('#exampleModal1').modal({
	      show: true,
	      backdrop: 'static',
	      keyboard: false
	  });
	  $('#modal-title1').html("Upload file to DB<i style=' font-size: x-small;'> | "+fileName+"</i>");
	  //$('#modal-body1').html("hello");
	  document.getElementById("uploadToDb").onclick=function(){uploadToDb(fileId,fileName);};
}	  

function downloadFile(x,id,name)
{
	loading();
	$.ajax({
		  type: "GET",
		  url: downloadUrl+"?id="+id,
		  headers: {
		    'Authorization': 'Bearer '+sessionStorage.token
		  },
		  xhrFields: {
		   responseType: 'blob'
			  
		  },
		  success: function (blob) {
			if(window.navigator.msSaveOrOpenBlob)
				{
				var newBlob = new Blob([blob], {type: 'text/html'})
				  console.log(newBlob);
				  window.navigator.msSaveOrOpenBlob(newBlob,name)
				}
			else
				{
		    var windowUrl = window.URL || window.webkitURL;
		    var url = windowUrl.createObjectURL(blob);
		    var anchor = document.createElement('a');
		    anchor.href = url;
		    anchor.download = name;
		    anchor.click();
		    loadingdone();
		    anchor.parentNode.removeChild(anchor);
		    windowUrl.revokeObjectURL(url);
				}
		  },
		  error: function (error) {
			loadingdone();
			$('#exampleModal').modal({
			      show: true,
			      closeOnEscape: true
			  });
			  $('#modal-body').html("Something is wrong");
		  }
		});
	

}


function downloadTemplateFile(id,name)
{
	name=name+".xlsx";
	loading();
	$.ajax({
		  type: "GET",
		  url: downloadTemplateUrl+"?id="+id,
		  headers: {
		    'Authorization': 'Bearer '+sessionStorage.token
		  },
		  xhrFields: {
		   responseType: 'blob'
			  
		  },
		  success: function (blob) {
			if(window.navigator.msSaveOrOpenBlob)
				{
				var newBlob = new Blob([blob], {type: 'text/html'})
				  console.log(newBlob);
				  window.navigator.msSaveOrOpenBlob(newBlob,name)
				}
			else
				{
		    var windowUrl = window.URL || window.webkitURL;
		    var url = windowUrl.createObjectURL(blob);
		    var anchor = document.createElement('a');
		    anchor.href = url;
		    anchor.download = name;
		    anchor.click();
		    loadingdone();
		    anchor.parentNode.removeChild(anchor);
		    windowUrl.revokeObjectURL(url);
				}
		  },
		  error: function (error) {
			loadingdone();
			$('#exampleModal').modal({
			      show: true,
			      closeOnEscape: true
			  });
			  $('#modal-body').html("Something is wrong");
			  
			  $('#exampleModal1').modal('hide');
		  }
		});
	

}


function getSetUploadTable() {
	

	try
	{
	let req = new XMLHttpRequest();
	//req.responseType = 'json';
	req.open("GET", getUploadTables);
	req.setRequestHeader('Content-Type', 'application/json');
	req.setRequestHeader('Accept', 'application/json, text/plain');
	req.setRequestHeader("Authorization", 'Bearer '+sessionStorage.token);
	req.send();
	req.onloadend = function() {
		
		
		  let responseObj = JSON.parse(req.response);
		  
		  if(req.status==200)
			{
			  //console.log("success");
			  let dd=document.getElementById("dropdown1");
			  
			  let innerd="";
			  for(let x=0;x<responseObj.length;x++)
				  {
				  	innerd+=("<a class='dropdown-item' href='#' onclick=selectTable(this,'"+responseObj[x].id+"','"+responseObj[x].table_name+"')>"+responseObj[x].table_name+"</a>");
				  	
				  }
			  dd.innerHTML=innerd;
			  
			  
			 }
		  else
			  {
			  document.getElemntById("modal-title").innerHTML="Error";
			  //console.log("fail");
				$('#exampleModal').modal({
			      show: true,
			      closeOnEscape: true
			  });
			  $('#modal-body').html(responseObj.message);
			  }
		  
		};
	}catch(e)
	{
		//console.log("error");
		//console.log(e);
	}
}

function uploadToDb(fileId,fileName)
{
	
	let tableId=document.getElementById("dropdownTable_hidden").value;
	console.log(fileId+" "+tableId);
	
	document.getElementById("closeTemp").style.display = "none";
	document.getElementById("uploadToDbClose").style.display = "none";
	document.getElementById("uploadToDb").style.display = "none";
	document.getElementById("uploading_wait").style.display = "block";
	document.getElementById("uploading_wait_font").style.display = "block";
	
	
	
	loading();
	
	var request = {
			table_id:tableId,
			file_id: fileId
		};
	console.log(request);
	
	let req = new XMLHttpRequest();
	
	req.open("POST", uploadExcelToDb);

	req.setRequestHeader('Accept', 'application/json, text/plain');
	req.setRequestHeader('Content-Type', 'application/json');
	req.setRequestHeader('Authorization','Bearer '+sessionStorage.token);
	req.send(JSON.stringify(request));
	
	var myVar = window.setInterval(function(){myTimer(document.getElementById("dropdownTable").innerHTML.trim());}, 1000);
	
	
	req.onloadend = function() {
		loadingdone();
		clearInterval(myVar);
		document.getElementById("uploading_wait_font").innerHTML="Please Wait..";
		document.getElementById("closeTemp").style.display = "block";
		document.getElementById("uploadToDbClose").style.display = "block";
		document.getElementById("uploadToDb").style.display = "block";
		document.getElementById("uploading_wait").style.display = "none";
		document.getElementById("uploading_wait_font").style.display = "none";
		
		
		
		
		let json=JSON.parse(req.response);
		
		
		if(json.status==200)
			{
			getDynamicQueryOutput(tableId);
			$('#exampleModal1').modal('hide');
					
					$('#exampleModal').modal({
				      show: true,
				      closeOnEscape: true
				  });
				  $('#modal-body').html(json.message);
				  $('#modal-title').html("Number of row inserted: "+json.no_row);
				
			}
		else
			{
				
				console.log("fail");
				x.disabled = false;
				$('#exampleModal').modal({
			      show: true,
			      closeOnEscape: true
			  });
			  $('#modal-body').html("Something is wrong");
			  $('#exampleModal1').modal('hide');
			}	
	};

}


function myTimer(table) {
    
	try
	{
	let req = new XMLHttpRequest();
	//req.responseType = 'json';
	req.open("GET", getTableCount+"?table="+table);
	req.setRequestHeader('Content-Type', 'application/json');
	req.setRequestHeader('Accept', 'application/json, text/plain');
	req.setRequestHeader("Authorization", 'Bearer '+sessionStorage.token);
	req.send();
	req.onloadend = function() {
		
		
		  let responseObj = JSON.parse(req.response);
		  
		  if(req.status==200)
			{
			  if(responseObj.message=="null")
				  document.getElementById("uploading_wait_font").innerHTML="Please Wait..";
			  else
				  document.getElementById("uploading_wait_font").innerHTML="Loading data, "+responseObj.message+" rows is in process...";
			
			}
		  
		};
	}catch(e)
	{
	}
    
}



function getDynamicQueryOutput(id) {
	

	try
	{
	let req = new XMLHttpRequest();
	//req.responseType = 'json';
	req.open("GET", getDynamicQO+"?id="+id);
	req.setRequestHeader('Content-Type', 'application/json');
	req.setRequestHeader('Accept', 'application/json, text/plain');
	req.setRequestHeader("Authorization", 'Bearer '+sessionStorage.token);
	document.getElementById("dropdownTable").disabled = true;
	req.send();
	req.onloadend = function() {
		
		document.getElementById("dropdownTable").disabled = false;
		document.getElementById("table_message").innerHTML="Loading Hints.....";
		  
		  if(req.status==200)
			{
			  console.log(req.response);
			  document.getElementById("table_message").innerHTML=req.response;
			  
			 }
		  else
			  {
			  let responseObj = JSON.parse(req.response);
			  document.getElemntById("modal-title").innerHTML="Error";
			  //console.log("fail");
				$('#exampleModal').modal({
			      show: true,
			      closeOnEscape: true
			  });
			  $('#modal-body').html(responseObj.message);
			  }
		  
		};
	}catch(e)
	{
		//console.log("error");
		//console.log(e);
	}
}
