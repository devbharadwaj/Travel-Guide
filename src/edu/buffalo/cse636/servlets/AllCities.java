package edu.buffalo.cse636.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;

import edu.buffalo.cse636.dbconnector.MySqlConnector;

/**	
 * Servlet implementation class AllCities
 */
@WebServlet("/AllCities")
public class AllCities extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AllCities() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("application/json");
		try {
			JSONArray cities = new JSONArray();
			MySqlConnector sqlconnector = new MySqlConnector("select name from GoogleMaps.location");
		
			ResultSet result = sqlconnector.readDataBase();
			while (result.next()) {
				cities.add(result.getString("name"));
			}
			PrintWriter out = response.getWriter();
			cities.writeJSONString(out);
			sqlconnector.close();

		} catch (Exception e) { e.printStackTrace();}		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
