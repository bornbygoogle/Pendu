package client;

import java.io.FileInputStream;
import java.io.IOException;

import commun.Joueur;
import commun.Utils;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;

public class Connexion extends Parent {
	
	private MainGUI main;

	private TextField login;
	private PasswordField password;
	private Button boutonConnexion;
	private Label message;
	
	public Connexion(MainGUI main) {
		this.main = main;
		try {
			this.login = new TextField();
			this.login.setPrefWidth(200);
			this.login.setPrefHeight(20);
			this.password = new PasswordField();
			this.password.setPrefWidth(200);
			this.password.setPrefHeight(20);
			this.boutonConnexion = new Button("Se connecter");
			this.boutonConnexion.setOnMouseClicked(new BoutonConnexionClicked(this));
			this.message = new Label("");
			this.message.setTextFill(Color.RED);
			
			Label texteLogin = new Label("Pseudo :");
			texteLogin.setPrefWidth(80);
			Label textePassword = new Label("Mot de passe :");
			textePassword.setPrefWidth(80);

			VBox conteneur = new VBox();

			Image logo = new Image(new FileInputStream("./Images/logo.png"));
			ImageView logoView = new ImageView(logo);
			conteneur.getChildren().add(logoView);
			

			VBox conteneurConnexion = new VBox();
			conteneurConnexion.setPadding(new Insets(5, 0, 5, 100));
			
			Label texteConnexion = new Label("Connexion");
			texteConnexion.setPadding(new Insets(0, 0, 0, 70));
			texteConnexion.setFont(Font.font("Helvetica", FontPosture.REGULAR, 36));
			conteneurConnexion.getChildren().add(texteConnexion);
			
			
			HBox conteneurLogin = new HBox();
			conteneurLogin.setPadding(new Insets(5, 0, 5, 0));
			conteneurLogin.getChildren().add(texteLogin);
			conteneurLogin.getChildren().add(this.login);
			conteneurConnexion.getChildren().add(conteneurLogin);
			
			
			HBox conteneurPassword = new HBox();
			conteneurPassword.setPadding(new Insets(5, 0, 5, 0));
			conteneurPassword.getChildren().add(textePassword);
			conteneurPassword.getChildren().add(this.password);
			conteneurConnexion.getChildren().add(conteneurPassword);
			

			HBox conteneurBouton = new HBox();
			conteneurBouton.setPadding(new Insets(5, 0, 5, 110));
			conteneurBouton.getChildren().add(this.boutonConnexion);
			conteneurConnexion.getChildren().add(conteneurBouton);
			

			HBox conteneurMessage = new HBox();
			conteneurMessage.setPadding(new Insets(5, 0, 5, 110));
			conteneurMessage.getChildren().add(this.message);
			conteneurConnexion.getChildren().add(conteneurMessage);
			
			
			conteneur.getChildren().add(conteneurConnexion);
			
			
			this.getChildren().add(conteneur);
		} catch(IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public TextField getLogin() {
		return login;
	}

	public PasswordField getPassword() {
		return password;
	}
	
	public Button getBouton() {
		return this.boutonConnexion;
	}
	
	public MainGUI getMain() {
		return main;
	}
	
	public void setMessageColor(Color couleur) {
		this.message.setTextFill(couleur);
	}
	
	public void setMessageText(String message) {
		this.message.setText(message);
	}
	
	public void definirIdentifiantsJoueur() {
		this.main.setJoueur(new Joueur());
		this.main.getJoueur().setPseudo(this.login.getText());
		this.main.getJoueur().setPass(Utils.encrypt(this.getPassword().getText(), Utils.getSecretKey()));
	}
	
	public void envoyerDemandeConnexion() {
		this.definirIdentifiantsJoueur();
		this.main.getClient().envoyer(this.main.getJoueur());
	}
	
	public void verifierReponseConnexion() {
		Object element = this.main.getClient().attenteReponse();
		if(element != null && element instanceof Joueur) {
			Joueur unJoueur = (Joueur)element;
			// Check du status renvoy�
			if(unJoueur.getStatus()) {
				// Si connexion ok -> red�finir le joueur + mettre connexion � true + charger le jeu
				this.main.setJoueur(unJoueur);
				this.main.setConnecte(true);
				// On Charge le jeu
				this.main.InitialisationPartie();
			} else {
				// Si connexion pas bonne, afficher le message d'erreur renvoy� par le serveur et laisser la page de connexion
				this.setMessageColor(Color.RED);
				if(unJoueur.getMessage().length() > 0) {
					Platform.runLater(new Runnable() {
					    @Override
					    public void run() {
							setMessageText(unJoueur.getMessage());
					    }
					});
				} else {
					Platform.runLater(new Runnable() {
					    @Override
					    public void run() {
							setMessageText("Login ou mot de passe incorrect");
					    }
					});
				}
				this.boutonConnexion.setDisable(false);
			}
		}
	}
}
