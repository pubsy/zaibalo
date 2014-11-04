
        <base href="${url_base}">

		<title>${pageTitle}</title>
		
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
		
		<link rel="stylesheet" type="text/css"  href="css/combobox.css?8"/>
		
		<script type="text/javascript" src="scripts/jquery.js"></script>
		<script type="text/javascript" src="scripts/mustache.js"></script>
		<script type="text/javascript" src="scripts/login.js?8"></script>
		<script type="text/javascript" src="scripts/scripts.js?14"></script>
		<script type="text/javascript" src="scripts/ajax.js?10"></script>
		<script type="text/javascript" src="scripts/textarea.js?8"></script>
		<script type="text/javascript" src="scripts/editpost.js?11"></script>
		<script type="text/javascript" src="scripts/edit_comment.js?3"></script>
		<script type="text/javascript" src="scripts/combobox.js?8"></script>
		<script type="text/javascript" src="scripts/message.js?8"></script>
		<script type="text/javascript" src="scripts/category_checkoxes.js?9"></script>
		<script type="text/javascript" src="scripts/posts_sorting.js?9"></script>
		<script type="text/javascript" src="scripts/register.js?2"></script>
		<script type="text/javascript" src="scripts/remind_password.js?1"></script>
		<script type="text/javascript" src="scripts/show_rating.js?2"></script>
		<script type="text/javascript" src="scripts/set_time_zone.js?1"></script>
		
		<link rel="stylesheet" type="text/css" href="css/dropdown/jquery-ui-1.8.13.custom.css">
		<link rel="stylesheet" type="text/css" href="css/dropdown/ui.dropdownchecklist.themeroller.css">
		
		<link rel="stylesheet" href="css/bootstrap.min.css">
		<script src="scripts/bootstrap.min.js"></script>
		
		<script type="text/javascript" src="scripts/dropdown/jquery-ui-1.8.13.custom.min.js"></script>
		<script type="text/javascript" src="scripts/dropdown/ui.dropdownchecklist.js"></script>
		
		<link rel="stylesheet" type="text/css"  href="css/style.css?22"/>
		
		<script type="text/javascript">
		
			$(document).ready(function() {
		
				var emptyText = '<zmt:message key="please_select"/>';
				if ($("#category_select").length > 0) {
					$("#category_select").dropdownchecklist({
						emptyText : emptyText,
						width : 150
					});
				}
		
				$('#edit_post_dialog').on('click', '.edit-cat-del', function() {
					onClickToCatName($(this).parent());
				});
			});
		</script>
		
		<script type="text/javascript">
		
			var ip = '${visitorIP}';
				  
			var _gaq = _gaq || [];
			_gaq.push(['_setAccount', 'UA-11428692-1']);
			_gaq.push(['_setCustomVar', 1,'IP', ip, 2]);
			_gaq.push(['_trackPageview']);
				
			(function() {
				var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
				ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
				var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
			})();
			
		</script>
		
		<script id="comment-template" type="x-tmpl-mustache">
			<div id="comment_{{ id }}" class="comment_style">
				<div class="comment_avatar">
					<img src="{{ author.smallImgPath }}" width="32" alt="{{ author.displayName }}">
				</div>
				<div class="comment_content">
					<div class="comment_author" onclick="location.href='/user/{{ author.id }}';">{{ author.displayName }}</div>
					<div class="comment_operations">
						<a href="javascript:editCommentShow({{ id }});"><span class="glyphicon glyphicon-wrench edit-post-icon"></span></a>
						<a href="javascript:deleteComment({{ id }})"><img src="img/icons/x.png"></a>
					</div>
					<div class="comment_context">{{ content }}</div>
					<div class="comment_rating">
						<img src="img/icons/comment_down.png" class="rating-button" id="commentRatingDown_{{ id }}" onclick="javascript:rateComment({{ id }}, '-1');">
						<span class="rating-text">
							<zmt:message key="rating_colon"/>
							<span id="comment_rating_sum_{{ id }}" class="rating_sum">{{ ratingSum }}</span>
							(<span id="comment_rating_count_{{ id }}" class="rating_text">{{ ratingCount }}</span>)
						</span>
						<img src="img/icons/comment_up.png" class="rating-button" id="commentRatingUp_{{ id }}" onclick="javascript:rateComment({{ id }}, '1');">
					</div>
					<div class="comment_date">
						{{ date }}
					</div>
					<div style="clear:both;"></div>
				</div>
			</div>
		</script>