package com.trello.utils;

import java.util.Hashtable;

public class TNRESTEndPoints {

	
	/** --------------------------- Channel Types ----------------------- **/
	public static final String CREATE_CHANNEL_TYPES = "/b2b/channel-types";
	public static final String GET_ALL_CHANNEL_TYPE = "/b2b/channel-types";
	public static final String GET_SPECIFIC_CHANNEL_TYPE = "/b2b/channel-types/{NAME}/describe";

	/** --------------------------- Boards ----------------------- **/
	public static final String CREATE_BOARDS = "/1/boards/?key=e9af85331c86ba7044e39b7b160061ac&token=067ef5fc56834c30b0dd9bb929bee521ed1fb7c85495f3673382a9f548474989&name=APITEST";
	public static final String GET_BOARDS="/1/boards/{ID}/lists?key=e9af85331c86ba7044e39b7b160061ac&token=067ef5fc56834c30b0dd9bb929bee521ed1fb7c85495f3673382a9f548474989";
	public static final String CREATE_CARD = "/1/cards?key=e9af85331c86ba7044e39b7b160061ac&token=067ef5fc56834c30b0dd9bb929bee521ed1fb7c85495f3673382a9f548474989&idList={LIST_ID}";
	public static final String DELETE_BOARD = "/1/boards/{ID}?key=e9af85331c86ba7044e39b7b160061ac&token=067ef5fc56834c30b0dd9bb929bee521ed1fb7c85495f3673382a9f548474989";
	public static final String GET_CARD = "/1/cards/{CARD_ID}?key=e9af85331c86ba7044e39b7b160061ac&token=067ef5fc56834c30b0dd9bb929bee521ed1fb7c85495f3673382a9f548474989&idList={LIST_ID}";

	
	

	
	
}
