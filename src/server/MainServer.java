package server;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import commum.Joueur;

public class MainServer {
	
	private List<Joueur> joueurs;

	public void main(String[] args) {
		try {
			// R�cup�ration des param�tres BDD
			Properties propBDD = new Properties();
			FileInputStream inputBDD = new FileInputStream("config.properties");
			propBDD.load(inputBDD);
			
			Bdd bdd = new Bdd(propBDD.getProperty("url"), propBDD.getProperty("login"), propBDD.getProperty("pass"));
			
			System.out.println("R�cup�ration des joueurs inscrit...");
			
			this.joueurs = bdd.getJoueurs();
		} catch(IOException e) {
			System.out.println("Erreur initilisation appli" + e.getMessage());
		}
	}
}
