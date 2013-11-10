/**
 * 
 */

$(document).ready(function() {
	$('.category_checkbox').change(function() {
		var catIds = getCategoryCheckBoxesIds();
		window.location.replace("/category?categoryId=" + catIds);
	});
});

function getCategoryCheckBoxesIds(){
	var catIds = "";
	$('.category_checkbox').each(function() {
		if($(this).is(':checked')){
			catIds += this.value + ",";
		}
	});
	catIds = catIds.slice(0, -1);
	return catIds;
}