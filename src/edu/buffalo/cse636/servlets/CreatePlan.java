package edu.buffalo.cse636.servlets;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.Days;
import org.joda.time.LocalDate;

import edu.buffalo.cse636.dbconnector.MySqlConnector;
import edu.buffalo.cse636.dbconnector.QueryMongoDB;

/**
 * Servlet implementation class CreatePlan
 */
@WebServlet("/CreatePlan")
public class CreatePlan extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreatePlan() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cityName = request.getParameter("city");
		String fromDate = request.getParameter("from");
		String toDate = request.getParameter("to");
		String numberOfSpots = request.getParameter("numspots");
		String latitude = "42.9047";
		String longitude = "78.8494";
		String country = "United States";
		fromDate = fromDate.substring(5) + "-" + fromDate.substring(0,4);
		toDate = toDate.substring(5) + "-" + toDate.substring(0,4);
		LocalDate from = QueryMongoDB.getDate(fromDate);
		LocalDate to = QueryMongoDB.getDate(toDate);
		int days = Days.daysBetween(from, to).getDays() + 1;
		
		QueryMongoDB mongoQuery = new QueryMongoDB(cityName,fromDate,toDate);
		String touristSpots = mongoQuery.getTouristSpots();
		String localNews = mongoQuery.getNews();
		String yelpSpots = mongoQuery.getYelp();
		String weather = mongoQuery.getWeather();
		mongoQuery.closeDB(); 
		mongoQuery = null;
		
		MySqlConnector sqlconnector = new MySqlConnector("select lat,lng,country from GoogleMaps.location where name = '"+cityName+"'");
		ResultSet result = sqlconnector.readDataBase();
		try {
			while (result.next()) {
				latitude = result.getString("lat");
				longitude = result.getString("lng");
				country = result.getString("country");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		request.setAttribute("city", cityName);
		request.setAttribute("from", fromDate);
		request.setAttribute("to", toDate);
		request.setAttribute("days", String.valueOf(days));
		request.setAttribute("numOfSpots", numberOfSpots);
		request.setAttribute("freebase", touristSpots);
		request.setAttribute("yelpSpots", yelpSpots);
		request.setAttribute("news", localNews);
		request.setAttribute("weather", weather);
		request.setAttribute("latitude", latitude);
		request.setAttribute("longitude", longitude);
		request.setAttribute("country", country.substring(0,country.length()-1));
	    RequestDispatcher rd = getServletContext()
                .getRequestDispatcher("/TravelPlan.jsp");
	    rd.forward(request, response);
	}


}
