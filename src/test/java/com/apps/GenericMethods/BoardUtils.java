package com.apps.GenericMethods;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;

import com.apps.base.TestBase;
import com.trello.testDefs.ServiceBoard;


public class BoardUtils extends TestBase {
	private Map<String, String> responseFromApiGet = new HashMap<>();
	
	private ServiceBoard sChannel = new ServiceBoard();
	
	public String listID="";


	public boolean verifyCreateBoard( Map<String, String> responseFromAPI)
			throws UnsupportedEncodingException {
		boolean verified = false;
		
		
		JSONObject response_json = new JSONObject(responseFromAPI.get("response"));
		String boardId = response_json.getString("id");
		responseFromApiGet = sChannel.getBoardDetails(boardId);

		Assert.assertEquals("200", responseFromApiGet.get("status"), "Response code for Get Call doesn't match");
		printResponse(Thread.currentThread().getStackTrace()[1].getMethodName(), responseFromApiGet.get("response"));
		
	
		JSONParser parser = new JSONParser();
		 
         Object obj;
		try {
			obj = parser.parse(responseFromApiGet.get("response"));
			JSONArray array = (JSONArray) obj;
			listID=(String) ((org.json.simple.JSONObject)array.get(0)).get("id");
			 for(int i = 0; i < array.size(); i++)
	         {
				 
				 org.json.simple.JSONObject objects = (org.json.simple.JSONObject)array.get(i);
	            System.out.println(objects.get("id")+" "+objects.get("name")+" "+objects.get("idBoard"));
	            
	        	 Assert.assertEquals(boardId,((org.json.simple.JSONObject)array.get(i)).get("idBoard"));
	         } 
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         
    
		verified = true;
		return verified;

	}

	public boolean verifyCreateCard( Map<String, String> responseFromAPI)
			throws UnsupportedEncodingException {
		boolean verified = false;
		
		
		JSONObject response_json = new JSONObject(responseFromAPI.get("response"));
		String cardId = response_json.getString("id");
		responseFromApiGet = sChannel.getCardDetails(cardId,listID);

		Assert.assertEquals("200", responseFromApiGet.get("status"), "Response code for Get Call doesn't match");
		printResponse(Thread.currentThread().getStackTrace()[1].getMethodName(), responseFromApiGet.get("response"));
		
		Assert.assertEquals(listID,response_json.getString("idList"));
		
		
		
		
    
		verified = true;
		return verified;

	}

	
	}











