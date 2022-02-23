var getFileUrl="/api/getUsers";
var createUser="/api/addUser";
var deleteUser="/api/deleteUser";
var edUser="/api/alterUserActive";
var roleUser="/api/alterUserRole";
var authUser="/api/authUser";
var getUnauthUser="/api/getUserForAuthorization";

function test() {
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
	req.setRequestHeader("Authorization", 'Bearer '+sessionStorage.token);
	req.send(JSON.stringify(request));
	req.onloadend = function() {
		
		
		  let responseObj = JSON.parse(req.response);
		  //console.log(responseObj);
		  submitButton.disabled = false;
		  
		  inputUserId.value = "";
		  inputPassword.value = "";
		  
		  if(req.status==200)
			{
			  	sessionStorage.token=responseObj.token;
				sessionStorage.name=responseObj.name;
				window.location=homePage;
			 }
		  else
			  {
			  ////console.log("fail");
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




function getSetUsers() {
	

	try
	{
	let req = new XMLHttpRequest();
	//req.responseType = 'json';
	req.open("GET", getFileUrl);
	req.setRequestHeader('Content-Type', 'application/json');
	req.setRequestHeader('Accept', 'application/json, text/plain');
	req.setRequestHeader("Authorization", 'Bearer '+sessionStorage.token);
	req.send();
	req.onloadend = function() {
		
		
		  let responseObj = JSON.parse(req.response);
		  document.getElementById("dropdownMenuButtonUserDel").disabled = true;
		  document.getElementById("dropdownMenuButtonedl").disabled = true;
		  
		  if(req.status==200)
			{
			  //console.log("success");
			  let dd=document.getElementById("dropdown1");
			  let dd2=document.getElementById("dropdown2");
			  let dd3=document.getElementById("dropdown3");
			  
			  let innerd="";
			  let innere="";
			  let innerm="";
			  for(let x=0;x<responseObj.length;x++)
				  {
				  	innerd+=("<a class='dropdown-item' href='#' onclick=selectEmp(this)>"+responseObj[x].id+"</a>");
				  	innere+=("<a class='dropdown-item' href='#' onclick=selectEmpE(this,'"+responseObj[x].active+"')>"+responseObj[x].id+"</a>");
				  	innerm+=("<a class='dropdown-item' href='#' onclick=selectEmpmu(this,'"+responseObj[x].role+"')>"+responseObj[x].id+"</a>");
				  }
			  dd.innerHTML=innerd;
			  dd2.innerHTML=innere;
			  dd3.innerHTML=innerm;
			  document.getElementById("dropdownMenuButtonUserDel").disabled = false;
			  document.getElementById("dropdownMenuButtonedl").disabled = false;
			  document.getElementById("dropdownMenuButtonmu").disabled = false;
			  
			  
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

function getSetUnauthUsers() {
	

	try
	{
	let req = new XMLHttpRequest();
	//req.responseType = 'json';
	req.open("GET", getUnauthUser);
	req.setRequestHeader('Content-Type', 'application/json');
	req.setRequestHeader('Accept', 'application/json, text/plain');
	req.setRequestHeader("Authorization", 'Bearer '+sessionStorage.token);
	req.send();
	req.onloadend = function() {
		
		
		  let responseObj = JSON.parse(req.response);
		  document.getElementById("dropdownMenuButtonedlt").disabled = true;
		  
		  if(req.status==200)
			{
			  //console.log("success");
			  let dd=document.getElementById("dropdown22");
			  
			  let innerd="";
			  for(let x=0;x<responseObj.length;x++)
				  {
				  	innerd+=("<a class='dropdown-item' href='#' onclick=selectEmpAut(this)>"+responseObj[x].id+"</a>");
				  	
				  }
			  dd.innerHTML=innerd;
			  document.getElementById("dropdownMenuButtonedlt").disabled = false;
			  
			  
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



function createUserNew() {
	
	var userid = document.getElementById("empid").value;
	var role = document.getElementById("dropdownMenuButtonUserAdd").innerHTML;
	var active=(document.getElementById("exampleCheck1").checked?1:0);

	var request = {
			id:userid,
		    role:role,
		    active:active,
		    force_active:"0"
	}
	

	try
	{
	let req = new XMLHttpRequest();
	//req.responseType = 'json';
	req.open("POST", createUser);
	req.setRequestHeader('Content-Type', 'application/json');
	req.setRequestHeader('Accept', 'application/json, text/plain');
	req.setRequestHeader("Authorization", 'Bearer '+sessionStorage.token);
	req.send(JSON.stringify(request));
	req.onloadend = function() {
		
		
		  let responseObj = JSON.parse(req.response);
		  //console.log(responseObj);
		  
		  
		  if(req.status==200)
			{
			  document.getElementById("empid").value="";
			  document.getElementById("modal-title").innerHTML="Message";
			  $('#exampleModal').modal({
			      show: true,
			      closeOnEscape: true
			  });
			  $('#modal-body').html(responseObj.message);
			  
			 }
		  else
			  {
			  //console.log("fail");
			  document.getElementById("modal-title").innerHTML="Error";
				$('#exampleModal').modal({
			      show: true,
			      closeOnEscape: true
			  });
			  $('#modal-body').html(responseObj.message);
			  }
		  getSetUsers();
		  getSetUnauthUsers();
		  
		};
	}catch(e)
	{
		//console.log("error");
		//console.log(e);
	}
}

function deleteEmployee() {
	var userid = document.getElementById("dropdownMenuButtonUserDel").innerHTML;

	var request = {
			id:userid
	}
	

	try
	{
	let req = new XMLHttpRequest();
	//req.responseType = 'json';
	req.open("POST", deleteUser);
	req.setRequestHeader('Content-Type', 'application/json');
	req.setRequestHeader('Accept', 'application/json, text/plain');
	req.setRequestHeader("Authorization", 'Bearer '+sessionStorage.token);
	req.send(JSON.stringify(request));
	req.onloadend = function() {
		
		
		  let responseObj = JSON.parse(req.response);
		  //console.log(responseObj);
		  
		  
		  if(req.status==200)
			{
			  document.getElementById("modal-title").innerHTML="Message";
			  document.getElementById("dropdownMenuButtonUserDel").innerHTML="Select Employee";
			  $('#exampleModal').modal({
			      show: true,
			      closeOnEscape: true
			  });
			  $('#modal-body').html(responseObj.message);
			  
			 }
		  else
			  {
			  document.getElementById("modal-title").innerHTML="Error";
			  //console.log("fail");
				$('#exampleModal').modal({
			      show: true,
			      closeOnEscape: true
			  });
			  $('#modal-body').html(responseObj.message);
			  }
		  getSetUsers();
		  getSetUnauthUsers();
		  
		};
	}catch(e)
	{
		//console.log("error");
		//console.log(e);
	}
}


function disableEnableEmployee(x) {
	var userid = document.getElementById("dropdownMenuButtonedl").innerHTML;
	let set=0;
	if(x.innerHTML=="Enable")
		set=1;
	else if(x.innerHTML=="Disable")
		set=0;
	else
	{
		document.getElementById("modal-title").innerHTML="Error";
		  //console.log("fail");
			$('#exampleModal').modal({
		      show: true,
		      closeOnEscape: true
		  });
		  $('#modal-body').html("Select Employee First");
		
	return;
	}
	
	var request = {
			active:set,
			id:userid
	}
	

	try
	{
	let req = new XMLHttpRequest();
	//req.responseType = 'json';
	req.open("POST", edUser);
	req.setRequestHeader('Content-Type', 'application/json');
	req.setRequestHeader('Accept', 'application/json, text/plain');
	req.setRequestHeader("Authorization", 'Bearer '+sessionStorage.token);
	req.send(JSON.stringify(request));
	req.onloadend = function() {
		
		
		  let responseObj = JSON.parse(req.response);
		  //console.log(responseObj);
		  
		  
		  if(req.status==200)
			{
			  document.getElementById("modal-title").innerHTML="Message";
			  document.getElementById("dropdownMenuButtonedl").innerHTML="Select Employee";
			  document.getElementById("dropdownMenuButtonedlButton").innerHTML="Select Emp";
			  $('#exampleModal').modal({
			      show: true,
			      closeOnEscape: true
			  });
			  $('#modal-body').html(responseObj.message);
			  
			 }
		  else
			  {
			  document.getElementById("modal-title").innerHTML="Error";
			  //console.log("fail");
				$('#exampleModal').modal({
			      show: true,
			      closeOnEscape: true
			  });
			  $('#modal-body').html(responseObj.message);
			  }
		  getSetUsers();
		  
		};
	}catch(e)
	{
		//console.log("error");
		//console.log(e);
	}
}

function ApproveEmployee(x)
{
	var userid = document.getElementById("dropdownMenuButtonedlt").innerHTML;
	
	
	var request = {
			id:userid
	}
	

	try
	{
	let req = new XMLHttpRequest();
	//req.responseType = 'json';
	req.open("POST", authUser);
	req.setRequestHeader('Content-Type', 'application/json');
	req.setRequestHeader('Accept', 'application/json, text/plain');
	req.setRequestHeader("Authorization", 'Bearer '+sessionStorage.token);
	req.send(JSON.stringify(request));
	req.onloadend = function() {
		
		
		  let responseObj = JSON.parse(req.response);
		  //console.log(responseObj);
		  
		  
		  if(req.status==200)
			{
			  document.getElementById("modal-title").innerHTML="Message";
			  document.getElementById("dropdownMenuButtonedlt").innerHTML="Select Employee";
			  document.getElementById("dropdownMenuButtonedlButtond").disabled=true;
			  
			  $('#exampleModal').modal({
			      show: true,
			      closeOnEscape: true
			  });
			  $('#modal-body').html(responseObj.message);
			  
			 }
		  else
			  {
			  document.getElementById("modal-title").innerHTML="Error";
			  //console.log("fail");
				$('#exampleModal').modal({
			      show: true,
			      closeOnEscape: true
			  });
			  $('#modal-body').html(responseObj.message);
			  }
		  getSetUsers();
		  getSetUnauthUsers();
		  
		};
	}catch(e)
	{
		//console.log("error");
		//console.log(e);
	}
}
function modifyEmployeeRole() {
	var userid = document.getElementById("dropdownMenuButtonmu").innerHTML;
	var userrole = document.getElementById("dropdownMenuButtonUserAddmua").innerHTML;
	
	
	var request = {
			role:userrole,
			id:userid
	}
	

	try
	{
	let req = new XMLHttpRequest();
	//req.responseType = 'json';
	req.open("POST", roleUser);
	req.setRequestHeader('Content-Type', 'application/json');
	req.setRequestHeader('Accept', 'application/json, text/plain');
	req.setRequestHeader("Authorization", 'Bearer '+sessionStorage.token);
	req.send(JSON.stringify(request));
	req.onloadend = function() {
		
		
		  let responseObj = JSON.parse(req.response);
		  //console.log(responseObj);
		  
		  
		  if(req.status==200)
			{
			  document.getElementById("modal-title").innerHTML="Message";
			  document.getElementById("dropdownMenuButtonmu").innerHTML="Select Employee";
			  document.getElementById("dropdownMenuButtonUserAddmua").innerHTML="Select Role";
			  $('#exampleModal').modal({
			      show: true,
			      closeOnEscape: true
			  });
			  $('#modal-body').html(responseObj.message);
			  
			 }
		  else
			  {
			  document.getElementById("modal-title").innerHTML="Error";
			  //console.log("fail");
				$('#exampleModal').modal({
			      show: true,
			      closeOnEscape: true
			  });
			  $('#modal-body').html(responseObj.message);
			  }
		  getSetUsers();
		  
		};
	}catch(e)
	{
		//console.log("error");
		//console.log(e);
	}
}