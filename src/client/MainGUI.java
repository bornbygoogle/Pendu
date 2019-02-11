package client;

import java.util.HashMap;

import commun.DemandeServeur;
import commun.Joueur;
import commun.Mot;
import commun.Partie;

import commun.ReponseServeur;
import commun.StatusJoueur;
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
	
	private boolean connecte;
	private boolean enPartie;
	
	private Thread threadConnexion;
	private Thread threadJeu;
	
	private boolean enTest = false;
	
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
		this.client = new Client("127.0.0.1", 1033);
		
		// Verif si joueur est connecté au serveur ou non
		this.connecte = false;
		this.enPartie = false;
		
		// Affichage de la page de connexion
		if(this.enTest) {
			this.partie = new Partie();
			Joueur joueur1 = new Joueur();
			Joueur joueur2 = new Joueur();
			joueur1.setPseudo("Joueur 1");
			joueur2.setPseudo("Joueur 2");
			HashMap<Joueur, StatusJoueur> participants = new HashMap<Joueur, StatusJoueur>();
			participants.put(joueur1, StatusJoueur.EnJeu);
			participants.put(joueur2, StatusJoueur.EnJeu);
			partie.setParticipants(participants);
			Mot mot = new Mot();
			mot.setMot("ESCALIER");
			partie.setMot(mot);
			this.AfficherJeu();
		} else 
			this.AfficherConnexion();
		
		// Affichage
		stage.show();
		
		// Lorsque l'application se ferme
		stage.setOnCloseRequest(e -> 
		{
			this.client.envoyer(DemandeServeur.Quitter);
			this.client.disconnectedServer();
			Platform.exit();
			System.exit(0);
		});
	}
	public static void main(String[] args) {
		launch(args);
	}

	public Client getClient() {
		return client;
	}

	public Joueur getJoueur() {
		return this.joueur;
	}
	
	public Partie getPartie() {
		return partie;
	}
	
	public void setPartie(Partie unePartie) {
		this.partie = unePartie;
	}

	public void setJoueur(Joueur unJoueur) {
		this.joueur = unJoueur;
	}
	
	public boolean isEnPartie() {
		return enPartie;
	}
	
	public void setEnPartie(boolean enPartie) {
		this.enPartie = enPartie;
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
			Connexion conn = new Connexion(this);
			this.threadConnexion = new Thread(conn);
			this.threadConnexion.start();
			this.groupe.getChildren().add(conn);
		});
	}
	
	public void AfficherJeu() {
		Platform.runLater(() -> {
			this.groupe.getChildren().clear();
			JeuPanel jeuPanel = new JeuPanel(this);
			this.threadJeu = new Thread(jeuPanel);
			this.threadJeu.start();
			this.groupe.getChildren().add(jeuPanel);
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
	
	public void InitialisationPartie() {
		this.enPartie = false;
		this.ChargerInterfaceAttente();
		// On attend que le serveur nous envoie une partie
		Object element = this.client.attenteReponse();
		if(element != null && element instanceof Partie) {
			this.partie = (Partie)element;
			this.AfficherJeu();
			this.enPartie = true;
		}
	}
	
	public void ChargerInterfaceAttente() {
		// Ici on va demander le status de la partie
		this.client.envoyer(DemandeServeur.StatusPartie);
		Object reponse = this.client.attenteReponse();
		if(reponse != null && reponse instanceof ReponseServeur) {
			ReponseServeur repServ = (ReponseServeur)reponse;
			if(repServ == ReponseServeur.PartieEnAttenteJoueur || repServ == ReponseServeur.PartieEnCours || repServ == ReponseServeur.ChargementProchainePartie) {
				switch (repServ) {
					case PartieEnAttenteJoueur:
						this.AfficherMessage("En attente de joueurs...", Color.ORANGE);
						break;
					case PartieEnCours:
						this.AfficherMessage("Une partie est en cours, veuillez patienter...", Color.ORANGE);
						break;
					case ChargementProchainePartie:
						this.AfficherMessage("Chargement prochaine partie...", Color.ORANGE);
						break;
				}
			}
		}
	}
}
