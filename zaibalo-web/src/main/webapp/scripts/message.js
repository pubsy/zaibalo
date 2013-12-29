$(document).ready(function() {
	// create the loading window and set autoOpen to false
	$("#message").dialog({
		width: 'auto', // overcomes width:'auto' and maxWidth bug
		maxWidth: 460,
		minHeight: 50,
		modal: true,
		fluid: true, // new option
		resizable: false,
		autoOpen: false,	// set this to false so we can manually open it
		dialogClass: "errorMessage",
		closeOnEscape: false,
		draggable: false
	}); // end of dialog
});

function showMessageDialog(message) {
	$("#message").html(message.message);
	$("#message").dialog('option', 'title', message.title);
	$("#message").dialog('open');
}

function closeMessageDialog() {
	$("#message").dialog('close');
}
//
////on window resize run function
//$(window).resize(function () {
//    fluidDialog();
//});
//
//// catch dialog if opened within a viewport smaller than the dialog width
//$(document).on("dialogopen", ".ui-dialog", function (event, ui) {
//    fluidDialog();
//});
//
//function fluidDialog() {
//    var $visible = $(".ui-dialog:visible");
//    // each open dialog
//    $visible.each(function () {
//        var $this = $(this);
//        var dialog = $this.find(".ui-dialog-content").data("ui-dialog");
//        // if fluid option == true
//        if (dialog.options.fluid) {
//            var wWidth = $(window).width();
//            // check window width against dialog width
//            if (wWidth < dialog.options.maxWidth + 50) {
//                // keep dialog from filling entire screen
//                $this.css("max-width", "90%");
//            } else {
//                // fix maxWidth bug
//                $this.css("max-width", dialog.options.maxWidth);
//            }
//            //reposition dialog
//            dialog.option("position", dialog.options.position);
//        }
//    });
//
//}
