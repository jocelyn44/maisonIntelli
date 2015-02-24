package com.get.graph;

import javax.jdo.PersistenceManager;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class LigneTemperature {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	
	@Persistent
	public String lieu;
	
	@Persistent
	public String date;
	
	@Persistent
	public String valeur;
	
	public LigneTemperature(String plieu, String pdate, String pvaleur){
		date=pdate;
		lieu=plieu;
		valeur=pvaleur;
	}
	
	public void store(){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try{
			pm.makePersistent(this);
		} finally {
            pm.close();
        }	
	}
}
