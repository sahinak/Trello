package com.trello.testDefs;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import com.trello.base.ServiceBase;
import com.trello.utils.CommonUtils;
import com.trello.utils.GenericRESTHandler;
import com.trello.utils.TNRESTEndPoints;

public class ServiceBoard extends ServiceBase {
	private Map<String, String> responseFromApi = new HashMap<>();
	private String URI = "";

	public Map<String, String> createBoard()
			throws UnsupportedEncodingException {
		URI = CommonUtils.getBaseURI() + TNRESTEndPoints.CREATE_BOARDS;
		System.out.println("REST END POINT: POST:: " + URI);
		responseFromApi = genRESTHandler.ExecuteAPI(GenericRESTHandler.HTTPMethod.POST, URI, null, null, null);
		return responseFromApi;
	}
	
	public Map<String, String> createCard(String listId)
			throws UnsupportedEncodingException {
		URI = CommonUtils.getBaseURI() + TNRESTEndPoints.CREATE_CARD.replace("{LIST_ID}", listId);
		System.out.println("REST END POINT: POST:: " + URI);
		responseFromApi = genRESTHandler.ExecuteAPI(GenericRESTHandler.HTTPMethod.POST, URI, null, null, null);
		return responseFromApi;
	}

	
	 
	  public Map<String, String> getBoardDetails(String boardId) throws UnsupportedEncodingException
	  
	  { 
		  URI =
	  CommonUtils.getBaseURI() + TNRESTEndPoints.GET_BOARDS.replace("{ID}", boardId); 
		  System.out.println("REST END POINT: GET:: " + URI);
	 responseFromApi = genRESTHandler.ExecuteAPI(GenericRESTHandler.HTTPMethod.GET, URI, null,null, null);
		 return responseFromApi; 
	 }
	  
   public Map<String, String> getCardDetails(String cardId,String listId) throws UnsupportedEncodingException
	  
	  { 
		  URI =
	  CommonUtils.getBaseURI() + TNRESTEndPoints.GET_CARD.replace("{CARD_ID}", cardId).replace("{LIST_ID}", listId); 
		  System.out.println("REST END POINT: GET:: " + URI);
	 responseFromApi = genRESTHandler.ExecuteAPI(GenericRESTHandler.HTTPMethod.GET, URI, null,null, null);
		 return responseFromApi; 
	 }
	 
	  public Map<String, String> deleteBoard( String boardId) throws UnsupportedEncodingException 
	  { 
		  URI =
	  CommonUtils.getBaseURI() + TNRESTEndPoints.DELETE_BOARD.replace("{ID}", boardId); 
		  System.out.println("REST END POINT: DELETE:: " + URI);
	  responseFromApi = genRESTHandler.ExecuteAPI(GenericRESTHandler.HTTPMethod.DELETE, URI,null,null, null);
	  return responseFromApi;
	  }
	 
}
