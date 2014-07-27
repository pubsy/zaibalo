function fbShareButtonClick(postLink, postName, postText){					
	//e.preventDefault();
	FB.ui({
		method : 'feed',
		name : 'Заїбало...',
		link : 'http://www.scum.com.ua/zaibalo',
		picture : 'http://www.scum.com.ua/zaibalo/img/userphoto/default/default.jpg',
		caption : postName,
		description : '',
		message : postText
	});
}

function postExtract(text){ 
	var maxLength = 200;
    var counter = text.length; 

    for (counter  ;counter > 0 ;counter -- ) { 
       //newString += theString.substring(counter-1, counter);
    	if(counter < maxLength){
    		if(text.substring(counter-1, counter) == " "){
    			alert(text.substring(0, counter - 1));
    			return;
    			//return text.substring(0, counter - 1);
    		}
    	}
    } 
    //document.write(theString + " reversed is " + newString + "!"); 
}