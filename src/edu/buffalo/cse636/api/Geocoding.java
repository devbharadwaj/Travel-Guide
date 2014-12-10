package edu.buffalo.cse636.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONObject;

import edu.buffalo.cse636.dbconnector.MySqlConnector;

public class Geocoding {

	
	public static void main(String[] args) throws SQLException, IOException {
	
		String preQuery = "https://maps.googleapis.com/maps/api/geocode/json?";
		String city = "address=";
		String apiKey = "&key=AIzaSyBK00AndpICwxFWMhPxp-cszUHrDALvF64";
		
		MySqlConnector connector = new MySqlConnector("select name from GoogleMaps.location");
		ResultSet result = connector.readDataBase();
		Connection conn = connector.connect;
		while (result.next()) {
		
			String cityName = result.getString("name");
			String query = preQuery+city+cityName.replaceAll(" ", "%20")+apiKey;
			URLConnection urlConnection_politicalArea;
			URL searchCityIdUrl=new URL(query);
			urlConnection_politicalArea=searchCityIdUrl.openConnection();
			urlConnection_politicalArea.addRequestProperty("User-Agent", 
				        "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
			urlConnection_politicalArea.connect();
			
			BufferedReader buffer = new BufferedReader(
					new InputStreamReader(urlConnection_politicalArea.getInputStream()));
	        String line;
	        StringBuffer sb=new StringBuffer();
	        while ((line=buffer.readLine())!=null)
	        {
	        	sb.append(line);
	        }
	        
	        JSONObject jsonAddress = new JSONObject(sb.toString());
	        double lat = (double)jsonAddress.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").get("lat"); 
	        double lng = (double)jsonAddress.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").get("lng");
	        String updateQuery = "update GoogleMaps.location set lat = ?,lng = ? where name ='"+cityName+"'";
	        PreparedStatement preparedStmt = conn.prepareStatement(updateQuery);
	        preparedStmt.setString(1, String.valueOf(lat));
	        preparedStmt.setString(2, String.valueOf(lng));
	   
	        // execute the java prepared statement
	        preparedStmt.executeUpdate();
	        
		}
		connector.close();
	}
	
}
