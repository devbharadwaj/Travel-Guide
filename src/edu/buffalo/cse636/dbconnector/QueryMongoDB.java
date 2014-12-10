package edu.buffalo.cse636.dbconnector;

import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

import edu.buffalo.cse636.api.DataCollector;

public class QueryMongoDB {
	Mongo mongo;
	String city;
	Date start;
	Date end;
	DB db;
	final int refreshFreebase = 30;
	final int refreshWeather = 2;
	final int refreshYelp = 10;
	final int refreshNews = 5;
	
	public QueryMongoDB(String city, String start, String end) {
		try {
			this.city = city;
			this.start = new SimpleDateFormat("MM-dd-yyyy", Locale.ENGLISH).parse(start);
			this.end = new SimpleDateFormat("MM-dd-yyyy", Locale.ENGLISH).parse(end);
			
			this.mongo = new Mongo("127.0.0.1",27017);
			this.db = mongo.getDB("travelguide");

		} catch (UnknownHostException | MongoException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}
	public static LocalDate getDate(String timestamp) {
		try {
			return new LocalDate(new SimpleDateFormat("MM-dd-yyyy", Locale.ENGLISH).parse(timestamp));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new LocalDate();
	}
	public String getTouristSpots() {
		DBCollection collection = db.getCollection("Freebase");
		DBObject whereQuery = new BasicDBObject(city, new BasicDBObject("$exists",true));
		DBCursor cursor = collection.find(whereQuery);
		StringBuffer jsonString = new StringBuffer();
		while(cursor.hasNext()) {
			jsonString.append(cursor.next());
		}
		JSONObject jsonObj = (JSONObject) JSONValue.parse(jsonString.toString());
		if (jsonObj == null) {
			System.out.println("No output");
			return null;
		}
		LocalDate timestamp = getDate(jsonObj.get("Date").toString());
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		Date today = new Date();
		if(Days.daysBetween(timestamp, new LocalDate()).getDays() > refreshFreebase) {
			DataCollector.freebaseAdd(city, sdf.format(today), collection);
			DBObject deleteQuery = new BasicDBObject(city, new BasicDBObject("$exists",true));
		    deleteQuery.put("Date",jsonObj.get("Date").toString());
			collection.remove(deleteQuery);
			DBCursor newCursor = collection.find(whereQuery);
			StringBuffer updateJson = new StringBuffer();
			while(newCursor.hasNext()) {
				updateJson.append(newCursor.next());
			}
			return updateJson.toString();
		}
		return jsonString.toString();
	}
	public String getYelp() {
		DBCollection collection = db.getCollection("Yelp");
		DBObject whereQuery = new BasicDBObject(city, new BasicDBObject("$exists",true));
		DBCursor cursor = collection.find(whereQuery);
		StringBuffer jsonString = new StringBuffer();
		while(cursor.hasNext()) {
			jsonString.append(cursor.next());
		}
		JSONObject jsonObj = (JSONObject) JSONValue.parse(jsonString.toString());
		if (jsonObj == null) {
			System.out.println("No output");
			return null;
		}
		LocalDate timestamp = getDate(jsonObj.get("Date").toString());
		if(Days.daysBetween(timestamp, new LocalDate()).getDays() > refreshYelp) {
			DataCollector.yelpAdd(city, collection);
			DBObject deleteQuery = new BasicDBObject(city, new BasicDBObject("$exists",true));
		    deleteQuery.put("Date",jsonObj.get("Date").toString());
			collection.remove(deleteQuery);
			DBCursor newCursor = collection.find(whereQuery);
			StringBuffer updateJson = new StringBuffer();
			while(newCursor.hasNext()) {
				updateJson.append(newCursor.next());
			}
			return updateJson.toString();
		}
		return jsonString.toString();

	}
	public String getWeather() {
		DBCollection collection = db.getCollection("Weather");
		DBObject whereQuery = new BasicDBObject(city, new BasicDBObject("$exists",true));
		DBCursor cursor = collection.find(whereQuery);
		StringBuffer jsonString = new StringBuffer();
		while(cursor.hasNext()) {
			jsonString.append(cursor.next());
		}
		JSONObject jsonObj = (JSONObject) JSONValue.parse(jsonString.toString());
		if (jsonObj == null) {
			System.out.println("No output");
			return null;
		}
		LocalDate timestamp = getDate(jsonObj.get("Date").toString());
		if(Days.daysBetween(timestamp, new LocalDate()).getDays() > refreshWeather) {
			DataCollector.weatherAdd(city, collection);
			DBObject deleteQuery = new BasicDBObject(city, new BasicDBObject("$exists",true));
		    deleteQuery.put("Date",jsonObj.get("Date").toString());
			collection.remove(deleteQuery);
			DBCursor newCursor = collection.find(whereQuery);
			StringBuffer updateJson = new StringBuffer();
			while(newCursor.hasNext()) {
				updateJson.append(newCursor.next());
			}
			return updateJson.toString();
		}
		return jsonString.toString();

	}
	public String getNews() {
		DBCollection collection = db.getCollection("Feedzilla");
		DBObject whereQuery = new BasicDBObject(city, new BasicDBObject("$exists",true));
		DBCursor cursor = collection.find(whereQuery);
		StringBuffer jsonString = new StringBuffer();
		while(cursor.hasNext()) {
			jsonString.append(cursor.next());
		}
		JSONObject jsonObj = (JSONObject) JSONValue.parse(jsonString.toString());
		if (jsonObj == null) {
			System.out.println("No output");
			return null;
		}
		LocalDate timestamp = getDate(jsonObj.get("Date").toString());
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		Date today = new Date();
		if(Days.daysBetween(timestamp, new LocalDate()).getDays() > refreshNews) {
			DataCollector.feedzillaAdd(city, sdf.format(today), collection);
			DBObject deleteQuery = new BasicDBObject(city, new BasicDBObject("$exists",true));
		    deleteQuery.put("Date",jsonObj.get("Date").toString());
			collection.remove(deleteQuery);
			DBCursor newCursor = collection.find(whereQuery);
			StringBuffer updateJson = new StringBuffer();
			while(newCursor.hasNext()) {
				updateJson.append(newCursor.next());
			}
			return updateJson.toString();
		}
		return jsonString.toString();

	}
	public void closeDB() {
		mongo.close();
	}
}
