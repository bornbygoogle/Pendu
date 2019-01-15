package client;

import java.io.FileNotFoundException;

import commun.Joueur;
import commun.Partie;
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
		// D�marrage du client //
		/////////////////////////


		// Config graphique de l'appli
		this.groupe = new Group();
		Scene scene = new Scene(this.groupe, 500, 825);
		stage.setTitle("Pendu");
		stage.setScene(scene);
		
		
		
		// Lancement du r�seau -> connexion au port 1025 en local 
		this.client = new Client("127.0.0.1", 1025);
		
		// D�claration des classes m�tiers
		this.joueur = new Joueur();
		this.partie = new Partie();
		
		// Verif si joueur est connect� au serveur ou non
		this.connecte = false;
		
		// Affichage de la page de connexion
		this.AfficherConnexion();
		
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
			this.groupe.getChildren().add(new ClientPanel("Mot"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
