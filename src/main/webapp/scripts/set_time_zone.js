$(document).ready(function() {
	   var s = function success(response) {}
	   
	   var dateVar = new Date();
	   var timeZone = dateVar.getTimezoneOffset()/60 * (-1);  
	   
	   var params = {
			    timeZone : timeZone,
			   	action	 : 'set_time_zone'
	   		}
	   var url = "action.do";
	   var method = "POST";	
	   var dataType = "json";

	   sendJQueryAjaxRequest(url, method, params, s, dataType);
});