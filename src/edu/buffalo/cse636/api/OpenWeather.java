package edu.buffalo.cse636.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class OpenWeather {

	public static void main(String[] args) {
		String place = "Washington DC";
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		Date date1 = null;
		Date date2 = null;
		try {
			date1 = format.parse("11/18/2014");
			date2 = format.parse("11/25/2014");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		OpenWeather o1 = new OpenWeather();
		BasicDBObject game = OpenWeather.getWeather(place);
		
		Mongo mongo;
		try {
			mongo = new Mongo("127.0.0.1",27017);
			DB db = mongo.getDB("travelguide");
			DBCollection weatherCollection = db.getCollection("Weather");
			weatherCollection.insert(game);
		} catch (UnknownHostException | MongoException e) {
			e.printStackTrace();
		}

	}

	public static BasicDBObject getWeather(String place) {
		BasicDBObject weather = new BasicDBObject();
		BasicDBObject daily = new BasicDBObject();
		String url = "http://api.openweathermap.org/data/2.5/forecast/daily?q="+place+"&mode=json&units=imperial&cnt=15&APPID=7199f7ec866fc9f379d86e856f3b239c";
		url = url.replace(" ", "%20");
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url);

		// add request header
		HttpResponse response;
		try {
			response = client.execute(request);
			//System.out.println("Response Code : "+ response.getStatusLine().getStatusCode());

			BufferedReader rd = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));

			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}

			JsonNode listNode = new ObjectMapper().readTree(result.toString());

			JsonNode city = listNode.get("city");
			JsonNode list = listNode.get("list");
			System.out.println("City " + city.get("name"));
			if (list.isArray()) {
				for (final JsonNode objNode : list) {
					JsonNode mainNode = objNode.get("temp");
					JsonNode secNode = objNode.get("weather");

		/*			if (objNode.get("dt").asLong() * 1000 > date1.getTime()
							&& objNode.get("dt").asLong() * 1000 < date2
									.getTime()) {*/
						SimpleDateFormat formatter = new SimpleDateFormat(
								"MM-dd-yyyy");
						formatter.setTimeZone(TimeZone.getTimeZone("EDT"));
						long milliSeconds = (objNode.get("dt").asLong());
						// System.out.println(milliSeconds * 1000);
						BasicDBObject thisday = new BasicDBObject();
					
						Calendar calendar = Calendar.getInstance();
						calendar.setTimeInMillis(milliSeconds * 1000);
						//System.out.println("Date:"+ formatter.format(calendar.getTime()));
						
						//System.out.println("day " + mainNode.get("day"));
						//System.out.println("temp max " + mainNode.get("max"));
						//System.out.println("temp min " + mainNode.get("min"));
						
						thisday.put("Day Temp",mainNode.get("day").toString());
						thisday.put("Max Temp",mainNode.get("max").toString());
						thisday.put("Min Temp",mainNode.get("min").toString());
						for (final JsonNode elemNode : secNode) {
							//System.out.println("Weather Description "
									//+ elemNode.get("description") + "\n");
							thisday.put("Description", elemNode.get("description").toString().replaceAll("\\\"",""));

						}
						daily.put(formatter.format(calendar.getTime()), thisday);
					//}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		weather.put(place, daily);
		weather.put("Date", dateFormat.format(new Date()));
		return weather;
	}
}