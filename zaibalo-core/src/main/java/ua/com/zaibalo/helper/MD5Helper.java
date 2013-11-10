package ua.com.zaibalo.helper;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Helper {

	public static String getMD5Of(String str){
	    MessageDigest m;
		try {
			m = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	    m.update(str.getBytes(),0,str.length());
	    return new BigInteger(1,m.digest()).toString(16);
	}
}
