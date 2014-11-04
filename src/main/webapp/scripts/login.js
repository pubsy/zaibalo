
function showLoginForm(){
	if(document.getElementById("login_form").style.display == "none"){
		$("#login_form").show("slow");
		document.getElementById("forgot_password").style.display = "none";
		document.getElementById("register").style.display = "none";
	}
}

function showForgotPassword(){
	if(document.getElementById("forgot_password").style.display == "none"){
		document.getElementById("login_form").style.display = "none";
		$("#forgot_password").show("slow");
		document.getElementById("register").style.display = "none";
	}
}

function showRegister(){
	if(document.getElementById("register").style.display == "none"){
		document.getElementById("login_form").style.display = "none";
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

