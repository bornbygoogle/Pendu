package server;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import commun.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainServer extends Application
{
	private Group groupe;

	private Server server;

	private Joueur joueur;
	private Partie partie;

	private Connection connection = null;
	private List<ConnectedClient> clients;

	private List<Theme> themes = new ArrayList<Theme>();
	private List<Joueur> joueurs = new ArrayList<Joueur>();

	@Override
	public void start(Stage stage) throws Exception {

		// Recuperation des donnees necessaires de la BDD
		connection = ConnectionBDD.getInstance();
		this.themes = Methods.getListThemes(connection);
		this.joueurs = Methods.getListJoueurs(connection);

		if (connection != null)
			connection.close();

		/*List<Joueur> nouveaux = new ArrayList();
		Joueur newJ = new Joueur();
		newJ.setPseudo("tony7");
		newJ.setPass("tony7");
		newJ.setDateDernierCo(null);
		newJ.setNbScore(0);
		newJ.setNbParties(0);
		nouveaux.add(newJ);
		Joueur newJ2 = new Joueur();
		newJ2.setPseudo("tony8");
		newJ2.setPass("tony8");
		newJ2.setDateDernierCo(null);
		newJ2.setNbScore(0);
		newJ2.setNbParties(0);
		nouveaux.add(newJ2);
		Methods.updateJoueur(connection, nouveaux);
		System.out.println(Methods.getMot(themes.get(1).getMots()));
/*
		Joueur unJoueur = new Joueur();
		unJoueur.setPseudo("dylan");

		for(Joueur j : joueurs)
			if (j.getPseudo().equals(unJoueur.getPseudo()))
				// envoie status to client
				System.out.println("true");
				*/

		///////////////////////////
		// Démarrage du serveur //
		/////////////////////////

		// Config graphique de l'appli
		this.groupe = new Group();
		Scene scene = new Scene(this.groupe, 500, 925);
		stage.setTitle("GUI Serveur");
		stage.setScene(scene);

		// Instancier une connection
		this.server = new Server(1025);

		
		// Déclaration des classes métiers
		//this.joueur = new Joueur();
		//this.partie = new Partie();
		
		// Verif si joueur est connecté au serveur ou non
		//this.connecte = false;
		
		// Affichage de la page de connexion
		this.serverGUI();

		// Affichage
		stage.show();

		// Exit de l'application
		stage.setOnHidden(e -> {
			this.shutdown();
			Platform.exit();
		});
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void serverGUI() {
		this.groupe.getChildren().add(new AjoutJoueur(this.server));
	}

    public void shutdown() {
        // cleanup code here...
		System.out.println("Stop");
		if (this.server != null)
			System.out.println(this.server.getPort());
		this.server.stopServerRunning();
	}

	public Server getServer() {
		return server;
	}

	public Joueur getJoueur() {
		return joueur;
	}

	public void setJoueur(Joueur unJoueur) {
		this.joueur = unJoueur;
	}
}