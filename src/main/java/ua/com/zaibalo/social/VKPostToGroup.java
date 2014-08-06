package ua.com.zaibalo.social;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import ua.com.zaibalo.spring.SpringPropertiesUtil;

public class VKPostToGroup {

	public static void postToVKGroup(String text) {
		//http://oauth.vkontakte.ru/authorize?client_id=2863655&scope=wall&redirect_uri=http://api.vk.com/blank.html&display=page&response_type=token

		HttpClient httpclient = new DefaultHttpClient();
		HttpPost post = new HttpPost("https://api.vk.com/method/wall.post");

		String token = SpringPropertiesUtil.getProperty("vk.access.token");
		
		List<NameValuePair> qparams = new ArrayList<NameValuePair>();
		qparams.add(new BasicNameValuePair("owner_id", "-10793353"));
		qparams.add(new BasicNameValuePair("from_group", "1"));
		qparams.add(new BasicNameValuePair("access_token", token));
		qparams.add(new BasicNameValuePair("message", text));
		
		HttpResponse response = null;
		try {
			post.setEntity(new UrlEncodedFormEntity(qparams, HTTP.UTF_8));
			response = httpclient.execute(post);
			
			BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String inputLine;
			while ((inputLine = in.readLine()) != null)
				System.out.println(inputLine);
			in.close();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		post.abort();
	}

}
