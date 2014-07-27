var post_count=10;

function showCommentBlock(postId){
	document.getElementById("hidden_comments_" + postId).style.display = "block";
	document.getElementById("view_all_comments_" + postId).style.display = "none";
}

function toggleComments(postId) {
	   if($("#hidden_comments_" + postId).css('display') == "block"){
		   $("#hidden_comments_" + postId).hide("slow");
	   }else{
		   $("#hidden_comments_" + postId).show("slow");
	   }
}


function toggleCategoryCheckboxes(button){
	catList = document.getElementById('categories_checkboxes');
	if(catList.style.display == "none" || catList.style.display == ""){
		catList.style.display = "block";
	}else{
		catList.style.display = "none";
	}
}


function addComment(postId){
	var textarea = $("#comment_textarea_" + postId);
	if(textarea.val().trim() == ""){
		return;
	}

	var url = "/secure/action.do";
	var method = "POST";
	var params = {
			post_id : postId,
			content : textarea.val(),
			action	: 'save_comment'
	}
	var dataType = "json";
	
	comments = document.getElementById('comments_' + postId);
	comment_count = document.getElementById('comment_count_' + postId);
	comment_count_text = document.getElementById('comment_count_text_' + postId);

	var s = function addCommentSuccess(response) {
		$('#loading_gif_' + + postId).hide("slow");
		
		if(response.status == "success"){
			var newCommentWrap = document.createElement("div");
			newCommentWrap.innerHTML = response.object;
			
			var comment = newCommentWrap.children[0];
			comment.style.display = "none";

			$('#comments_' + postId).append(newCommentWrap);
			$(comment).show("slow");

			if (comment_count != null) {
				var commentCount = parseInt(comment_count.innerHTML) + 1;
				comment_count.innerHTML = commentCount;
			}
			textarea.val("");
			textarea.trigger('keydown');
		}else{
			showMessageDialog({title: "Хай йому грець!", message: response.message});
			return;
		}

	}
		
	$('#loading_gif_' + + postId).show("slow");
	sendJQueryAjaxRequest(url, method, params, s, dataType);
}

function add_post() {
	//content = document.getElementById('new_posts');

	post_title = document.getElementById('post_title');
	post_text = document.getElementById('post_text');
	post_tags = document.getElementById('tags_input');
	category_select = document.getElementById('category_select');
	categories = "";
	for ( var i = 0; i < category_select.options.length; i++) {
		if (category_select.options[i].selected) {
			categories += category_select.options[i].value + ",";
		}
	}

	//categories_cb_block = document.getElementById('categories_checkboxes');

	var s = function addPostSuccess(response) {
		if(response.status == "fail"){
			$('#loadingScreen').hide("slow");
			showMessageDialog({title: "Хай йому грець!", message: response.message});
			return;
		}
		
		var newPost = document.createElement("div");
		newPost.innerHTML = response.object;
		var ppp = newPost.children[0];

		$('#posts').prepend(ppp);
		$(ppp).css("display", "none");
		$(ppp).show("slow");

		post_title.value = "";
		post_text.value = "";
		post_tags.value = "";
		$('#loadingScreen').hide("slow");
		$(post_text).trigger('keydown');
	}

	var url = "/secure/action.do";
	var method = "POST";
	var params = {
			post_title	: post_title.value,
			post_text	: post_text.value,
			categories	: categories + post_tags.value,
			action		: 'save_post'
	}
	var dataType = "json";
	
	$('#loadingScreen').show("slow");
	sendJQueryAjaxRequest(url, method, params, s, dataType);
	
}

function showAddCommentField(id){
	$("#add_comment_text_" + id).hide();
	$("#add_comment_" + id).show("slow");
}

function removePost(id){
	var s = function postRemovedSuccess(obj){
		   if(obj.status == "success"){
			   $("#post_" + id).hide("slow");
			}else if(obj.status == "fail"){
				showMessageDialog({title: "Хай йому грець!", message: obj.message});
				//alert(obj.message);
			}
	}
	var url = "/secure/action.do";
	var method = "POST";
	var params = {
			postId: id,
			action: 'delete_post'
		}
	var dataType = "json";
	
	sendJQueryAjaxRequest(url, method, params, s, dataType);
}

function deleteComment(id){
	var s = function commentRemovedSuccess(obj){
		   if(obj.status == "success"){
			   $("#comment_" + id).hide("slow");
			}else if(obj.status == "fail"){
				showMessageDialog({title: "Хай йому грець!", message: obj.message});
				//alert(obj.message);
			}
	}
	var url = "/secure/action.do";
	var method = "POST";
	var params = {
			commentId: id,
			action: 'delete_comment'
	}
	var dataType = "json";
	
	sendJQueryAjaxRequest(url, method, params, s, dataType);
}

function ratePost(id, how){
	var s = function success(obj){
		   if(obj.status == "success"){
			   
			   var sumObj = document.getElementById("rating_sum_" + id);
			   sumObj.innerHTML = obj.sum;
			   
			   var countObj = document.getElementById("rating_count_" + id);
			   countObj.innerHTML = obj.count;
			   
			}else if(obj.status == "fail"){
				showMessageDialog({title: "Хай йому грець!", message: obj.message});
			}
	}
	var url = "/secure/action.do";
	var method = "POST";
	var params = {
			postId: id,
			how   : how,
			action: 'rate_post'
		}
	
	var dataType = "json";
	
	sendJQueryAjaxRequest(url, method, params, s, dataType);
}

function rateComment(id, how){
	var s = function success(obj){
		   if(obj.status == "success"){

			   var sumObj = document.getElementById("comment_rating_sum_" + id);
			   sumObj.innerHTML = obj.sum;
			   
			   var countObj = document.getElementById("comment_rating_count_" + id);
			   countObj.innerHTML = obj.count;
			   
			}else if(obj.status == "fail"){
				showMessageDialog({title: "Хай йому грець!", message: obj.message});
			}
	}
	var url = "/secure/action.do";
	var method = "POST";
	var params = {
			action: "rate_comment",
			commentId: id,
			how: how
	}
	var dataType = "json";
	
	sendJQueryAjaxRequest(url, method, params, s, dataType);
}

function sendMessage(){
	
	otherUserName = $('#cb_identifier').val();
	
	var s = function sendSuccess(response){
		if(response.status == "success"){
			$('#cb_identifier').remove();
			var inputHTML = '<input type="hidden" id="cb_identifier" value="' + otherUserName + '">';
			$(".content").append($(inputHTML));
			$(".combobox").remove();
			$(".dialog_messaging_with_name").empty().append(otherUserName + "...");
			$(".dialog_messaging_with").show();
			$("#text").val("");
			$("#text").trigger('keydown');
			$("#dialog_messages").html(response.object);
		}else{
			showMessageDialog({title: "Хай йому грець!", message: response.message});
		}
	}
		
	var params = {
		action : 'send_message',
		recipient_name : otherUserName,
		text : $("#text").val()
	}
	var url = "/secure/action.do";
	var method = "POST";
	var dataType = "json";

	sendJQueryAjaxRequest(url, method, params, s, dataType);
}

