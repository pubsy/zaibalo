package ua.com.zaibalo.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.apache.commons.lang.StringUtils;

import ua.com.zaibalo.i18n.I18nResources;
import ua.com.zaibalo.model.Category;

public class StringHelper {
	private static final String UTF_8 = "utf-8";
	private static final String RESOURCE_BUNDLE_UK_UA_PROPERTIES = "ResourceBundle_uk_UA.properties";
	private static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	private static final Properties ukUAproperties = new Properties();
	private static final String HASH_TAG_REGEX = "(^|\\s|\\b)[\\#]\\p{L}+";
	
	static{
		try {
			Reader reader = new BufferedReader(new InputStreamReader(
					I18nResources.class.getResourceAsStream(RESOURCE_BUNDLE_UK_UA_PROPERTIES), UTF_8));
			ukUAproperties.load(reader);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String generateString(int length)
	{
		Random r = new Random(); 
		
	    char[] text = new char[length];
	    for (int i = 0; i < length; i++)
	    {
	        text[i] = CHARS.charAt(r.nextInt(CHARS.length()));
	    }
	    return new String(text);
	}
	
	public static boolean isValidEmailAddress(String email) {
		   boolean result = true;
		   try {
		      InternetAddress emailAddr = new InternetAddress(email);
		      emailAddr.validate();
		   } catch (AddressException ex) {
		      result = false;
		   }
		   return result;
	}
	
	public static String escapeXML(String str){
		return StringUtils.replaceEach(str, 
				new String[]{"&", "\"", "<", ">"}, 
				new String[]{"&amp;", "&quot;", "&lt;", "&gt;"});
	}
	
	public static String getLocalString(String key, Object... params){
		String pattern = ukUAproperties.getProperty(key);
		if(params != null && params.length > 0){
			pattern = new MessageFormat(pattern).format(params, new StringBuffer(), null).toString();
		}
		return pattern;
	}
	
	public static boolean isNotBlank(String str){
		return !isBlank(str);
	}
	
	public static boolean isBlank(String str){
		if(str == null || "".equals(str)){
			return true;
		}
		return false;
	}
	
	public static boolean isDecimal(String str){
		try{
			Integer.parseInt(str);
		}catch(NumberFormatException e){
			return false;
		}
		return true;
	}
	
	public static boolean isArrayDecimal(String str, String separator){
		String[] array = str.split(separator);
		try{
			for(String value: array){
				Integer.parseInt(value);
			}
		}catch(NumberFormatException e){
			return false;
		}
		return true;
	}

	public static String extract(String text, int maxWords, String postFix) {
		text = text.trim();
		maxWords = maxWords - postFix.length();
		StringBuilder sb = new StringBuilder();
		String[] split = text.split(" ");
		
		if(split.length == 1 && split[0].length() >= maxWords){
			sb.append(split[0].substring(0, maxWords));
		}
		
		for(String word: split){
			if(sb.length() < maxWords){
				if(sb.length() + word.length() < maxWords){
					sb.append(word + " ");
				}
			}
		}

		sb.deleteCharAt(sb.length() - 1);
		
		if(text.length() > maxWords - postFix.length()){
			
			while(sb.lastIndexOf(".") == sb.length() - 1){
				sb.deleteCharAt(sb.length() -1);
			}
			
			sb = new StringBuilder(sb.toString().trim());
			
			sb.append(postFix);
		}
		
		return sb.toString();
	}
	
	public static String extract(String text, int maxWords){
		return extract(text, maxWords, "");
	}

	public static String replaceTagsWithLinks(Set<Category> categories, String content) {
		for(Category tag: categories){
			content = content.replaceAll(tag.getName(), "<a href=\"category/" + tag.getId() + "\">"+ tag.getName() + "</a>");
		}
		return content;
	}
	
	public static Set<String> parseTags(String text) {
		Set<String> postTags = new HashSet<String>();
		
		Pattern pattern = Pattern.compile(HASH_TAG_REGEX);
		Matcher matcher = pattern.matcher(text);
		
		while (matcher.find()) {
			postTags.add(matcher.group(0).trim());
		}
		return postTags;
	}

}