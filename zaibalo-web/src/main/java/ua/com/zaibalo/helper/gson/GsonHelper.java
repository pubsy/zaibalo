package ua.com.zaibalo.helper.gson;


import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonHelper {

	public static Gson getGsonWithExclusionStrategy(){
		Gson gson = new GsonBuilder().
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
