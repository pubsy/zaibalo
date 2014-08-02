package ua.com.zaibalo.helper.gson.adapters;

import java.lang.reflect.Type;

import ua.com.zaibalo.helper.gson.GsonHelper;
import ua.com.zaibalo.model.Comment;
import ua.com.zaibalo.model.Post;
import ua.com.zaibalo.servlets.listener.ContextInitListener;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class SinglePostAdapter implements JsonSerializer<Post>{

	@Override
	public JsonElement serialize(Post post, Type type, JsonSerializationContext arg2) {
		JsonObject obj = new JsonObject();
		obj.addProperty("id", post.getId());
        obj.addProperty("title", post.getTitle());
        if(post.getDate() != null){
        	obj.addProperty("date", post.getDate().toString());
        }
        if(post.getAuthor() != null){
        	obj.addProperty("authorName", post.getAuthor().getDisplayName());
        }
        obj.addProperty("ratingSum", post.getRatingSum());
        obj.addProperty("ratingCount", post.getRatingCount());
        obj.addProperty("commentCount", post.getComments().size());
        
        String avatarUrl = "http://" + ContextInitListener.getProperty("app.server.domain")  + "/image/" +  post.getAuthor().getSmallImgPath();
        obj.addProperty("authorAvatarUrl", avatarUrl);
        
        Gson gson = GsonHelper.getGsonWithExclusionStrategy();
        JsonElement cats = gson.toJsonTree(post.getCategories());
        obj.add("categories", cats);
        
        obj.addProperty("content", post.getContent());
        
        gson = new GsonBuilder().registerTypeAdapter(Comment.class, new CommentsListAdapter()).create();
        JsonElement comments = gson.toJsonTree(post.getComments());
        obj.add("comments", comments);
        
        return obj;
	}

}
