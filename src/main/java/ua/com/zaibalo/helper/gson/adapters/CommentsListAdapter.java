package ua.com.zaibalo.helper.gson.adapters;

import java.lang.reflect.Type;

import ua.com.zaibalo.model.Comment;
import ua.com.zaibalo.spring.SpringPropertiesUtil;

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
        
        String avatarUrl = comment.getAuthor().getBigImgPath() + "?size=small";
        obj.addProperty("authorAvatarUrl", avatarUrl);
        
        obj.addProperty("content", comment.getContent());
        
        return obj;
	}

}
