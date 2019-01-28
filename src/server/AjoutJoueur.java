package server;

import java.io.FileInputStream;

import commun.Joueur;
import commun.Utils;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;

public class AjoutJoueur extends Parent 
{

	private Server mainServer;

	private TextField login;
	private PasswordField password;
	private PasswordField passwordVerif;
	private Button boutonRegister;
	private Button boutonFermer;
	private Label message;
	private Joueur newJoueur;

	public AjoutJoueur(Server mainServeur) {
		this.mainServer = mainServeur;
		try {
			// Lancement du theard d'�coute de la connexion
			/*this.connexionReceive = new ConnexionReceive(this);
			this.threadReceive = new Thread(this.connexionReceive);
			this.threadReceive.start();*/
			
			this.login = new TextField();
			this.login.setPrefWidth(200);
			this.login.setPrefHeight(20);
			this.password = new PasswordField();
			this.password.setPrefWidth(200);
			this.password.setPrefHeight(20);
			this.passwordVerif = new PasswordField();
			this.passwordVerif.setPrefWidth(200);
			this.passwordVerif.setPrefHeight(20);
			this.boutonRegister = new Button("Register");
			this.boutonRegister.setOnMouseClicked(new BoutonRegisterClicked(this));
			this.boutonFermer = new Button("Fermer");
			//this.boutonFermer.setOnMouseClicked(/*Connection.arret()*/);
			this.message = new Label("");
			this.message.setTextFill(Color.RED);

			Button exitBtn = new Button("Exit");
			exitBtn.setOnAction(e -> Platform.exit());
			
			Label texteLogin = new Label("Pseudo :");
			texteLogin.setPrefWidth(80);
			Label textePassword = new Label("Mot de passe :");
			textePassword.setPrefWidth(80);
			Label textePasswordVerif = new Label("Verification :");
			textePasswordVerif.setPrefWidth(80);

			VBox conteneur = new VBox();

			Image logo = new Image(new FileInputStream("./Images/etape1.png"));
			ImageView logoView = new ImageView(logo);
			conteneur.getChildren().add(logoView);

			VBox conteneurConnexion = new VBox();
			conteneurConnexion.setPadding(new Insets(5, 0, 5, 100));
			
			Label texteConnexion = new Label("REGISTER");
			texteConnexion.setPadding(new Insets(0, 0, 0, 50));
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

			HBox conteneurPasswordVerif = new HBox();
			conteneurPasswordVerif.setPadding(new Insets(5, 0, 5, 0));
			conteneurPasswordVerif.getChildren().add(textePasswordVerif);
			conteneurPasswordVerif.getChildren().add(this.passwordVerif);
			conteneurConnexion.getChildren().add(conteneurPasswordVerif);

			Region region1 = new Region();
			HBox.setHgrow(region1, Priority.ALWAYS);

			HBox conteneurBoutonRegister = new HBox();
			conteneurBoutonRegister.setPadding(new Insets(5, 0, 5, 40));
			conteneurBoutonRegister.getChildren().add(this.boutonRegister);
			conteneurConnexion.getChildren().add(conteneurBoutonRegister);

			HBox conteneurMessage = new HBox();
			conteneurMessage.setPadding(new Insets(5, 0, 5, 60));
			conteneurMessage.getChildren().add(this.message);
			conteneurConnexion.getChildren().add(conteneurMessage);
			
			conteneur.getChildren().add(conteneurConnexion);
			
			this.getChildren().add(conteneur);
		}
		catch(Exception e) 
		{
			System.out.println(e.getMessage());
		}
	}

	public TextField getLogin() {
		return login;
	}

	public PasswordField getPassword() {
		return password;
	}

	public PasswordField getPasswordVerif() {
		return passwordVerif;
	}

	public Server getServer() {
		return mainServer;
	}

	public void setMessageColor(Color couleur) {
		this.message.setTextFill(couleur);
	}
	
	public void setMessageText(String message) {
		this.message.setText(message);
	}

	public void definirIdentifiantsJoueur() {
		newJoueur.setPseudo(this.getLogin().getText());
		newJoueur.setPass(Utils.encrypt(this.password.getText()));
		/*this.main.getJoueur().setPseudo(this.login.getText());
		this.main.getJoueur().setPass(Utils.encrypt(this.password.getText()));*/
	}
	
	public void envoyerDemandeConnexion() {
		newJoueur = new Joueur();
		newJoueur.setPseudo(this.getLogin().getText());
		//this.mainServer.getJoueur().setPseudo(this.getLogin().getText());
		//this.mainServer.getJoueur().setPass(Utils.encrypt(this.password.getText()));
		//this.definirIdentifiantsJoueur();
		System.out.println(Utils.getCurrentTimeUsingCalendar());
		System.out.println(this.newJoueur.getPseudo());
		//this.main.getServer().envoyer(this.main.getJoueur());
	}
}