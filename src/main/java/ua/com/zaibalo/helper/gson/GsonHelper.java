package ua.com.zaibalo.helper.gson;


import ua.com.zaibalo.helper.gson.adapters.SingleCommentAdapter;
import ua.com.zaibalo.helper.gson.adapters.SinglePostAdapter;
import ua.com.zaibalo.model.Comment;
import ua.com.zaibalo.model.Post;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonHelper {

	public static Gson getGsonWithExclusionStrategy(){
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Post.class, new SinglePostAdapter());
		gsonBuilder.registerTypeAdapter(Comment.class, new SingleCommentAdapter());

		Gson gson = gsonBuilder.
		addSerializationExclusionStrategy(new ExclusionStrategy() {
			
			@Override
			public boolean shouldSkipField(FieldAttributes arg0) {
				ExcludeFromJson anno = arg0.getAnnotation(ExcludeFromJson.class);
				return (anno != null);
			}
			
			@Override
			public boolean shouldSkipClass(Class<?> arg0) {
				return false;
			}
		}).create();
		
		
		return gson;
	}
}
