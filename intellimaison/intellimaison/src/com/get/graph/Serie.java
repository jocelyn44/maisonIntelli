package com.get.graph;
import java.util.*;

import javax.jdo.PersistenceManager;

import com.google.appengine.api.datastore.*;

import javax.jdo.Query;;

public class Serie {
	public String nomSerie;
	public List<String> valeurs;
	
	PersistenceManager pm = PMF.get().getPersistenceManager();
	
	public Serie(String pnom){
		nomSerie=pnom;
		valeurs = new LinkedList<String>();
	}
	
	public List<LigneTemperature> generateSerie(){
		List<LigneTemperature> lignes;
		Query q = pm.newQuery(LigneTemperature.class);
		q.setOrdering("date asc");
		//Query q = pm.newQuery("select from LigneTemperature order by date asc");
		lignes=(List<LigneTemperature>) q.execute();
		
		String str="";
		str="{name: '"+nomSerie+"',data: [";
		for(int i=0;i<valeurs.size();i++){
			str+=valeurs.get(i)+",";
		}
		str=str.substring(0, str.length()-1);
		str+="]}";
		return lignes;
	}
	
	public String toString(){
		String str="{name: '"+nomSerie+"',data: [";
		for(int i=0;i<valeurs.size();i++){
			str+=valeurs.get(i);
		}
		return str;
	}
}
