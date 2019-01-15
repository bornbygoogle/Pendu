package client;

import commun.Joueur;
import commun.Partie;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainGUI extends Application {
	
	private Client client;
	
	private Joueur joueur;
	private Partie partie;
	
	private boolean estConnecte;
	
	@Override
	public void start(Stage stage) throws Exception {
		// Déclaration de la connexion
		this.client = new Client("127.0.0.1", 1025);
		
		// Déclaration des classes métiers
		this.joueur = new Joueur();
		this.partie = new Partie();
		
		// Verif si joueur est connecté au serveur ou non
		this.estConnecte = false;
		
		// Affichage de la page de connexion
		Group root = new Group();
<<<<<<< HEAD
		Scene scene = new Scene(root, 500, 825);
		root.getChildren().add(new Connexion(this));
		// Ajouter un titre
=======
		root.getChildren().add(new ClientPanel("ESCALIER"));
		Scene scene = new Scene(root, 500, 825);
		//root.getChildren().add(new ClientPanel());
		//root.getChildren().add(new Connexion(this));
>>>>>>> b723bb36ad1f84478f0868d32a7a723b7e7a4097
		stage.setTitle("Pendu");
		stage.setScene(scene);
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
}
