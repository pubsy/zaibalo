
function endsWith(str, suffix) {
    return str.indexOf(suffix, str.length - suffix.length) !== -1;
}

function size(textarea, event){	

	$(".comment_width_div").remove();
	var pre = $('<pre></pre>').html(textarea.value);
	var div = $('<div></div>').css("width", $(textarea).width() + "px").css("opacity","0").addClass("comment_width_div");
	div.append(pre);
	$(document.body).append(div);
	
	$(textarea).height(pre.height() + 14.1);
	
}