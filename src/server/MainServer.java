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
	private ReponseServeur statusPartie;

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
		
		for(Joueur j : this.joueurs) {
			System.out.println(j.getPseudo() + " : " + j.getPass());
		}
		
		this.statusPartie = ReponseServeur.PartieEnAttenteJoueur;
		
		///////////////////////////
		// Démarrage du serveur //
		/////////////////////////

		// Config graphique de l'appli
		this.groupe = new Group();
		Scene scene = new Scene(this.groupe, 500, 925);

		stage.setTitle("GUI Serveur");
		stage.setScene(scene);

		// Instancier une connection

		
		// Déclaration des classes métiers
		//this.joueur = new Joueur();
		//this.partie = new Partie();
		
		// Verif si joueur est connecté au serveur ou non
		//this.connecte = false;
		this.server = new Server(this, 1033);
		
		// Affichage de la page de connexion
		this.serverGUI();

		// Affichage
		stage.show();

		// Exit de l'application
		stage.setOnCloseRequest(e -> 
		{
			this.shutdown();
			Platform.exit();
			System.exit(0);
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
	
	public ReponseServeur getStatusPartie() {
		return this.statusPartie;
	}
	
	public List<Joueur> getListeJoueurs() {
		return this.joueurs;
	}
}