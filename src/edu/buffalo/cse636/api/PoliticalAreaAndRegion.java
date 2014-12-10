package edu.buffalo.cse636.api;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import com.google.gson.*;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;

public class PoliticalAreaAndRegion {
	private String url_pA="http://www.state.gov/developer/geoPoliticalArea.json";
	private String url_pR="http://www.state.gov/developer/geoPoliticalRegion.json";
    public void getPoliticalAreaAndRegion()
    {
    	try {
    		Mongo mongo = new Mongo("127.0.0.1",27017);
  		    DB db = mongo.getDB("travelguide");
			getPoliticalArea(mongo, db);
			getPoliticalRegion(mongo, db);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
	public void getPoliticalArea(Mongo pa_mongo,DB pa_db) throws IOException
	{
		URLConnection urlConnection_politicalArea;
		URL searchCityIdUrl=new URL(url_pA);
		urlConnection_politicalArea=searchCityIdUrl.openConnection();
		urlConnection_politicalArea.addRequestProperty("User-Agent", 
			        "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
		urlConnection_politicalArea.connect();
		
		BufferedReader buffer = new BufferedReader(
				new InputStreamReader(urlConnection_politicalArea.getInputStream()));
        String politicalArea;
        StringBuffer sb=new StringBuffer();
        while ((politicalArea=buffer.readLine())!=null)
        {
        	sb.append(politicalArea);
        }
        politicalArea=sb.toString();
//        politicalArea=politicalArea.replaceAll(".", "");
//        System.out.println(politicalArea);
        DBCollection collection_pa = pa_db.getCollection("PoliticalArea");
        BasicDBObject country_pa = new BasicDBObject();
		BasicDBObject country_pa_sub=new BasicDBObject();
        JsonParser jsParser = new JsonParser();
        JsonArray  values = (JsonArray)jsParser.parse(politicalArea);
        for(int i=0;i<values.size();i++) {
            JsonObject data = values.get(i).getAsJsonObject();
            String name = data.get("Name").getAsString();
//            if(name.contains("."));
//            {
              name=name.replaceAll("\\.", "");
//            }
            String tag=data.get("Tag").getAsString();
            JsonArray regionTags=data.get("regionTags").getAsJsonArray();
            String regionTags_s="";
            for (int j=0;j<regionTags.size();j++)
            {
            	if(j!=regionTags.size()-1)
            	{
            		regionTags_s= regionTags_s+regionTags.get(j).getAsString()+",";
            		}
            	else{
            	regionTags_s= regionTags_s+regionTags.get(j).getAsString();
            	}
            }
            country_pa_sub.put("Tag", tag);
            country_pa_sub.put("regionTags", regionTags_s);
            country_pa.put(name, country_pa_sub);
//            System.out.println("Name: "+name+"Tag: "+tag+"regionTags"+regionTags_s);                  
         }  
        collection_pa.insert(country_pa);
//        BasicDBList data = (BasicDBList) JSON.parse(politicalArea);
//        for(int i=0; i < data.size(); i++){
//        	collection.insert((DBObject) data.get(i));
//        }
//        DBCursor cursorDoc = collection.find();
//		while (cursorDoc.hasNext()) {
////			System.out.println(cursorDoc.next());
//		}

//		System.out.println("Done");
	
	}
	public void getPoliticalRegion(Mongo pr_mongo,DB pr_db) throws IOException
	{
		URLConnection urlConnection_politicalRegion;
		URL searchCityIdUrl=new URL(url_pA);
		urlConnection_politicalRegion=searchCityIdUrl.openConnection();
		urlConnection_politicalRegion.addRequestProperty("User-Agent", 
			        "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
		urlConnection_politicalRegion.connect();
		
		BufferedReader buffer = new BufferedReader(
				new InputStreamReader(urlConnection_politicalRegion.getInputStream()));
        String politicalRegion;
        StringBuffer sb=new StringBuffer();
        while ((politicalRegion=buffer.readLine())!=null)
        {
        	sb.append(politicalRegion);
        }
        
        politicalRegion=sb.toString();
//        System.out.println(politicalRegion);
        DBCollection collection_pr = pr_db.getCollection("PoliticalRegion");
        BasicDBObject country_pr = new BasicDBObject();
		BasicDBObject country_pr_sub=new BasicDBObject();
        JsonParser jsParser = new JsonParser();
        JsonArray  values = (JsonArray)jsParser.parse(politicalRegion);
        for(int i=0;i<values.size();i++) {
            JsonObject data = values.get(i).getAsJsonObject();
            String name = data.get("Name").getAsString();   
            String tag=data.get("Tag").getAsString();
            country_pr_sub.put("Name", name);
            country_pr.put(tag, country_pr_sub);           
         }  
        collection_pr.insert(country_pr);
//        DBCollection collection = pr_db.getCollection("PoliticalAreaRegion");
//        BasicDBList data = (BasicDBList) JSON.parse(politicalRegion);
//      for(int i=0; i < data.size(); i++){
//      	collection.insert((DBObject) data.get(i));
//      }
//      DBCursor cursorDoc = collection.find();
//		while (cursorDoc.hasNext()) {
////			System.out.println(cursorDoc.next());
//		}
//		return politicalRegion;
	}
}