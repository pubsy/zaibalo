
function sendJQueryAjaxRequest(url, type, params, successMethod, dataType) {
	var request = $.ajax({
		url : url,
		type : type,
		data : params,
		dataType : dataType
	});

	request.done(function(msg) {
		successMethod(msg);
	});

	request.fail(function(jqXHR, textStatus) {
		console.log("ERROR: %s", textStatus);
		showMessageDialog({title: jqXHR.status, message: "HTTP error. Status: " + textStatus});
	});
}
