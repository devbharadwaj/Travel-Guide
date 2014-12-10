package edu.buffalo.cse636.api;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;

import edu.buffalo.cse636.dbconnector.MySqlConnector;

public class DataCollector {
	
	public static void yelpAdd(String city, DBCollection collection) {
		BasicDBObject yelper = YelpApi.yelpLocation(city);
		collection.insert(yelper);
		System.out.println(city + " added to YelpDB.");
	}
	
	public static void weatherAdd(String city, DBCollection collection) {
		BasicDBObject obj = OpenWeather.getWeather(city);
		collection.insert(obj);
	}
	
	public static void freebaseAdd(String city, String mongoDate, DBCollection collection) {
		
		Freebase fb = new Freebase();
		HashMap<String,String> hs = fb.getFreeBaseInformation(city);
		
		System.out.println(city + " spots added.");
		/*for (Map.Entry<String, String> entry : hs.entrySet()) {
		  
			System.out.println(entry);
			System.out.println("\n");
		}*/
		BasicDBObject citySpots = new BasicDBObject();
		citySpots.put(city, new BasicDBObject(hs));
		citySpots.put("Date", mongoDate);
		collection.insert(citySpots);
	}
	
	public static void feedzillaAdd(String city, String mongoDate, DBCollection collection) {
		
		Feedzilla fz=new Feedzilla();
		try {
			fz.getRecentNews(city);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String sentimentOfNews=fz.getSentimentOfNews();//the sentiment of all the summaries and titles of the news in recent 7 days
		HashMap <String,String> keywordsAndSentiments=fz.getKeyWordsAndSentiment();//keywords and corresponding sentiments
		ArrayList<String> NewsKeywords=fz.getNewsKeywords();//just list of keywords
		
		if (sentimentOfNews == null && keywordsAndSentiments == null && NewsKeywords == null) {
			BasicDBObject cityNews = new BasicDBObject();
			cityNews.put(city, new BasicDBObject().put("No News!","neutral"));
			cityNews.put(city, new BasicDBObject().put("Overall","neutral"));
			collection.insert(cityNews);
			return;
		}
		keywordsAndSentiments.put("Overall", sentimentOfNews);
		
		System.out.println("News from " + city  + " is "+sentimentOfNews);
		/*for(int i=0;i<NewsKeywords.size();i++)
		{
			System.out.println(NewsKeywords.get(i));
		}*/
		Iterator Iter= keywordsAndSentiments.entrySet().iterator();
		HashMap<String,String> newsAndSenti = new HashMap<>();
		
		while(Iter.hasNext())
		{
			Map.Entry<String, String> entry=(Map.Entry<String, String>) Iter.next();
			String keyword=entry.getKey();
			keyword = keyword.replaceAll("\\.","");
			String sentiment=entry.getValue();
			//System.out.println(keyword+":");
			//System.out.println(sentiment);
			//System.out.println();
			newsAndSenti.put(keyword, sentiment);
		}
		BasicDBObject cityNews = new BasicDBObject();
		cityNews.put(city, new BasicDBObject(newsAndSenti));
		cityNews.put("Date", mongoDate);
		collection.insert(cityNews);

	}
	
	
	public static void travelwaAdd(String mongoDate, DBCollection collection1, DBCollection collection2) {
		TravelAlertsAndWarning wa = new TravelAlertsAndWarning();
//		TravelWA wa = new TravelWA();
		wa.getTravelWA();
		HashMap<String,HashMap<String,String>> hsAlert = wa.getTravelAlert();
		HashMap<String,HashMap<String,String>> hsWarnning = wa.getTravelWarning();
		
		BasicDBObject travelTa = new BasicDBObject();
		BasicDBObject travelTw = new BasicDBObject();
		BasicDBObject travelTa_sub=new BasicDBObject();
		BasicDBObject travelTw_sub=new BasicDBObject();
		Set<Map.Entry<String,HashMap<String,String>>>ta_Outentry=hsAlert.entrySet(); 
	        for(Iterator<Map.Entry<String,HashMap<String,String>>>it_a=ta_Outentry.iterator();it_a.hasNext();) 
	        { 
	            Map.Entry<String,HashMap<String,String>> out=it_a.next(); 
//	            travelTa.put("Alert:", out.getKey());
                System.out.println("title:"+out.getKey()); 
	            HashMap<String,String>id_desc=out.getValue();
	            Set<Map.Entry<String,String>>innerentry=id_desc.entrySet(); 
	            for(Iterator<Map.Entry<String,String>>it_inner_a=innerentry.iterator();it_inner_a.hasNext();){
	            	Map.Entry<String,String> inner_a=it_inner_a.next(); 
	            	travelTa_sub.put("identifier", inner_a.getKey());
//	            	System.out.println("identifier:"+inner_a.getKey());
//	            	System.out.println("description:"+inner_a.getValue());
	            	travelTa_sub.put("description", inner_a.getValue());
	            }
	            travelTa.put(out.getKey(),travelTa_sub);        
	        } 
	        Set<Map.Entry<String,HashMap<String,String>>>tw_Outentry=hsWarnning.entrySet(); 
	        for(Iterator<Map.Entry<String,HashMap<String,String>>>it_w=tw_Outentry.iterator();it_w.hasNext();) 
	        { 
	            Map.Entry<String,HashMap<String,String>> out=it_w.next(); 
//	            travelTa.put("Alert:", out.getKey());
//                System.out.println("title:"+out.getKey()); 
	            HashMap<String,String>id_desc=out.getValue();
	            Set<Map.Entry<String,String>>innerentry=id_desc.entrySet(); 
	            for(Iterator<Map.Entry<String,String>>it_inner_w=innerentry.iterator();it_inner_w.hasNext();){
	            	Map.Entry<String,String> inner_w=it_inner_w.next(); 
	            	travelTw_sub.put("identifier", inner_w.getKey());
//	            	System.out.println("identifier:"+inner_w.getKey());
//	            	System.out.println("description:"+inner_w.getValue());
	            	travelTw_sub.put("description", inner_w.getValue());
	            }
	            travelTw.put(out.getKey(),travelTw_sub);        
	        } 
//		travelTa.put("alerts",new BasicDBObject(hsAlert));
		travelTa.put("Date", mongoDate);
//		travelTw.put("warnings",new BasicDBObject(hsWarnning));
		travelTw.put("Date", mongoDate);
		collection1.insert(travelTa);
		collection2.insert(travelTw);
	}
	public static void politicalAreaRegionAdd()//call this function will add data into mongoDB
	{
		PoliticalAreaAndRegion par=new PoliticalAreaAndRegion();
		par.getPoliticalAreaAndRegion();
	}
	public static void main(String[] args) {
		
		String city = "Washington DC";
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		Date date = new Date();
		politicalAreaRegionAdd();
		try {
			/*
			 * Add Freebase tourist attractions to MongoDB
			 */
			Mongo mongo = new Mongo("127.0.0.1",27017);
			DB db = mongo.getDB("travelguide");
			DBCollection freebaseCollection = db.getCollection("Freebase");
			DBCollection feedzillaCollection = db.getCollection("Feedzilla");
			DBCollection weatherCollection = db.getCollection("Weather");
			DBCollection yelpCollection = db.getCollection("Yelp");
			DBCollection travelAlertCollection = db.getCollection("travelAlert");	
			DBCollection travelWarningCollection = db.getCollection("travelWarning");	
			
			MySqlConnector sqlconnector = new MySqlConnector("select name from GoogleMaps.location");
			ResultSet result = sqlconnector.readDataBase();
			while (result.next()) {
			
				//freebaseAdd(result.getString("name"), sdf.format(date), freebaseCollection);
				//feedzillaAdd(result.getString("name"), sdf.format(date), feedzillaCollection);
				//weatherAdd(result.getString("name"), weatherCollection);
				//yelpAdd(result.getString("name"), yelpCollection);
				break;
			}
			travelwaAdd(sdf.format(date),travelAlertCollection,travelWarningCollection);
			sqlconnector.close();
			mongo.close();
			
		} catch (Exception e) {
				e.printStackTrace();
		}
	}
}