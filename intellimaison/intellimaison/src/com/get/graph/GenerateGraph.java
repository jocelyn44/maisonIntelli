package com.get.graph;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GenerateGraph extends HttpServlet {
	
	PersistenceManager pm = PMF.get().getPersistenceManager();
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		
		String ID="container";
		String titre="titre";
		String sousTitre="sousTitre";
		String axeX=generereLegendeX();//"['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun','Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']";
		String legendeY="Temperature (°C)";
		
		List<LigneTemperature> lignes =this.getTemperatures();
		
		
		String str = "var charts; $(function () {charts=$('#"+ID+"').highcharts({title: {text: '"+titre+"',x: -20},"+
				"subtitle: {text: '"+sousTitre+"',x: -20},"+
				"xAxis: {categories: "+axeX+"},"+
				"yAxis: {title: {text: '"+legendeY+"'},"+
				"plotLines: [{value: 0,width: 1,color: '#808080'}]},"+
				"tooltip: {valueSuffix: '°C'},"+
				"legend: {layout: 'vertical',align: 'right',verticalAlign: 'middle',borderWidth: 0},"+
				"series: [{name: 'Paris',data: [9.0, 15.9, 2.5, 19.5, 28.2, 29.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6]},{name: 'Tokyo',data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6]}, {name: 'New York',data: [-0.2, 0.8, 5.7, 11.3, 17.0, 22.0, 24.8, 24.1, 20.1, 14.1, 8.6, 2.5]}, {name: 'Berlin',data: [-0.9, 0.6, 3.5, 8.4, 13.5, 17.0, 18.6, 17.9, 14.3, 9.0, 3.9, 1.0]}, {name: 'London',data: [3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.8]}]});});";
		resp.getWriter().println(str);
				
	}
	
	/** récupère toutes les dates en base et génère l'axe x**/
	public String generereLegendeX(){
		Query q = pm.newQuery(LigneTemperature.class);
		q.setOrdering("date asc");
		List<LigneTemperature> l = (List<LigneTemperature>) q.execute();
		String str="['";
		for(int i=0;i<l.size();i++){
			String current = l.get(i).date+"','";
			if(!l.get(i).date.equals(current)){
				str+=current;
			}
		}
		str=str.substring(0,str.length()-2)+"]";
		return str;
	}
	
	/** récupère toutes les températures en base **/
	public List<LigneTemperature> getTemperatures(){
		List<LigneTemperature> lignes;
		Query q = pm.newQuery(LigneTemperature.class);
		q.setOrdering("date asc");
		//Query q = pm.newQuery("select from LigneTemperature order by date asc");
		lignes=(List<LigneTemperature>) q.execute();
		return lignes;
	}
	
	/** génère les series de données à envoyer en JS**/
	public String generateSeries(List<LigneTemperature> datas){
		String str="";
		List<Serie> series = new LinkedList<Serie>();
		for(int i=0;i<datas.size();i++){
			LigneTemperature ligne=datas.get(i);
			series=insertSeries(series, ligne);
		}
		return str;
	}
	
	/** insère la donnée dans la liste des series de données**/
	public List<Serie> insertSeries(List<Serie> series, LigneTemperature ligne){
		for(int i=0;i<series.size();i++){
			if(series.get(i).nomSerie.equals(ligne.lieu)){
				series.get(i).valeurs.add(ligne.valeur);
				return series;
			}
		}
		Serie s= new Serie(ligne.lieu);
		s.valeurs.add(ligne.valeur);
		series.add(s);
		return series;
	}
	
}
