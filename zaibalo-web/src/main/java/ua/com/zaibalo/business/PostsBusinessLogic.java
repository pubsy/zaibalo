package ua.com.zaibalo.business;


public class PostsBusinessLogic {

//	public List<Post> getPostsList(DataAccessFactory factory, String categoryIdsParam, String orderByParam, String countParam, String pageParam) {
//		int count = 10;
//		
//		if(StringHelper.isNotBlank(countParam) && StringHelper.isDecimal(countParam)){
//			count = Integer.parseInt(countParam);
//		}
//		
//		Post.PostOrder order = Post.PostOrder.ID;
//		if(StringHelper.isNotBlank(orderByParam) && !"latest".equals(orderByParam)){
//			order = Post.PostOrder.RATING_SUM;
//		}		
//		
//		String catIdsParam = validateCategoryIds(categoryIdsParam);
//		
//		Date fromDate = formatFromDate(orderByParam);
//		
//		int from = -1;
//		if(StringHelper.isDecimal(pageParam)){
//			from = (Integer.parseInt(pageParam) - 1) * count;
//		}
//		
//		return factory.getPostsAccessInstance().getPostsList(catIdsParam, fromDate, order, from, count);
//	}
//
//	public int getPagingResultsSize(DataAccessFactory factory, String categoryIdsParam, String orderByParam) {
//		String catIdsParam = validateCategoryIds(categoryIdsParam);
//		
//		Date fromDate = formatFromDate(orderByParam);
//		
//		return factory.getPostsAccessInstance().getPostsListSize(catIdsParam, fromDate);
//	}
//
//	private String validateCategoryIds(String categoryIdsParam) {
//		String catIdsParam = null;
//		if(StringHelper.isNotBlank(categoryIdsParam) && StringHelper.isArrayDecimal(categoryIdsParam, ",")){
//			catIdsParam = categoryIdsParam;
//		}
//		return catIdsParam;
//	}
//
//	private Date formatFromDate(String orderByParam) {
//		Date fromDate = null;		
//		if(StringHelper.isNotBlank(orderByParam)){
//			if("all_times".equals(orderByParam)){
//				return null;
//			}
//			
//			Calendar calendar = Calendar.getInstance();
//			calendar.setTime(new Date());
//			
//			if("week".equals(orderByParam)){
//				calendar.add(Calendar.WEEK_OF_YEAR, -1);
//			}else if("month".equals(orderByParam)){
//				calendar.add(Calendar.MONTH, -1);
//			}else if("half_a_year".equals(orderByParam)){
//				calendar.add(Calendar.MONTH, -6);
//			}else if("year".equals(orderByParam)){
//				calendar.add(Calendar.YEAR, -1);
//			}else{
//				//SOMETHING BAD HAPPENED
//				return null;
//			}
//			fromDate = calendar.getTime();
//		}
//		return fromDate;
//	}

}
