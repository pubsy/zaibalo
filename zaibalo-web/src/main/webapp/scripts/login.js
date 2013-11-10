function login() {
   var s = function sendLoginRequestSuccess(response){

	   if(response.status =="fail"){
		   showMessageDialog({title: "Помилка!", message: response.message});
		   $("password").val("");
		   return;
	   }else if(response.status =="success"){
		   document.location.reload(true);
	   }
   }

   var params = {
		   action: "authenticate", 
		   name: document.getElementById("name").value,
		   password: document.getElementById("password").value,
		   remember: document.getElementById("remember").checked
   }
   var url = "/action.do";
   var method = "POST";	
   var dataType = "json";

   sendJQueryAjaxRequest(url, method, params, s, dataType);
}

function showLoginForm(){
	if(document.getElementById("login_form").style.display == "none"){
		$("#login_form").show("slow");
		//document.getElementById("login_status").style.display = "none";
		document.getElementById("forgot_password").style.display = "none";
		document.getElementById("register").style.display = "none";
	}
}

function showLoginStatus(){
	//if(document.getElementById("login_status").style.display == "none"){
	//	document.getElementById("login_form").style.display = "none";
	//	$("#login_status").show("slow");
	//	document.getElementById("forgot_password").style.display = "none";
	//	document.getElementById("register").style.display = "none";
	//}
}

function showForgotPassword(){
	if(document.getElementById("forgot_password").style.display == "none"){
		document.getElementById("login_form").style.display = "none";
		//document.getElementById("login_status").style.display = "none";
		$("#forgot_password").show("slow");
		document.getElementById("register").style.display = "none";
	}
}

function showRegister(){
	if(document.getElementById("register").style.display == "none"){
		document.getElementById("login_form").style.display = "none";
		//document.getElementById("login_status").style.display = "none";
		document.getElementById("forgot_password").style.display = "none";
		$("#register").show("slow");
	}
}

function getParameterByName(name)
{
  name = name.replace(/[\[]/, "\\\[").replace(/[\]]/, "\\\]");
  var regexS = "[\\?&]" + name + "=([^&#]*)";
  var regex = new RegExp(regexS);
  var results = regex.exec(window.location.search);
  if(results == null)
    return "";
  else
    return decodeURIComponent(results[1].replace(/\+/g, " "));
}

