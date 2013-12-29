$(document).ready(function() {
	$("#register").dialog({
		width: 'auto', // overcomes width:'auto' and maxWidth bug
		maxWidth: 600,
		height: 'auto',
		modal: true,
		fluid: true, // new option
		resizable: false,
		autoOpen: false,
		dialogClass: "editPost",
		closeOnEscape: false,
		draggable: false,
		minHeight: 50,
		buttons: {
			"Зареєструватися": function() {
				register();
			},
			"Закрити": function() { 
				$(this).dialog("close"); 
			}
		}
	});
});

function registerDialogShow(){
	$("#register_email").val("");
	$("#register_login").val("");
	$("#register-dialog-validation").html("");

	$("#register").dialog('option', 'title', 'Реєстрація');
	$("#register").dialog('open');
}

function register(){
	var s = function success(obj){
		   if(obj.status == "success"){
			   	$("#register").dialog('close');
				showMessageDialog({title: "Thanks", message: obj.message});
	   		}else if(obj.status == "fail"){
	   			$("#register-dialog-validation").html(obj.message);
	   		}
	   }
	   
	   var params = {
			   	email			: $("#register_email").val(),
	   			register_login	: $("#register_login").val(),
	   			action			: 'register'
	   		}
	   var url = "/action.do";
	   var method = "POST";	
	   var dataType = "json";

	   sendJQueryAjaxRequest(url, method, params, s, dataType);
} 
