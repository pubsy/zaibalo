package ua.com.zaibalo.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ua.com.zaibalo.db.api.PostsDAO;
import ua.com.zaibalo.model.Post;
import ua.com.zaibalo.model.Post.PostOrder;

@Component
@Transactional(propagation=Propagation.REQUIRED)
public class PostsBusinessLogic {
	
	@Autowired
	private PostsDAO postsDAO;
	
	public List<Post> getOrderedList(int from, int count, PostOrder order) {
		return postsDAO.getOrderedList(-1, 10, Post.PostOrder.ID);
	}

}
