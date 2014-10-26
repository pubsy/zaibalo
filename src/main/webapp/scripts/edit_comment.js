$(document).ready(function() {
	$("#edit_comment_dialog").dialog({
		width: 'auto', // overcomes width:'auto' and maxWidth bug
		maxWidth: 600,
		height: 'auto',
		modal: true,
		fluid: true, // new option
		resizable: false,
		autoOpen: false,
		dialogClass: "editComment",
		closeOnEscape: false,
		draggable: false,
		minHeight: 50,
		buttons: {
			"Зберегти": function() {
				if (validateCommentFields()){
					updateComment(); 
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

function editCommentShow(id){
	$('#edit_comment_id').val(id);
	$("#edit_comment_validation").html("");
	
	var regex = /<br\s*[\/]?>/gi;
	content = $("#comment_" + id).find(".comment_context").html().replace(regex, "\n");
	
	$("#edit_comment_content").val(content);
	$("#edit_comment_dialog").dialog('option', 'title', 'Змінити коментар');
	$("#edit_comment_dialog").dialog('open');
	
}

function validateCommentFields(){
	content = $('#edit_comment_content').val().trim();
	
	if(content == ''){
		$('#edit_comment_content').effect("highlight", {}, 3000);
		$("#edit_comment_validation").html("Тіло коментаря не може бути пустим!");
		return false;
	}

	return true;
}

function updateComment() {

	$("#edit_comment_dialog:button:contains('Save')").attr("disabled","disabled").addClass("ui-state-disabled");
	
	var commentText = $("#edit_comment_content").val();
	var commentId = $('#edit_comment_id').val();

	var s = function editCommentSuccess(response) {
		var template = $('#comment-template').html();
		Mustache.parse(template);
		var comment = $(Mustache.render(template, response));
		comment.css("display", "none");
		$('#comment_' + commentId).replaceWith(comment);
		comment.show("slow");
		$("#edit_comment_dialog").dialog("close");
	}

	var url = "/secure/comment/" + commentId;
	var method = "POST";
	var params = {content: commentText}
	var dataType = "json";
	
	sendJQueryAjaxRequest(url, method, params, s, dataType);

}
