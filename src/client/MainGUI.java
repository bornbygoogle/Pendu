package client;

import java.io.FileNotFoundException;
import java.util.HashMap;

import commun.Joueur;
import commun.Mot;
import commun.Partie;
import commun.StatusJoueur;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainGUI extends Application {
	
	private Group groupe;
	
	private Client client;
	
	private Joueur joueur;
	private Partie partie;
	
	private boolean connecte;
	
	@Override
	public void start(Stage stage) throws Exception {
		
		/////////////////////////
		// Démarrage du client //
		/////////////////////////

		// Config graphique de l'appli
		this.groupe = new Group();
		Scene scene = new Scene(this.groupe, 725, 925);
		stage.setTitle("Pendu");
		stage.setScene(scene);
		
		// Lancement du réseau -> connexion au port 1025 en local 
		this.client = new Client("127.0.0.1", 1025);
		
		// Déclaration des classes métiers
		this.joueur = new Joueur();
		this.partie = new Partie();
		
		Mot mot = new Mot();
		mot.setMot("ESCALIER");
		partie.setMot(mot);
		Joueur joueur1 = new Joueur();
		joueur1.setPseudo("Joueur 1");
		Joueur joueur2 = new Joueur();
		joueur2.setPseudo("Joueur 2");
		HashMap<Joueur, StatusJoueur> participants = new HashMap<Joueur, StatusJoueur>();
		participants.put(joueur1, StatusJoueur.EnJeu);
		participants.put(joueur2, StatusJoueur.EnJeu);
		partie.setParticipants(participants);
		
		// Verif si joueur est connecté au serveur ou non
		this.connecte = false;
		
		// Affichage de la page de connexion
		this.AfficherJeu();
		
		// Affichage
		stage.show();
	}
	public static void main(String[] args) {
		launch(args);
	}

	public Client getClient() {
		return client;
	}

	public Joueur getJoueur() {
		return joueur;
	}
	
	public Partie getPartie() {
		return partie;
	}

	public void setJoueur(Joueur unJoueur) {
		this.joueur = unJoueur;
	}
	
	public boolean isConnecte() {
		return connecte;
	}
	
	public void setConnecte(boolean connecte) {
		this.connecte = connecte;
	}
	
	public void AfficherConnexion() {
		this.groupe.getChildren().add(new Connexion(this));
	}
	
	public void AfficherJeu() {
		try {
			this.groupe.getChildren().add(new ClientPanel(this));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
