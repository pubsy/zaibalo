package ua.com.zaibalo.rss;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.feed.AbstractRssFeedView;

import ua.com.zaibalo.helper.StringHelper;
import ua.com.zaibalo.model.Post;

import com.sun.syndication.feed.rss.Channel;
import com.sun.syndication.feed.rss.Description;
import com.sun.syndication.feed.rss.Item;
 
public class CustomRssViewer extends AbstractRssFeedView {
	
	@Override
	protected void buildFeedMetadata(Map<String, Object> model, Channel feed,
		HttpServletRequest request) {
 
		feed.setTitle(StringHelper.getLocalString("zaibalo_blog"));
		feed.setDescription(StringHelper.getLocalString("zaibalo_descr"));
		feed.setLink("http://zaibalo.com.ua");
 
		super.buildFeedMetadata(model, feed, request);
	}
 
	@Override
	protected List<Item> buildFeedItems(Map<String, Object> model,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
 
		@SuppressWarnings("unchecked")
		List<Post> listContent = (List<Post>) model.get("feedContent");
		List<Item> items = new ArrayList<Item>(listContent.size());
 
		for(Post post : listContent ){
 
			Item item = new Item();
 
			Description description = new Description();
			description.setValue(StringHelper.escapeXML(post.getContent()));
			item.setDescription(description);
 
			item.setTitle(post.getTitle());
			String link = "http://www.zaibalo.com.ua/post/" + post.getId();
			item.setLink(link);
			item.setPubDate(post.getDate());
 
			items.add(item);
		}
 
		return items;
	}
}