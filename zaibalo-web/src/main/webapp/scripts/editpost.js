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
		draggable: false,
		minHeight: 50,
		buttons: {
			"Зберегти": function() {
				if (validateFields()){
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
	$('#edit_post_tags_input').val("");
	$("#edit_post_category").html("");
	
	title = $("#post_" + id).find(".post_header_title a").text();
	categories = $("#post_" + id).find(".post_category");
	
	var regex = /<br\s*[\/]?>/gi;
	content = $("#post_" + id).find(".post_content_text").html().replace(regex, "\n");
	
	catText = "";
	categories.children('a').each(function(i) {
		catText += "<span class='edit-cat'>" + 
		$(this).text().trim() + 
		"<span class='edit-cat-del'><img src='img/icons/edit_post_del_cat.png' ></span>" +
		"</span>, ";
	});

	newtext = catText.substring(0, catText.length - 2);
	$("#edit_post_category").html(newtext);
	
	$("#edit_post_title").val(title);
	$("#edit_post_content").val(content);
	$("#edit_post_dialog").dialog('option', 'title', 'Змінити пост');
	$("#edit_post_dialog").dialog('open');
	
}

function validateFields(){
	title = $('#edit_post_title').val().trim();
	content = $('#edit_post_content').val().trim();
	categories = $('#edit_post_category').text().trim();
	
	if(title == ''){
		$('#edit_post_title').effect("highlight", {}, 3000);
		$("#edit_post_validation").html("Заголовок поста не може бути пустим!");
		return false;
	}
	
	if(content == ''){
		$('#edit_post_content').effect("highlight", {}, 3000);
		$("#edit_post_validation").html("Тіло поста не може бути пустим!");
		return false;
	}
	
	if(categories == ''){
		$('.edit_post_cat_title').effect("highlight", {}, 3000);
		$("#edit_post_validation").html("Ви мусите обрати хоча б одну категорію!");
		return false;
	}
	
	return true;
}

function update_post() {

	$("#edit_post_dialog:button:contains('Save')").attr("disabled","disabled").addClass("ui-state-disabled");


	
	post_title = $("#edit_post_title").val();
	post_text = $("#edit_post_content").val();
	categories = $("#edit_post_category").text();
	postId = $('#edit_post_id').val();

	var s = function editPostSuccess(response) {
		if(obj.status == "success"){
			$("#post_" + postId).html(response.object);
		}else if(obj.status == "fail"){
			showMessageDialog({title: "Ooops...", message: obj.message});
		}
		$("#edit_post_dialog").dialog("close");
	}

	var url = "/secure/action.do";
	var method = "POST";
	var params = {
			post_title	: post_title,
			post_text	: post_text,
			categories	: categories,
			post_id		: $('#edit_post_id').val(),
			action		: 'update_post'
	}
	var dataType = "json";
	
	//$('#loadingScreen').show("slow");
	sendJQueryAjaxRequest(url, method, params, s, dataType);
	
}

function addSelectedCategoryToEditPost(){
	catSelect = $('#edit_category_select');
	value = catSelect.val();
	values = $('#edit_post_category').text().split(",");
	if (!contains(values, value)){
		if($('#edit_post_category').text().trim() != ""){
			$('#edit_post_category').append(", ");
		}
		$('#edit_post_category').append("<span class='edit-cat'>" + value + 
			"<span class='edit-cat-del'><img src='img/icons/edit_post_del_cat.png' ></span>" +		
			"</span>");
	}
}

function addTagToEditPost(){
	array = $('#edit_post_tags_input').val().split(",");
	for (x=0; x < array.length; x++) { 
		value = array[x].trim();
		values = $('#edit_post_category').text().split(",");
		if(!contains(values, value)){
			if($('#edit_post_category').text().trim() != ""){
				$('#edit_post_category').append(", ");
			}
			$('#edit_post_category').append("<span class='edit-cat'>" + value + 
				"<span class='edit-cat-del'><img src='img/icons/edit_post_del_cat.png' ></span>" +	
				"</span>");
		}
	}
	$('#edit_post_tags_input').val('');
}

function contains(a, obj) {
    for (var i = 0; i < a.length; i++) {
        if (a[i].trim() === obj.trim()) {
        	$("#edit_post_category").find("span:contains(" + obj + ")").filter(function() {
        	    return $(this).text() === obj;
        	}).effect("highlight", {}, 3000);
            return true;
        }
    }
    return false;
}

function onClickToCatName(thisEl){
		catName = $(thisEl).text();
		catText = "";
		$('#edit_post_category').children('span').each(function(i) {
			if($(this).text() != catName){
				catText += "<span class='edit-cat'>" + $(this).text().trim() + 
					"<span class='edit-cat-del'><img src='img/icons/edit_post_del_cat.png'></span>" +
					"</span>, ";
			}
		});
		newtext = catText.substring(0, catText.length - 2);
		$('#edit_post_category').html(newtext);
}
