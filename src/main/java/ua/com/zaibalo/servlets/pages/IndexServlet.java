package ua.com.zaibalo.servlets.pages;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import ua.com.zaibalo.db.api.PostsDAO;
import ua.com.zaibalo.helper.StringHelper;
import ua.com.zaibalo.model.Post;

@Component
@Transactional(propagation=Propagation.REQUIRED)
public class IndexServlet {

	@Autowired
	private PostsDAO postsDAO;

	public ModelAndView posts(String categoryIdsParam, 
			String orderByParam, 
			String countParam, 
			String pageParam) {

		int count = 10;

		if (StringHelper.isNotBlank(countParam)
				&& StringHelper.isDecimal(countParam)) {
			count = Integer.parseInt(countParam);
		}

		Post.PostOrder order = Post.PostOrder.ID;
		if (StringHelper.isNotBlank(orderByParam)
				&& !"latest".equals(orderByParam)) {
			order = Post.PostOrder.RATING_SUM;
		}

		List<Integer> catIdsParam = null;
		if (StringHelper.isNotBlank(categoryIdsParam)
				&& StringHelper.isArrayDecimal(categoryIdsParam, ",")) {
			catIdsParam = new ArrayList<Integer>();
			for (String num : categoryIdsParam.split(",")) {
				catIdsParam.add(Integer.parseInt(num));
			}
		}

		Date fromDate = null;
		if (StringHelper.isNotBlank(orderByParam)) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());

			if ("week".equals(orderByParam)) {
				calendar.add(Calendar.WEEK_OF_YEAR, -1);
				fromDate = calendar.getTime();
			} else if ("month".equals(orderByParam)) {
				calendar.add(Calendar.MONTH, -1);
				fromDate = calendar.getTime();
			} else if ("half_a_year".equals(orderByParam)) {
				calendar.add(Calendar.MONTH, -6);
				fromDate = calendar.getTime();
			} else if ("year".equals(orderByParam)) {
				calendar.add(Calendar.YEAR, -1);
				fromDate = calendar.getTime();
			} else if ("all_times".equals(orderByParam)) {

			}
		}

		int from = -1;
		if (StringHelper.isDecimal(pageParam)) {
			from = (Integer.parseInt(pageParam) - 1) * count;
		}

		List<Post> postList = postsDAO.getPostsList(catIdsParam, fromDate,
				order, from, count);

		long pagingResultsSize = postsDAO
				.getPostsListSize(catIdsParam, fromDate);

		ModelAndView mav = new ModelAndView("index");
		mav.addObject("posts", postList);
		mav.addObject("results_size", pagingResultsSize);
		mav.addObject("param_count", countParam);
		mav.addObject("param_page", pageParam);
		mav.addObject("param_categories", categoryIdsParam);
		mav.addObject("param_order_by", orderByParam);
		
		return mav;
	}

}
