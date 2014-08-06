$(document).ready(function() {
	$("#forgot_password").dialog({
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
			"Відправити": function() {
				sendForgotPasswordRequest();
			},
			"Закрити": function() { 
				$(this).dialog("close"); 
			}
		}
	});
});

function remindPassDialogShow(){
	$("#userName").val("");
	$("#forgot-pass-dialog-validation").html("");

	$("#forgot_password").dialog('option', 'title', 'Нагадати пароль');
	$("#forgot_password").dialog('open');
}

function sendForgotPasswordRequest(){
	   var s = function success(obj){
		   if(obj.status == "success"){
			    $("#forgot_password").dialog('close');
				showMessageDialog({title: "Дякую", message: obj.message});
	   		}else if(obj.status == "fail"){
	   			$("#forgot-pass-dialog-validation").html(obj.message);
	   		}
	   }
	   
	   var params = {
			   	userName 	: $("#userName").val(),
			   	action		: 'remind_password'
	   		}
	   var url = "/action.do";
	   var method = "POST";	
	   var dataType = "json";

	   sendJQueryAjaxRequest(url, method, params, s, dataType);
}
