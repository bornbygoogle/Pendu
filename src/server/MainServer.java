package server;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import commun.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainServer extends Application
{
	// Eléments JAVAFX
	private Group groupe;

	// Eléments Réseau
	private Server server;
	private List<ConnectedClient> clients;

	// Eléments jeu
	private Partie partie;
	private ReponseServeur statusPartie;

	// Eléments BDD
	private Connection connection = null;
	private List<Theme> themes = new ArrayList<Theme>();
	private List<Joueur> joueurs = new ArrayList<Joueur>();
	private List<Joueur> newJoueurs = new ArrayList<Joueur>();

	@Override
	public void start(Stage stage) throws Exception 
	{
		System.out.println(Utils.getCurrentTimeUsingCalendar());
		//////////////////////
		// Récupération BDD //
		//////////////////////

		// Recuperation des donnees necessaires de la BDD
		this.connection = ConnectionBDD.getInstance();
		this.themes = Methods.getListThemes(connection);
		this.joueurs = Methods.getListJoueurs(connection);
		
		this.afficherJoueursEnregistres();
		
		/////////////////////////
		// Démarrage du réseau //
		/////////////////////////

		this.server = new Server(this, 1033);

		///////////////////////
		// Configuration jeu //
		///////////////////////
		
		// Définition du status de la partie à en attente joueur au lancement du serveur
		this.statusPartie = ReponseServeur.PartieEnAttenteJoueur;


		//////////////////////
		// Interface JAVAFX //
		//////////////////////

		// Config graphique de l'appli
		this.groupe = new Group();
		Scene scene = new Scene(this.groupe, 500, 925);

		stage.setTitle("Pendu serveur");
		stage.setScene(scene);
		
		// Affichage de la page de connexion
		this.afficherPanelAjouterJoueur();

		// Affichage
		stage.show();

		///////////////////
		// Arrêt serveur //
		///////////////////

		stage.setOnCloseRequest(e -> 
		{
			// On enregistre les nouveaux joueurs en BDD
			try {
				System.out.println("Enregistrement des joueurs dans la BDD :");
				for(Joueur j : this.newJoueurs)
					System.out.println("\t- " + j.getPseudo());
				
				Methods.updateJoueur(connection, newJoueurs);

				if (connection != null)
					connection.close();

			} catch (SQLException e2) {
				e2.printStackTrace();
			}

			// On stop le réseau
			this.server.stopServerRunning();
			Platform.exit();
			System.exit(0);
		});
	}

	public static void main(String[] args) {
		launch(args);
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






	public void ajouterJoueur(Joueur unJoueur) {
		this.joueurs.add(unJoueur);
		this.newJoueurs.add(unJoueur);
	}





	

	public void afficherPanelAjouterJoueur() {
		this.groupe.getChildren().add(new AjoutJoueur(this));
	}

	public void afficherJoueursEnregistres() {
		System.out.println("Liste des joueurs enregistrés :");
		for(Joueur j : this.joueurs) {
			System.out.println("\t- " + j.getPseudo());
		}
	}





	public void lancementJeu() {
		// Si une partie n'est pas lancée
		if(this.statusPartie == ReponseServeur.PartieEnAttenteJoueur && this.partie == null) {
			// Si il y a au moins 2 clients connectés au serveur
			if(this.clients.size() >= 2) {
				int cptAuthentifier = 0;
				for(ConnectedClient client : this.clients) {
					if(client.getJoueur() != null)
					cptAuthentifier++;
				}
				// Si au moins 2 clients sont authentifiés
				if(cptAuthentifier >= 2) {
					// On lance une nouvelle partie
					this.definirPartie();
					this.statusPartie = ReponseServeur.PartieEnCours;
					// On envoie la partie aux joueur concernés
					this.envoyerPartie();
				}
			}
		}
	}

	private void definirPartie() {
		// On crée une nouvelle partie
		Partie nouvellePartie = new Partie();
		// On ajoute les joueurs authentifiés à la partie
		HashMap<Joueur, StatusJoueur> lesParticipants = new HashMap<Joueur, StatusJoueur>();
		for(ConnectedClient client : this.clients) {
			if(client.getJoueur() != null)
				lesParticipants.put(client.getJoueur(), StatusJoueur.EnJeu);
		}
		nouvellePartie.setParticipants(lesParticipants);
		// On définit le mot à rechercher aléatoirement
		Random rand = new Random();
		Theme randomTheme = this.themes.get(rand.nextInt(this.themes.size()));
		Mot randomMot = randomTheme.getMots().get(rand.nextInt(randomTheme.getMots().size()));
		nouvellePartie.setMot(randomMot);
		// On définit la partie
		this.partie = nouvellePartie;
	}

	private void envoyerPartie() {
		for(Joueur j : this.partie.getParticipants().keySet()) {
			for(ConnectedClient client : this.clients) {
				if(client.getJoueur().equals(j)) {
					client.envoyer(this.partie);
				}
			}
		}
	}
}