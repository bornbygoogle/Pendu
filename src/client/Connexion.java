package client;

import java.io.FileInputStream;
import java.io.IOException;

import commun.Joueur;
import commun.Utils;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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

public class Connexion extends Parent implements Runnable {
	
	private MainGUI main;

	private TextField login;
	private PasswordField password;
	private Button boutonConnexion;
	private Label message;
	
	private boolean statutThread;
	
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
			
			this.statutThread = true;
			
			Label texteLogin = new Label("Pseudo :");
			texteLogin.setPrefWidth(80);
			Label textePassword = new Label("Mot de passe :");
			textePassword.setPrefWidth(80);
			
			VBox conteneur = new VBox();
			conteneur.setAlignment(Pos.CENTER);
			conteneur.setPrefSize(725, 925);

			Image logo = new Image(new FileInputStream("./Images/logo.png"));
			ImageView logoView = new ImageView(logo);
			HBox conteneurLogo = new HBox();
			conteneurLogo.setAlignment(Pos.CENTER);
			conteneurLogo.getChildren().add(logoView);
			conteneur.getChildren().add(conteneurLogo);
			

			VBox conteneurConnexion = new VBox();
			conteneurConnexion.setAlignment(Pos.CENTER);
			conteneurConnexion.setPadding(new Insets(5, 0, 5, 0));
			
			Label texteConnexion = new Label("Connexion");
			texteConnexion.setFont(Font.font("Helvetica", FontPosture.REGULAR, 36));
			conteneurConnexion.getChildren().add(texteConnexion);
			
			
			HBox conteneurLogin = new HBox();
			conteneurLogin.setAlignment(Pos.CENTER);
			conteneurLogin.setPadding(new Insets(5, 0, 5, 10));
			conteneurLogin.getChildren().add(texteLogin);
			conteneurLogin.getChildren().add(this.login);
			conteneurConnexion.getChildren().add(conteneurLogin);
			
			
			HBox conteneurPassword = new HBox();
			conteneurPassword.setAlignment(Pos.CENTER);
			conteneurPassword.setPadding(new Insets(5, 0, 5, 10));
			conteneurPassword.getChildren().add(textePassword);
			conteneurPassword.getChildren().add(this.password);
			conteneurConnexion.getChildren().add(conteneurPassword);
			

			HBox conteneurBouton = new HBox();
			conteneurBouton.setAlignment(Pos.CENTER);
			conteneurBouton.setPadding(new Insets(5, 0, 5, 0));
			conteneurBouton.getChildren().add(this.boutonConnexion);
			conteneurConnexion.getChildren().add(conteneurBouton);
			

			HBox conteneurMessage = new HBox();
			conteneurMessage.setAlignment(Pos.CENTER);
			conteneurMessage.setPadding(new Insets(5, 0, 5, 0));
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
	
	public void verifierReponseConnexion(Joueur unJoueur) {
		// Check du status renvoy�
		if(unJoueur.getStatus()) {
			// Si connexion ok -> red�finir le joueur + mettre connexion � true + charger le jeu
			this.main.setJoueur(unJoueur);
			this.main.setConnecte(true);
			this.statutThread = false;
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

	@Override
	public synchronized void run() {
		while(this.statutThread) {
			try {
				Object element = this.main.getClient().attenteReponse();
				System.out.println("Run connexion");
				if(element instanceof Joueur) {
					this.verifierReponseConnexion((Joueur) element);
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				this.statutThread = false;
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				this.statutThread = false;
				e.printStackTrace();
			}
		}
	}
}
