package com.apps.testcases;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.ITest;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.apps.GenericMethods.BoardUtils;
import com.apps.base.TestBase;
import com.trello.testDefs.ServiceBoard;

public class BoardCrud extends TestBase implements ITest {
	private Map<String, String> responseFromApi = new HashMap<>();
	private ServiceBoard sChannel = new ServiceBoard();
	private BoardUtils cUtils = new BoardUtils();
	
	private String statusCodeActual="200";
	private ArrayList<String> boardIDsArray;
	private ArrayList<String> cardIDsArray;


	



	@BeforeClass
	public void initializePreRequisite() {
		
		boardIDsArray = new ArrayList<String>();
		cardIDsArray = new ArrayList<String>();

	}

	

	
	@AfterClass
	
	 public void teardown() throws IOException {
	 
	  for (String s : boardIDsArray) 
	  { 
		  System.out.println("The value of s is " + s);
		  responseFromApi = sChannel.deleteBoard(s);
	  System.out.println("The response from api is " + responseFromApi);
	  statusCodeActual = responseFromApi.get("status");
	  System.out.println("The actual status is" + statusCodeActual);
	  Assert.assertEquals(statusCodeActual, "200", "Status Code does not match");
	  }}
	 

	   @Test
		public void CreateBoard() throws IOException, JSONException {
		
		String STATUS_CODE_EXPECTED ="200";

		responseFromApi = sChannel.createBoard();
		statusCodeActual = responseFromApi.get("status");
		printResponse(Thread.currentThread().getStackTrace()[1].getMethodName(), responseFromApi.get("response"));
		Assert.assertEquals(statusCodeActual, STATUS_CODE_EXPECTED, "Status Code does not match");
		if (STATUS_CODE_EXPECTED.equalsIgnoreCase("200")) {
			cUtils.verifyCreateBoard( responseFromApi);
			JSONObject response_json = new JSONObject(responseFromApi.get("response"));
			String boardId = response_json.getString("id");
			boardIDsArray.add(boardId);
			System.out.println("The board ID's are" + boardIDsArray);
		}

	}
	   
	   @Test
	 		public void CreateCard() throws IOException, JSONException {
	 		
	 		String STATUS_CODE_EXPECTED ="200";
	 		String listID=cUtils.listID;
	 		responseFromApi = sChannel.createCard(listID);
	 		statusCodeActual = responseFromApi.get("status");
	 		printResponse(Thread.currentThread().getStackTrace()[1].getMethodName(), responseFromApi.get("response"));
	 		Assert.assertEquals(statusCodeActual, STATUS_CODE_EXPECTED, "Status Code does not match");
	 		if (STATUS_CODE_EXPECTED.equalsIgnoreCase("200")) {
	 			cUtils.verifyCreateCard( responseFromApi);
	 			JSONObject response_json = new JSONObject(responseFromApi.get("response"));
	 			String cardId = response_json.getString("id");
	 			cardIDsArray.add(cardId);
	 			System.out.println("The card ID's are" + cardIDsArray);
	 		}

	 	}



	@Override
	public String getTestName() {
		// TODO Auto-generated method stub
		return null;
	}




}


























