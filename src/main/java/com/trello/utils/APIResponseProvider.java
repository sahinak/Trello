package com.trello.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.NewCookie;

import com.sun.jersey.api.client.ClientResponse;

public class APIResponseProvider 
{
	public static Map<String,String> getResponse(ClientResponse response,String responseType, String fileName) 
	{
		String response_string=null;
		Map<String,String> apiResponseString = new HashMap<>();
		 int status = response.getStatus();
         if(status!=204)
         {
        	
        				response_string = response.getEntity(String.class);
        			
        	 }
        	 else
        	 {
        		 response_string = response.getEntity(String.class);
        	 }
         
         
         apiResponseString = new HashMap<>();
         apiResponseString.put("response", response_string);
         apiResponseString.put("status", ""+status);
         
         return apiResponseString;
	}
	
	public static String getCookies(String cookiName,ClientResponse response)
	{
      List<NewCookie> cookies= response.getCookies();
      response.getHeaders().get("Set-Cookie");
      response.getEntity(String.class);
      
    
      for (NewCookie c : cookies) {
          if(c.getName().equalsIgnoreCase(cookiName))
          {
        	return c.getValue();  
          }
          
      }
      
      return null;
      
	}
	
}
