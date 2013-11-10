package ua.com.zaibalo.servlets.pages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.com.zaibalo.db.DataAccessFactory;
import ua.com.zaibalo.helper.StringHelper;
import ua.com.zaibalo.model.Post;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.io.SyndFeedOutput;

@WebServlet(urlPatterns = {"/feed"}, initParams = {@WebInitParam(name = "default.feed.type", value = "rss_2.0")}, name="RSS")
public class RssServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

    private String defaultType;

    public void init() {
        defaultType = getServletConfig().getInitParameter("default.feed.type");
        defaultType = (defaultType != null) ? defaultType : "atom_0.3";
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
        	
            SyndFeed feed = getFeed(request, response);

            String feedType = request.getParameter("type");
            feedType = (feedType!=null) ? feedType : defaultType;
            feed.setFeedType(feedType);

            response.setContentType("application/xml; charset=UTF-8");
            SyndFeedOutput output = new SyndFeedOutput();
            output.output(feed,response.getWriter());
            
        }
        catch (Exception ex) {
            String msg ="Could not generate feed";
            log(msg,ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,msg);
        }
    }

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    
    @Override
    public String getServletInfo() {
        return "Short description";
    }

    protected SyndFeed getFeed(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	
    	DataAccessFactory factory = new DataAccessFactory(request);
    	List<Post> list = factory.getPostsAccessInstance().getOrderedList(-1, 10, Post.PostOrder.ID);
    	
        SyndFeed feed = new SyndFeedImpl();
        feed.setTitle(StringHelper.getLocalString("zaibalo_blog"));
        feed.setLink("http://www.zaibalo.com.ua");
        feed.setDescription(StringHelper.getLocalString("zaibalo_descr"));

        //create the feeds.Each tutorial will be a feed entry
        List<SyndEntry> entries = new ArrayList<SyndEntry>();
        for (Post post : list) {
            SyndEntry entry = new SyndEntryImpl();
            SyndContent description;
            String title = post.getTitle();
            String link = "http://www.zaibalo.com.ua/post.do?id=" + post.getId();
            entry.setTitle(title);
            entry.setLink(link);
            entry.setPublishedDate(post.getDate());
            
            
            //Create the description of the feed entry
            description = new SyndContentImpl();
            description.setType("text/plain");
            description.setValue(StringHelper.escapeXML(post.getContent()));
            entry.setDescription(description);
            entries.add(entry);
        }
        feed.setEntries(entries);
        return feed;
    }

}
