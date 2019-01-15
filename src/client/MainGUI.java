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
	
	@Override
	public void start(Stage stage) throws Exception {
		this.client = new Client("127.0.0.1", 1025);
		
		this.joueur = new Joueur();
		this.partie = new Partie();
		
		Group root = new Group();
		//root.getChildren().add(new ClientPanel("abcdefg"));
		Scene scene = new Scene(root, 500, 825);
		//root.getChildren().add(new ClientPanel());
		root.getChildren().add(new Connexion(this));
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
