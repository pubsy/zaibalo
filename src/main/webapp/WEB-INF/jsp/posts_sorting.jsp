	<c:set var="pre_order_url_base" value="/" />
	<c:set var="post_order_url_base" value="" />

	<c:if test="${not empty param}">
		<c:if test="${param.categoryId ne null}">
			<c:set var="pre_order_url_base" value="${pre_order_url_base}category/${param.categoryId}/" />
		</c:if>
		<c:if test="${param.count ne null}">
			<c:set var="post_order_url_base" value="${post_order_url_base}/count/${param.count}" />
		</c:if>
		<c:if test="${param.page ne null}">
			<c:set var="post_order_url_base" value="${post_order_url_base}/page/${param.page}" />
		</c:if>
	</c:if>
	<c:if test="${empty param}">
		<c:if test="${param_categories ne null}">
			<c:set var="pre_order_url_base" value="${pre_order_url_base}category/${param_categories}/" />
		</c:if>
		<c:if test="${param_count ne null}">
			<c:set var="post_order_url_base" value="${post_order_url_base}/count/${param_count}" />
		</c:if>
		<c:if test="${param_page ne null}">
			<c:set var="post_order_url_base" value="${post_order_url_base}/page/${param_page}" />
		</c:if>
	</c:if>

	<c:set var="pre_count_url_base" value="/" />
	<c:set var="post_count_url_base" value="" />

	<c:if test="${not empty param}">
		<c:if test="${param.categoryId ne null}">
			<c:set var="pre_count_url_base" value="${pre_count_url_base}category/${param.categoryId}/" />
		</c:if>
		<c:if test="${param.order_by ne null}">
			<c:set var="pre_count_url_base" value="${pre_count_url_base}order/${param.order_by}/" />
		</c:if>
		<c:if test="${param.page ne null}">
			<c:set var="post_count_url_base" value="${post_count_url_base}/page/${param.page}" />
		</c:if>
	</c:if>
	<c:if test="${empty param}">
		<c:if test="${param_categories ne null}">
			<c:set var="pre_count_url_base" value="${pre_count_url_base}category/${param_categories}/" />
		</c:if>
		<c:if test="${param_order_by ne null}">
			<c:set var="pre_count_url_base" value="${pre_count_url_base}order/${param_order_by}/" />
		</c:if>
		<c:if test="${param_page ne null}">
			<c:set var="post_count_url_base" value="${post_count_url_base}/page/${param_page}" />
		</c:if>
	</c:if>


<div class="posts_sorting">
	<div class="posts_order_block">
		<span class="hide-on-mobile"><zmt:message key="sort_by_colon"/></span>
		<select id="posts_sorting_combobox" onchange="goToPath('${pre_order_url_base}order/' + this.value + '${post_order_url_base}')">
			<option value="latest" <c:if test="${param.order_by eq 'latest' or param_order_by eq 'latest'}">selected</c:if>><zmt:message key="last_added"/></option>
			<option value="week" <c:if test="${param.order_by eq 'week' or param_order_by eq 'week'}">selected</c:if>><zmt:message key="the_best_in_a_week"/></option>
			<option value="month" <c:if test="${param.order_by eq 'month' or param_order_by eq 'month'}">selected</c:if>><zmt:message key="the_best_in_a_month"/></option>
			<option value="half_a_year" <c:if test="${param.order_by eq 'half_a_year' or param_order_by eq 'half_a_year'}">selected</c:if>><zmt:message key="the_best_in_half_a_year"/></option>
			<option value="year" <c:if test="${param.order_by eq 'year' or param_order_by eq 'year'}">selected</c:if>><zmt:message key="the_best_in_a_year"/></option>
			<option value="all_times" <c:if test="${param.order_by eq 'all_times' or param_order_by eq 'all_times'}">selected</c:if>><zmt:message key="the_best_of_all"/></option>
		</select>
	</div>
	<div class="show_count_block">
		<span class="hide-on-mobile"><zmt:message key="show_count_colon"/></span>
			<select id="posts_count_combobox" onchange="goToPath('${pre_count_url_base}count/' + this.value + '${post_count_url_base}')">
				<option value="10" <c:if test="${param.count eq 10 or param_count eq 10}">selected</c:if>>10</option>
				<option value="25" <c:if test="${param.count eq 25 or param_count eq 25}">selected</c:if>>25</option>
				<option value="50" <c:if test="${param.count eq 50 or param_count eq 50}">selected</c:if>>50</option>
				<option value="100" <c:if test="${param.count eq 100 or param_count eq 100}">selected</c:if>>100</option>
			</select>
	</div>
	<div style="clear:both;"></div>
</div>