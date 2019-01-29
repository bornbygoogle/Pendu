package client;

import java.io.FileNotFoundException;

import commun.DemandeServeur;
import commun.Joueur;
import commun.Partie;
import commun.ReponseServeur;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MainGUI extends Application {
	
	private Group groupe;
	
	private Client client;
	
	private Joueur joueur;
	private Partie partie;
	
	private PartieReceive partieReceive;
	
	private boolean connecte;
	
	@Override
	public void start(Stage stage) throws Exception {
		
		/////////////////////////
		// Démarrage du client //
		/////////////////////////

		// Config graphique de l'appli
		this.groupe = new Group();
		Scene scene = new Scene(this.groupe, 500, 925);
		stage.setTitle("Pendu");
		stage.setScene(scene);
		
		// Lancement du réseau -> connexion au port 1025 en local 
		this.client = new Client("127.0.0.1", 1033);
		
		// Déclaration des classes métiers
		this.joueur = new Joueur();
		this.partie = new Partie();
		
		// Verif si joueur est connecté au serveur ou non
		this.connecte = false;
		
		// Affichage de la page de connexion
		this.AfficherConnexion();
		//this.AfficherJeu();
		//this.AfficherMessage("Test", Color.RED);
		
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
		Platform.runLater(() -> {
			this.groupe.getChildren().clear();
			this.groupe.getChildren().add(new Connexion(this));
		});
	}
	
	public void AfficherJeu() {
		Platform.runLater(() -> {
				this.groupe.getChildren().clear();
				this.groupe.getChildren().add(new ClientPanel(this));
		});
	}
	
	public void AfficherMessage(String message, Color couleur) {
		Platform.runLater(new Runnable() {
		    @Override
		    public void run() {
				groupe.getChildren().clear();
				groupe.getChildren().add(new MessagePanel(message, couleur));
		    }
		});
	}
	
	public void ChargerJeu() {
		this.AfficherMessage("En attente de joueurs...", Color.ORANGE);
		/*
		this.lancerEcoutePartie();
		// Ici on va demander le status de la partie
		this.demanderStatusPartie();
		ReponseServeur repServeur = null;
		while(repServeur == null) {
			Object reponse = this.partieReceive.attenteReponse();
			if(reponse != null && reponse instanceof ReponseServeur) {
				ReponseServeur repServ = (ReponseServeur)reponse;
				if(repServ == ReponseServeur.PartieEnAttenteJoueur || repServ == ReponseServeur.PartieEnCours)
					repServeur = repServ;
			}
		}
		
		switch (repServeur) {
			case PartieEnAttenteJoueur:
				this.AfficherMessage("En attente de joueurs...", Color.ORANGE);
				break;
			case PartieEnCours:
				this.AfficherMessage("Une partie est en cours, veuillez patienter...", Color.ORANGE);
				break;
		}
		*/
	}
	
	
	public void lancerEcoutePartie() {
		this.partieReceive = new PartieReceive(this);
	}
	
	public void demanderStatusPartie() {
		this.client.envoyer(DemandeServeur.StatusPartie);
	}
}
