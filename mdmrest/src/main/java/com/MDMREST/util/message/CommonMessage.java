package com.MDMREST.util.message;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.hibernate.validator.resourceloading.PlatformResourceBundleLocator;

public class CommonMessage {
	public static String True = "True";
	public static String False = "False";	
	
	public final static String getMessage(Locale locale, String key) {
		
	    String message = key;
	    key = key.toString().replace("{", "").replace("}", "");

	    PlatformResourceBundleLocator bundleLocator = new PlatformResourceBundleLocator(ResourceBundleMessageInterpolator.USER_VALIDATION_MESSAGES);
	    ResourceBundle resourceBundle = bundleLocator.getResourceBundle(locale);

	    try {
	       message = resourceBundle.getString(key);
	    }
	    catch(MissingResourceException ex) {
	       message = key;
	    }

	    return message;
	}
}
