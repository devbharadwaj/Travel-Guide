package edu.buffalo.cse636.api;
import java.util.*;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
/*
 * HashMap TravelAlerts contains alert data in a format <title,<identifier,description>>
 * so is the HashMap TravelWarnings
 * */
public class TravelAlertsAndWarning {
	public String url_travelTAs="http://travel.state.gov/_res/rss/TAs.xml";
	public String url_travelTWs="http://travel.state.gov/_res/rss/TWs.xml";
	public HashMap <String,HashMap<String,String>> TravelAlerts=new HashMap <String,HashMap<String,String>>();
	public HashMap <String,HashMap<String,String>> TravelWarnings=new HashMap <String,HashMap<String,String>>();
	public void getTravelWA()
	{
		try {
			convertStringToDocument(url_travelTWs);
			convertStringToDocument(url_travelTAs);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public HashMap <String,HashMap<String,String>> getTravelAlert()
	{
		return TravelAlerts;
	}
	public HashMap <String,HashMap<String,String>> getTravelWarning()
	{
		return TravelWarnings;
	}
	private void convertStringToDocument(String urlString) {
		
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
	    DocumentBuilder builder;  
	    try 
	    {  
	        builder = factory.newDocumentBuilder();
	        Document doc=builder.parse(urlString);
	        Element rootElement=doc.getDocumentElement();
	        NodeList list = rootElement.getElementsByTagName("item");
	        //int k=list.getLength();
	        if ((list != null) && (list.getLength() > 0)) {
	            for(int i=0; i<list.getLength();i++)
	            {
	            	Element itm=(Element)list.item(i);
	            	NodeList titles=itm.getElementsByTagName("title");
	            	NodeList ids=itm.getElementsByTagName("dc:identifier");
	            	NodeList des=itm.getElementsByTagName("description");
	            	if(urlString.equals(url_travelTWs)){
	            		putValues(titles,ids,des,"W"); 
	            	}
	            	else
	            	{
	            		putValues(titles,ids,des,"A"); 
	            	}
	            }
	            }
	    } catch (Exception e) {  
	        e.printStackTrace();  
	    } 
	}
	public void putValues(NodeList titles, NodeList ids, NodeList description,String tag)
	{
		String title="";
		String sub_id="";
		String desc="";
		NodeList sub_title = titles.item(0).getChildNodes();
	    if (sub_title != null && sub_title.getLength() > 0) {
	    	title=sub_title.item(0).getNodeValue();
//	    	System.out.println(title);
	     }
	    NodeList sub_ids = ids.item(0).getChildNodes();
	    if (sub_ids != null && sub_ids.getLength() > 0) {
	    	sub_id=sub_ids.item(0).getNodeValue().trim();
	    	if(sub_id==null)
	    	{
	    		sub_id="sorry no identifiers";
	    	}
	     } 
	    
		NodeList sub_description = description.item(0).getChildNodes();
		  if (sub_description != null && sub_description.getLength() > 0) {
		    String sub_des=sub_description.item(0).getNodeValue();
		    org.jsoup.nodes.Document doc = Jsoup.parse(sub_des);
		    Elements desc_p=  doc.select("p");
		    desc=desc_p.text();
//		    System.out.println(desc);
		  }
		  HashMap <String, String> id_desc=new HashMap<String,String>();
		  id_desc.put(sub_id, desc);
		  if(tag.equals("A")){
		  TravelAlerts.put(title, id_desc);
		  }
		  else
		  {
			TravelWarnings.put(title, id_desc);
		  }
	}
	
	public static void main(String[] args) throws Exception
	{
		TravelAlertsAndWarning ta=new TravelAlertsAndWarning();
		ta.getTravelWA();
	}
}