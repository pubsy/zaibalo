$(document).ready(function() {
	$("#edit_post_dialog").dialog({
		width: 'auto', // overcomes width:'auto' and maxWidth bug
		maxWidth: 600,
		height: 'auto',
		modal: true,
		fluid: true, // new option
		resizable: false,
		autoOpen: false,
		dialogClass: "editPost",
		closeOnEscape: false,
		draggable: true,
		minHeight: 50,
		buttons: {
			"Зберегти": function() {
				if (validatePostFields()){
					update_post(); 
				}		
			},
			"Закрити": function() { 
				$(this).dialog("close"); 
			}
		},
		open: function() {},
		close: function() {}
	});
});

function editPostShow(id){
	$('#edit_post_id').val(id);
	$("#edit_post_validation").html("");
	
	var regex = /<br\s*[\/]?>/gi;
	var content = $("#post_" + id).find(".post_content_text").html().replace(regex, "\n");

	$("#edit_post_content").val(content);
	$("#edit_post_dialog").dialog('option', 'title', 'Змінити пост');
	$("#edit_post_dialog").dialog('open');
}

function validatePostFields(){
	var content = $('#edit_post_content').val().trim();
	
	if(content == ''){
		$('#edit_post_content').effect("highlight", {}, 3000);
		$("#edit_post_validation").html("Тіло поста не може бути пустим!");
		return false;
	}

	return true;
}

function update_post() {

	$("#edit_post_dialog:button:contains('Save')").attr("disabled","disabled").addClass("ui-state-disabled");

	post_text = $("#edit_post_content").val();
	postId = $('#edit_post_id').val();

	var s = function editPostSuccess(response) {
		if(response.status == "success"){
			$("#post_" + postId).html(response.object);
		}else if(response.status == "fail"){
			showMessageDialog({title: "Хай йому грець!", message: response.message});
		}
		$("#edit_post_dialog").dialog("close");
	}

	var url = "secure/action.do";
	var method = "POST";
	var params = {
			post_text	: post_text,
			post_id		: postId,
			action		: 'update_post'
	}
	var dataType = "json";
	
	sendJQueryAjaxRequest(url, method, params, s, dataType);
	
}
