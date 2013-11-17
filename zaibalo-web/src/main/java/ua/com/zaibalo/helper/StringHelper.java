package ua.com.zaibalo.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.MessageFormat;
import java.util.Properties;
import java.util.Random;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.apache.commons.lang3.StringUtils;

import ua.com.zaibalo.i18n.I18nResources;

public class StringHelper {
	private static final String UTF_8 = "utf-8";
	private static final String RESOURCE_BUNDLE_UK_UA_PROPERTIES = "ResourceBundle_uk_UA.properties";
	private static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	private static final Properties ukUAproperties = new Properties();
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
		String escaped =  StringUtils.replaceEach(str, 
				new String[]{"&", "\"", "<", ">"}, 
				new String[]{"&amp;", "&quot;", "&lt;", "&gt;"});
		return escaped.replaceAll("\n", "<\\br>");
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
	
//	@Test
//	public void testExtractNormalCase(){
//		String actual = extract("Some random text just being typed inside this shit.", 20, "...");
//		assertEquals("Some random text...", actual);
//	}
//	
//	@Test
//	public void testExtractLongWordCase(){
//		String actual = extract("Somerandomtextjustbeingtypedinsidethisshit.", 20, "...");
//		assertEquals("Somerandomtextju...", actual);
//	}
//	
//	@Test
//	public void testExtractStartsWithSpacesCase(){
//		String actual = extract("                      Some random text just being typed.", 20, "...");
//		assertEquals("Some random text...", actual);
//	}
//	
//	@Test
//	public void testExtractOneSymbolCase1(){
//		String actual = extract("A                      B", 20, "...");
//		assertEquals("A...", actual);
//	}
//	
//	@Test
//	public void testExtractOneSymbolCase2(){
//		String actual = extract("A", 20, "...");
//		assertEquals("A", actual);
//	}
//	
//	@Test
//	public void testExtractSpecSymbolCase3(){
//		String actual = extract("\"~\"!@#$%^&*()09`{}\"?>:|<>?", 20, "...");
//		assertEquals("\"~\"!@#$%^&*()09`...", actual);
//	}
//
//	
//	@Test
//	public void testExtractSpecSymbolCase4(){
//		String actual = extract("Some random f...", 20, "...");
//		assertEquals("Some random f...", actual);
//	}	
}