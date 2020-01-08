package com.MDMREST.service.mdm;

import java.nio.charset.Charset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
	@Autowired
	private Environment env;
	
	private String base64Decode(String token) {
		// TODO Auto-generated method stub
		Base64 base64 = new Base64();
    	byte[] decodedBytes = base64.decode(token.getBytes());
    	String decodeString =  new String(decodedBytes, Charset.forName("UTF-8"));		
    	int index1 = decodeString.indexOf("3du");
    	int index2 = decodeString.indexOf("l0g");
    	int lengthChar = decodeString.length();
    	if(lengthChar %4 != 0) {
    		return "";
    	}
    	if((index1 < 3) & (index2 >=3))
    	{
    		lengthChar = lengthChar - 3;
    	}
    	if(lengthChar > 0) {
    		decodeString = decodeString.substring(3, lengthChar);
    	}    	
    	return decodeString;
	}
	public Boolean loginWeb (String userName, String token) {
    	String encodeResult = base64Decode(token);
    	String strUser = env.getProperty("web.user");
    	String strPass = env.getProperty("web.pass");
    	if((userName.equalsIgnoreCase(strUser)) & encodeResult.equals(strPass)) {
    		return true;
    	}
    	return false;
    }
}