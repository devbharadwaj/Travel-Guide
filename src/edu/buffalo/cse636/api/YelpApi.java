package edu.buffalo.cse636.api;

import java.io.IOException;
import java.text.DateFormat;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.mongodb.BasicDBObject;

public class YelpApi {
	private static final String API_HOST = "api.yelp.com";
	// private static final String DEFAULT_TERM = "bar";
	private static final int SEARCH_LIMIT = 10;
	private static final String DEFAULT_LOCATION = "sf";
	private static final int SEARCH_SORT = 2;
	private static final String SEARCH_PATH = "/v2/search";
	private static final String BUSINESS_PATH = "/v2/business";

	private static final String CONSUMER_KEY = "Ml6uB_LL3yHyxU0oecv7tQ";
	private static final String CONSUMER_SECRET = "GSWEft5_aYv7PWNebPphvTCEx0k";
	private static final String TOKEN = "dWf1JnUiUUb__HNpYLyqYAa8C9eKIyAV";
	private static final String TOKEN_SECRET = "Hel-GZx_vQxZUTDX1ObTbZkvZ60";

	OAuthService service;
	Token accessToken;

	public YelpApi(String consumerKey, String consumerSecret, String token,
			String tokenSecret) {
		this.service = new ServiceBuilder().provider(TwoStepOAuth.class)
				.apiKey(consumerKey).apiSecret(consumerSecret).build();
		this.accessToken = new Token(token, tokenSecret);
	}

	public String searchForBusinessesByLocation(String term, String location) {
		OAuthRequest request = createOAuthRequest(SEARCH_PATH);
		request.addQuerystringParameter("term", term);
		request.addQuerystringParameter("location", location);
		request.addQuerystringParameter("limit", String.valueOf(SEARCH_LIMIT));
		request.addQuerystringParameter("sort", String.valueOf(SEARCH_SORT));
		return sendRequestAndGetResponse(request);
	}

	public String searchByBusinessId(String businessID) {
		OAuthRequest request = createOAuthRequest(BUSINESS_PATH + "/"
				+ businessID);
		return sendRequestAndGetResponse(request);
	}

	private OAuthRequest createOAuthRequest(String path) {
		OAuthRequest request = new OAuthRequest(Verb.GET, "http://" + API_HOST
				+ path);
		return request;
	}

	private String sendRequestAndGetResponse(OAuthRequest request) {
		this.service.signRequest(this.accessToken, request);
		//System.out.println(request.getUrl());
		org.scribe.model.Response response = request.send();
		return response.getBody();
	}

	private static List<JsonNode> queryAPI(YelpApi yelpApi,
			YelpAPICLI yelpApiCli) {
		String searchResponseJSON = yelpApi.searchForBusinessesByLocation(
				yelpApiCli.term, yelpApiCli.location);

		JSONParser parser = new JSONParser();
		JSONObject response = null;
		ArrayList<JsonNode> list = new ArrayList<JsonNode>();
		try {
			response = (JSONObject) parser.parse(searchResponseJSON);
		} catch (ParseException pe) {
			System.out.println("Error: could not parse JSON response:");
			System.out.println(searchResponseJSON);
			System.exit(1);
		}

		JSONArray businesses = (JSONArray) response.get("businesses");
		//System.out.println(String.format("%s businesses found for %s",
		//		businesses.size(), yelpApiCli.term));
		if (businesses == null) {
			return null;
		}
		for (int i = 0; i < businesses.size(); i++) {

			JSONObject firstBusiness = (JSONObject) businesses.get(i);
			String firstBusinessID = Normalizer.normalize(
					firstBusiness.get("id").toString(), Normalizer.Form.NFD)
					.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
			String businessResponseJSON = yelpApi
					.searchByBusinessId(firstBusinessID.toString());

			try {
				JsonNode listNode = new ObjectMapper()
						.readTree(businessResponseJSON.toString());
				list.add(listNode);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		//System.out.println("\n");
		return list;
	}

	private static class YelpAPICLI {
		private static final String DEFAULT_TERM = null;

		@Parameter(names = { "-q", "--term" }, description = "Search Query Term")
		public String term = DEFAULT_TERM;

		@Parameter(names = { "-l", "--location" }, description = "Location to be Queried")
		public String location = DEFAULT_LOCATION;

	}

	public static List<JsonNode> getCoffeeInformation(String location,
			YelpApi yelpApi) {

		YelpAPICLI yelpCli = new YelpAPICLI();
		yelpCli.location = location;
		yelpCli.term = "Coffee&Tea";
		return queryAPI(yelpApi, yelpCli);
	}

	public static List<JsonNode> getRestaurantsInformation(String location,
			YelpApi yelpApi) {

		YelpAPICLI yelpCli = new YelpAPICLI();
		yelpCli.location = location;
		yelpCli.term = "Restaurants";
		return queryAPI(yelpApi, yelpCli);
	}

	public static List<JsonNode> getDonutsInformation(String location,
			YelpApi yelpApi) {

		YelpAPICLI yelpCli = new YelpAPICLI();
		yelpCli.location = location;
		yelpCli.term = "Donuts";
		return queryAPI(yelpApi, yelpCli);
	}

	public static List<JsonNode> getBeerWineSpiritsInformation(String location,
			YelpApi yelpApi) {

		YelpAPICLI yelpCli = new YelpAPICLI();
		yelpCli.location = location;
		yelpCli.term = "Beer Wine Spirits";
		return queryAPI(yelpApi, yelpCli);
	}

	public static List<JsonNode> getIceCreamInformation(String location,
			YelpApi yelpApi) {

		YelpAPICLI yelpCli = new YelpAPICLI();
		yelpCli.location = location;
		yelpCli.term = "Ice Cream";
		return queryAPI(yelpApi, yelpCli);
	}

	public static List<JsonNode> getBakeriesInformation(String location,
			YelpApi yelpApi) {

		YelpAPICLI yelpCli = new YelpAPICLI();
		yelpCli.location = location;
		yelpCli.term = "Bakeries";
		return queryAPI(yelpApi, yelpCli);
	}
	
	public static List<JsonNode> getFoodTrucksInformation(String location,
			YelpApi yelpApi) {
		
		YelpAPICLI yelpCli = new YelpAPICLI();
		yelpCli.location = location;
		yelpCli.term = "Food Truck";
		return queryAPI(yelpApi, yelpCli);
	}

	public static void main(String[] args) {
		YelpAPICLI yelpApiCli = new YelpAPICLI();
		YelpApi yelpApi = new YelpApi(CONSUMER_KEY, CONSUMER_SECRET, TOKEN,
				TOKEN_SECRET);
		new JCommander(yelpApiCli, args);

		try {
/*			printList(getCoffeeInformation("buffalo", yelpApi));
			printList(getRestaurantsInformation("sf", yelpApi));
			printList(getBeerWineSpiritsInformation("sf", yelpApi));
			printList(getIceCreamInformation("sf", yelpApi));
			printList(getDonutsInformation("sf", yelpApi));
			printList(getBakeriesInformation("sf", yelpApi));
			printList(getFoodTrucksInformation("sf", yelpApi));
*/			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static BasicDBObject yelpLocation(String city) {
		BasicDBObject returnCity = new BasicDBObject();
		BasicDBObject yelpCity = new BasicDBObject();
		String args[] = null;
		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		Date date = new Date();
		returnCity.put("Date", dateFormat.format(date));
		
		YelpAPICLI yelpApiCli = new YelpAPICLI();
		YelpApi yelpApi = new YelpApi(CONSUMER_KEY, CONSUMER_SECRET, TOKEN,
				TOKEN_SECRET);
		//new JCommander(yelpApiCli, args);

		printList(getCoffeeInformation(city, yelpApi), "Coffee & Tea", yelpCity);
		printList(getRestaurantsInformation(city, yelpApi), "Restaurants", yelpCity);
		printList(getBeerWineSpiritsInformation(city, yelpApi), "Bars", yelpCity);
		printList(getIceCreamInformation(city, yelpApi), "Ice-Cream Parlor", yelpCity);
		printList(getDonutsInformation(city, yelpApi), "Donuts", yelpCity);
		printList(getBakeriesInformation(city, yelpApi), "Bakeries", yelpCity);
		printList(getFoodTrucksInformation(city, yelpApi),"Food Trucks", yelpCity);
		returnCity.put(city, yelpCity);
		return returnCity;
	}

	/**
	 * @param list
	 */
	public static void printList(List<JsonNode> list, String place, BasicDBObject yelpCity) {
		BasicDBObject eatery = new BasicDBObject();
		if (list == null) {
			yelpCity.put(place,null);
			return;
		}
		int rank = 1;
		for (int i = 0; i < list.size(); i++) {
			HashMap<String,String> jsonLocation = new HashMap<>();
			BasicDBObject jsonOfPlace = new BasicDBObject();
			JsonNode listNode = list.get(i);
			JsonNode name = listNode.get("name");
			JsonNode rating = listNode.get("rating");

			//System.out.println("Business Name:" + name);
			jsonLocation.put("Business Name", (name == null) ? null : name.toString().replaceAll("\\\"", ""));
			//System.out.println("Rating:" + rating);
			jsonLocation.put("Rating", (rating == null) ? null : rating.toString());
			
			JsonNode location = listNode.get("location");
			JsonNode address = (location == null) ? null : location.get("display_address");
			//System.out.print("Address: ");
			if (address == null) {
				jsonLocation.put("Address", null);
				jsonLocation.put("Latitude", null);
				jsonLocation.put("Longitude", null);
			}
			else {
				StringBuffer loc = new StringBuffer();
				for (final JsonNode elemNode : address) {
					//System.out.println(elemNode);
					loc.append(elemNode);
				}
				jsonLocation.put("Address", (loc == null) ? null : loc.toString().replaceAll("\\\"", " "));
			
				JsonNode phoneNumber = listNode.get("display_phone");
				jsonLocation.put("Phone", (phoneNumber == null) ? null : phoneNumber.toString().replaceAll("\\\"", ""));
				
				JsonNode coordinates = location.get("coordinate");
				jsonLocation.put("Latitude", (coordinates == null) ? null : coordinates.get("latitude").toString());
				jsonLocation.put("Longitude", (coordinates == null) ? null : coordinates.get("longitude").toString());
			}
			jsonOfPlace.putAll(jsonLocation);
			eatery.put(String.valueOf(rank++), jsonOfPlace);
		}
		yelpCity.put(place, eatery);
	}
}