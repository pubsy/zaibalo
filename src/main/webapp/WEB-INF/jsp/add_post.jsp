
<security:check>
	<div id="add_post_div">
        <ul class="nav nav-tabs" id="myTab">
            <li class="active"><a href="#edit">Edit</a></li>
            <li><a href="#preview">Priview</a></li>
        </ul>
        <div class="tab-content">
           <div class="tab-pane active" id="edit">
               <div id="add_post_form">
                   <textarea id="post_text" rows="2" placeholder='<zmt:message key="post_text"/>'></textarea>
               </div>
           </div>
           <div class="tab-pane" id="preview">
               <div id="post_preview"></div>
           </div>
        </div>
        <div id="add_post_button">
            <input type="button" id="addPostButton" value='<zmt:message key="send"/>' onclick="add_post();" />
            <span id="loadingScreen"><img src="img/icons/loading.gif" /></span>
        </div>
	</div>
</security:check>
<script type="text/javascript">
    $('#myTab a').click(function (e) {
        e.preventDefault();
        $(this).tab('show');
        $('#post_text').bind('input propertychange', function() {
            var converter = new Showdown.converter();
            $('#post_preview').html(converter.makeHtml(this.value));
        });
    });
</script>