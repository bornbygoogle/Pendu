package client;

import java.io.IOException;

import commun.DemandeServeur;
import commun.Joueur;
import commun.Partie;

import commun.StatusPartie;
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
	
	private boolean connecte;
	private boolean enPartie;
	
	private Thread threadConnexion;
	private Thread threadJeu;
	
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
	
	public void AfficherJeu(Partie unePartie) {
		Platform.runLater(() -> {
			this.groupe.getChildren().clear();
			JeuPanel jeuPanel = new JeuPanel(this, unePartie);
			this.threadJeu = new Thread(jeuPanel);
			this.threadJeu.start();
			this.groupe.getChildren().add(jeuPanel);
		});
	}
	
	public void AfficherMessage(String message, Color couleur, int version) {
		Platform.runLater(new Runnable() {
		    @Override
		    public void run() {
				groupe.getChildren().clear();
				groupe.getChildren().add(new MessagePanel(message, couleur, version));
		    }
		});
	}
	
	public void InitialisationPartie(int test) {
		this.enPartie = false;
		this.ChargerInterfaceAttente();
		// On attend que le serveur nous envoie une partie
		Object element;
		try {
			element = this.client.attenteReponse();
			if (test == 2) {
				element = this.client.attenteReponse();
			}
			if(element != null && element instanceof Partie) {
				this.AfficherJeu((Partie)element);
				this.enPartie = true;
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public void ChargerInterfaceAttente() {
		// Ici on va demander le status de la partie
		this.client.envoyer(DemandeServeur.StatusPartie);
		try {
			Object reponse = this.client.attenteReponse();
			if(reponse != null && reponse instanceof StatusPartie) {
				StatusPartie repServ = (StatusPartie)reponse;
				switch (repServ) {
					case EnAttenteJoueur:
						this.AfficherMessage("En attente de joueurs...", Color.ORANGE, 3);
						break;
					case EnCours:
						this.AfficherMessage("Une partie est en cours, \nVeuillez patienter...", Color.ORANGE, 3);
						break;
					case ChargementPartie:
					case Fini:
						this.AfficherMessage("Chargement prochaine partie...", Color.ORANGE, 3);
						break;
				}
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
