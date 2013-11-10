/**
 * 
 */

$(document).ready(function() {
	$('#posts_sorting_combobox').change(function() {
		comboboxCahnge();
	});
	
	$('#posts_count_combobox').change(function() {
		comboboxCahnge();
	});
	
});

function comboboxCahnge(){
	var categoryIdsParam = "";
	var catIds = getComboCategroyIds();
	if(catIds != null && catIds != ""){
		categoryIdsParam = "&categoryId=" + catIds;
	}
	
	var countParam = "";
	var countVal = $("#posts_count_combobox").val();
	if(countVal != null && countVal != ""){
		countParam="&count=" + countVal;
	}
	
	var orderByParam = "";
	var orderBy = $('#posts_sorting_combobox').val();
	if(orderBy != null && orderBy != ""){
		orderByParam = "/?order_by=" + orderBy;
	}
	
	window.location.replace(orderByParam + categoryIdsParam + countParam);
}

function getComboCategroyIds(){
	var querystring = location.search.replace( '?', '' ).split( '&' );
	
	for ( var i=0; i<querystring.length; i++ ) {

	      var name = querystring[i].split('=')[0];
	      var value = querystring[i].split('=')[1];

	      if(name == 'categoryId'){
	    	  return value;
	      }
	}
	
	return null;
}