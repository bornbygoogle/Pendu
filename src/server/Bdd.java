




package server;

import java.util.ArrayList;
import java.util.List;

import commun.Joueur;

public class Bdd {

	private String url, login, pass;
	
	public Bdd(String url, String login, String pass) throws ClassNotFoundException {
		this.url = url;
		this.login = login;
		this.pass = pass;
		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		
		
	}
	
	public List<Joueur> getJoueurs() {
		List<Joueur> lesJoueurs = new ArrayList<Joueur>();
		
		
		
		return lesJoueurs;
	}
}