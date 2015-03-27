package ua.com.zaibalo.helper.gson.adapters;

import java.lang.reflect.Type;
import java.text.DateFormat;

import ua.com.zaibalo.helper.gson.GsonHelper;
import ua.com.zaibalo.model.Comment;
import ua.com.zaibalo.model.User;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class SingleCommentAdapter implements JsonSerializer<Comment> {

	@Override
	public JsonElement serialize(Comment comment, Type arg1, JsonSerializationContext arg2) {
		JsonObject obj = new JsonObject();
		obj.addProperty("id", comment.getId());
        obj.addProperty("date", DateFormat.getDateTimeInstance(
                DateFormat.SHORT, DateFormat.SHORT).format(comment.getDate()));
        obj.addProperty("ratingSum", comment.getRatingSum());
        obj.addProperty("ratingCount", comment.getRatingCount());
        obj.addProperty("content", comment.getContent());
        
        Gson gson = GsonHelper.getGsonWithExclusionStrategy();
        User u = new User();
        u.setDisplayName(comment.getAuthor().getDisplayName());
        u.setBigImgPath("/image/" + comment.getAuthor().getBigImgPath());
        JsonElement author = gson.toJsonTree(u);
        obj.add("author", author);

        return obj;
	}

}
