package server;

import java.io.FileInputStream;

import commun.Joueur;
import commun.Utils;
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

	private MainServer mainServer;

	private TextField login;
	private PasswordField password;
	private Button boutonRegister;
	private Label message;

	private Joueur newJoueur;

	public AjoutJoueur(MainServer mainServeur) {
		this.mainServer = mainServeur;
		try {
			// Lancement du theard d'ïecoute de la connexion
			/*this.connexionReceive = new ConnexionReceive(this);
			this.threadReceive = new Thread(this.connexionReceive);
			this.threadReceive.start();*/
			
			this.login = new TextField();
			this.login.setPrefWidth(200);
			this.login.setPrefHeight(20);
			
			this.password = new PasswordField();
			this.password.setPrefWidth(200);
			this.password.setPrefHeight(20);
			
			this.boutonRegister = new Button("Register");
			this.boutonRegister.setOnMouseClicked(new BoutonRegisterClicked(this));
			
			this.message = new Label("");
			this.message.setTextFill(Color.RED);
			
			Label texteLogin = new Label("Pseudo :");
			texteLogin.setPrefWidth(80);
			Label textePassword = new Label("Mot de passe :");
			textePassword.setPrefWidth(80);

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

	public MainServer getServer() {
		return mainServer;
	}

	public void setMessageColor(Color couleur) {
		this.message.setTextFill(couleur);
	}
	
	public void setMessageText(String message) {
		this.message.setText(message);
	}

public void registerJoueurs() {
		this.newJoueur = new Joueur();
		this.newJoueur.setPseudo(this.getLogin().getText());
		this.newJoueur.setPass(Utils.encrypt(this.getPassword().getText(), Utils.getSecretKey()));
		this.newJoueur.setDateDernierCo(Utils.getCurrentTimeUsingCalendar());
		this.mainServer.ajouterJoueur(this.newJoueur);
	}
}