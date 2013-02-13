package com.ppierson.webservicetasks.utils;

import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;

public class WebserviceUtils {
	public static URI convertToURIEscapingIllegalCharacters(String string){
	    try {
	        String decodedURL = URLDecoder.decode(string, "UTF-8");
	        URL url = new URL(decodedURL);
	        URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef()); 
	        return uri; 
	    } catch (Exception ex) {
	        ex.printStackTrace();
	        return null;
	    }
	}
	
	public static URL convertToURLEscapingIllegalCharacters(String string){
	    try {
	        URI uri = convertToURIEscapingIllegalCharacters(string);
	        return uri.toURL();
	    } catch (Exception ex) {
	        ex.printStackTrace();
	        return null;
	    }
	}
}
