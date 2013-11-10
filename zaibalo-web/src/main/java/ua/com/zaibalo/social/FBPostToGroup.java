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

import ua.com.zaibalo.helper.AppProperties;

public class FBPostToGroup {

	public static void postToFBGroup(String text) {


		String accessToken = AppProperties.getProperty("fb.access.token");
		String url = "https://graph.facebook.com/285081091622988/feed";
		
		List<NameValuePair> qparams = new ArrayList<NameValuePair>();
		qparams.add(new BasicNameValuePair("access_token", accessToken));
		qparams.add(new BasicNameValuePair("message", text));

		HttpClient httpclient = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);

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