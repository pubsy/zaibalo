package ua.com.zaibalo.servlets.pages;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.com.zaibalo.db.DataAccessFactory;
import ua.com.zaibalo.helper.StringHelper;
import ua.com.zaibalo.model.Post;

@WebServlet(urlPatterns={"/index.html","/index", "/category"}, loadOnStartup=1, name="IndexPage")
public class IndexServlet extends ServletPage{

	private static final long serialVersionUID = 1L;

	@Override
	public String run(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws ServletException, IOException{
		String categoryIdsParam = request.getParameter("categoryId");
		String orderByParam = request.getParameter("order_by");
		String countParam = request.getParameter("count");
		String pageParam = request.getParameter("page");
		
		int count = 10;
		
		if(StringHelper.isNotBlank(countParam) && StringHelper.isDecimal(countParam)){
			count = Integer.parseInt(countParam);
		}
		
		Post.PostOrder order = Post.PostOrder.ID;
		if(StringHelper.isNotBlank(orderByParam) && !"latest".equals(orderByParam)){
			order = Post.PostOrder.RATING_SUM;
		}		
		
		List<Integer> catIdsParam = null;
		if(StringHelper.isNotBlank(categoryIdsParam) && StringHelper.isArrayDecimal(categoryIdsParam, ",")){
			catIdsParam = new ArrayList<Integer>();
			for(String num: categoryIdsParam.split(",")){
				catIdsParam.add(Integer.parseInt(num));
			}
		}
		
		Date fromDate = null;		
		if(StringHelper.isNotBlank(orderByParam)){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			
			if("week".equals(orderByParam)){
				calendar.add(Calendar.WEEK_OF_YEAR, -1);
				fromDate = calendar.getTime();
			}else if("month".equals(orderByParam)){
				calendar.add(Calendar.MONTH, -1);
				fromDate = calendar.getTime();
			}else if("half_a_year".equals(orderByParam)){
				calendar.add(Calendar.MONTH, -6);
				fromDate = calendar.getTime();
			}else if("year".equals(orderByParam)){
				calendar.add(Calendar.YEAR, -1);
				fromDate = calendar.getTime();
			}else if("all_times".equals(orderByParam)){

			}
		}
		
		int from = -1;
		if(StringHelper.isDecimal(pageParam)){
			from = (Integer.parseInt(pageParam) - 1) * count;
		}
		
		DataAccessFactory factory = new DataAccessFactory(request);
		
		List<Post> postList = factory.getPostsAccessInstance().getPostsList(catIdsParam, fromDate, order, from, count);

		request.setAttribute("posts", postList);
		
		int pagingResultsSize = factory.getPostsAccessInstance().getPostsListSize(catIdsParam, fromDate);

		request.setAttribute("results_size", pagingResultsSize);
		
		return "/jsp/index.jsp";
	}
	
}
