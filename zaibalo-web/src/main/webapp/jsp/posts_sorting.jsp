<div class="posts_sorting">
	<div class="posts_order_block">
		<zmt:message key="sort_by_colon"/>
		<select id="posts_sorting_combobox">
			<option value="latest" <c:if test="${param.order_by eq 'latest'}">selected</c:if>><zmt:message key="last_added"/></option>
			<option value="week" <c:if test="${param.order_by eq 'week'}">selected</c:if>><zmt:message key="the_best_in_a_week"/></option>
			<option value="month" <c:if test="${param.order_by eq 'month'}">selected</c:if>><zmt:message key="the_best_in_a_month"/></option>
			<option value="half_a_year" <c:if test="${param.order_by eq 'half_a_year'}">selected</c:if>><zmt:message key="the_best_in_half_a_year"/></option>
			<option value="year" <c:if test="${param.order_by eq 'year'}">selected</c:if>><zmt:message key="the_best_in_a_year"/></option>
			<option value="all_times" <c:if test="${param.order_by eq 'all_times'}">selected</c:if>><zmt:message key="the_best_of_all"/></option>
		</select>
	</div>
	<div class="show_count_block">
		<zmt:message key="show_count_colon"/>
			<select id="posts_count_combobox">
				<option value="10" <c:if test="${param.count eq 10}">selected</c:if>>10</option>
				<option value="25" <c:if test="${param.count eq 25}">selected</c:if>>25</option>
				<option value="50" <c:if test="${param.count eq 50}">selected</c:if>>50</option>
				<option value="100" <c:if test="${param.count eq 100}">selected</c:if>>100</option>
			</select>
	</div>
	<div style="clear:both;"></div>
</div>