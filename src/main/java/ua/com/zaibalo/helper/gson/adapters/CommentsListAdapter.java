package ua.com.zaibalo.helper.gson.adapters;

import java.lang.reflect.Type;

import ua.com.zaibalo.model.Comment;
import ua.com.zaibalo.servlets.listener.ContextInitListener;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class CommentsListAdapter implements JsonSerializer<Comment>{

	@Override
	public JsonElement serialize(Comment comment, Type type, JsonSerializationContext arg2) {
		JsonObject obj = new JsonObject();
		obj.addProperty("id", comment.getId());
		obj.addProperty("date", Long.valueOf(comment.getDate().getTime()));
        obj.addProperty("authorName", comment.getAuthor().getDisplayName());
        obj.addProperty("ratingSum", comment.getRatingSum());
        obj.addProperty("ratingCount", comment.getRatingCount());
        
        //String avatarUrl = "http://10.0.2.2:8080" + post.getAuthor().getSmallImgAbsolutePath();
        String avatarUrl = "http://" + ContextInitListener.getProperty("app.server.domain") + "/image/" + comment.getAuthor().getSmallImgPath();
        obj.addProperty("authorAvatarUrl", avatarUrl);
        
        obj.addProperty("content", comment.getContent());
        
        return obj;
	}

}
