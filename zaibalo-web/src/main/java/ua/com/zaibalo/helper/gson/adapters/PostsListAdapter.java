package ua.com.zaibalo.helper.gson.adapters;

import java.lang.reflect.Type;

import ua.com.zaibalo.helper.StringHelper;
import ua.com.zaibalo.helper.gson.GsonHelper;
import ua.com.zaibalo.model.Post;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class PostsListAdapter implements JsonSerializer<Post>{

	@Override
	public JsonElement serialize(Post post, Type type, JsonSerializationContext arg2) {
		JsonObject obj = new JsonObject();
		obj.addProperty("id", post.getId());
        obj.addProperty("title", post.getTitle());
        obj.addProperty("date", Long.valueOf(post.getDate().getTime()));
        obj.addProperty("authorName", post.getAuthor().getDisplayName());
        obj.addProperty("ratingSum", post.getRatingSum());
        obj.addProperty("ratingCount", post.getRatingCount());
        obj.addProperty("commentCount", post.getComments().size());
        
        String avatarUrl = post.getAuthor().getSmallImgPath();
        obj.addProperty("authorAvatarUrl", avatarUrl);
        
        Gson gson = GsonHelper.getGsonWithExclusionStrategy();
        JsonElement cats = gson.toJsonTree(post.getCategories());
        obj.add("categories", cats);
        
        obj.addProperty("contentExtract", StringHelper.extract(post.getContent(), 120, "..."));
        return obj;
	}

}
