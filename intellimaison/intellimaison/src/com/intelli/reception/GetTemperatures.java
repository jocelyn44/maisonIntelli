package com.intelli.reception;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.get.graph.LigneTemperature;
import com.google.appengine.api.datastore.*;

public class GetTemperatures  extends HttpServlet {
	
	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		
		String valeur=req.getParameter("temp");//Integer.parseInt(req.getParameter("temp"));
		String lieu = req.getParameter("lieu");
		String date = req.getParameter("date");
		
		LigneTemperature l = new LigneTemperature(lieu, date, valeur);
		l.store();
		
		/*Entity temp = new Entity("Temperature");
		temp.setProperty("Date", date);
		temp.setProperty("lieu", lieu);
		temp.setProperty("valeur", valeur);
		
		datastore.put(temp);*/
		resp.getWriter().println("");
	}
}
