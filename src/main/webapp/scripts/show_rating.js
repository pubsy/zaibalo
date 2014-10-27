$(document).ready(function() {
	$('.rating-text').dblclick(function() {
		type_id = $(this).closest(".comment_style").attr("id");
		if(type_id == undefined){
			type_id = $(this).closest(".post").attr("id");
		}
		
		split = type_id.split("_");
		sendShowRatingRequest(split[0], split[1]);
	});
});


$(document).ready(function() {
	$("#show_rating").dialog({
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
		minHeight: 50
	});
});

function showRatingDialog(){
	$("#show_rating").dialog('option', 'title', 'Оцінки користувачів');
	$("#show_rating").dialog('open');
}

function sendShowRatingRequest(type, id){
	   var s = function success(response) {
		   if (response.status == "success") {
			   $("#show_rating").html(response.object);
			   showRatingDialog();
		   } else if (response.status == "fail") {
			   //TODO do something
			   $("#register-dialog-validation").html("FAIL");
		   }
	   }
	   
	   var params = {
			   	type 	: type,
				id		: id,
			   	action	: 'show_rating'
	   		}
	   var url = "action.do";
	   var method = "POST";	
	   var dataType = "json";

	   sendJQueryAjaxRequest(url, method, params, s, dataType);
}
