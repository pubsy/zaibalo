
function sendJQueryAjaxRequest(url, type, params, successMethod, dataType, contentType, headers) {
	contentType = typeof contentType !== 'undefined' ? contentType : 'application/x-www-form-urlencoded';
	
	var request = $.ajax({
		url : url,
		type : type,
		data : params,
		dataType : dataType,
		contentType: contentType,
		headers: headers
	});

	request.done(function(msg) {
		successMethod(msg);
	});

	request.fail(function(jqXHR, textStatus) {
		console.log("ERROR: %s", textStatus);
		showMessageDialog({title: jqXHR.status, message: "HTTP error. Status: " + textStatus});
	});

}
