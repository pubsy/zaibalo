
<security:check>
	<div id="add_post_div">
        <ul class="nav nav-tabs" id="myTab">
            <li class="active"><a href="#edit"><zmt:message key="text"/></a></li>
            <li><a href="#preview"><zmt:message key="preview"/></a></li>
        </ul>
        <div class="tab-content">
           <div class="tab-pane active" id="edit">
               <div id="add_post_form">
                   <textarea id="post_text" rows="2" placeholder='<zmt:message key="post_text"/>'></textarea>
               </div>
           </div>
           <div class="tab-pane" id="preview">
               <div id="post_preview" class="post_content_text"></div>
           </div>
        </div>
        <div id="add_post_button">
            <input type="button" id="addPostButton" value='<zmt:message key="send"/>' />
            <span id="loadingScreen"><img src="img/icons/loading.gif" /></span>
        </div>
	</div>
</security:check>
<script type="text/javascript">
	$('#post_text').autosize();    

    $('#myTab a').click(function (e) {
     	var text = $('#post_text').val().replace(/(#[A-za-z\u0400-\u04FF\u0400-\u04FF-]+)/ig, ' [$1]($1)');
        var converter = new Markdown.Converter();

        $('#post_preview').html(converter.makeHtml(text));

        e.preventDefault();
        $(this).tab('show');
    });

    $('#addPostButton').click(function(e) {
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

    			$('#post_text').val("").trigger('autosize.resize');
    			$('#post_preview').empty();
    			$('#loadingScreen').hide("slow");
    			$(post_text).trigger('keydown');
    		}

    		var url = "secure/action.do";
    		var method = "POST";
    		var params = {
    				post_text	: $('#post_text').val(),
    				action		: 'save_post'
    		}
    		var dataType = "json";
    		
    		$('#loadingScreen').show("slow");
    		sendJQueryAjaxRequest(url, method, params, s, dataType);

    });
</script>