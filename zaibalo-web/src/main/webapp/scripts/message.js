$(document).ready(function() {
	// create the loading window and set autoOpen to false
	$("#message").dialog({
		autoOpen: false,	// set this to false so we can manually open it
		dialogClass: "errorMessage",
		closeOnEscape: false,
		draggable: false,
		width: 460,
		minHeight: 50,
		modal: true,
		buttons: {},
		resizable: false,
		open: function() {
			// scrollbar fix for IE
			//$('body').css('overflow','hidden');
		},
		close: function() {
			// reset overflow
			//$('body').css('overflow','auto');
		}
	}); // end of dialog
});

function showMessageDialog(message) { // I choose to allow my loading screen dialog to be customizable, you don't have to
	$("#message").html(message.message);
	$("#message").dialog('option', 'title', message.title);
	$("#message").dialog('open');
}
function closeMessageDialog() {
	$("#message").dialog('close');
}
