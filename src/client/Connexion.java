package client;

import java.io.FileInputStream;
import java.io.IOException;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Connexion extends Parent {

	private TextField login;
	private PasswordField password;
	
	public Connexion() {
		try {
			this.login = new TextField();
			this.login.setPrefWidth(200);
			this.login.setPrefHeight(20);
			this.password = new PasswordField();
			this.password.setPrefWidth(200);
			this.password.setPrefHeight(20);
			
			
			

			Label texteLogin = new Label("Identifiant :");
			texteLogin.setPrefWidth(100);
			Label textePassword = new Label("Password :");
			textePassword.setPrefWidth(100);
			

			Image logo = new Image(new FileInputStream("./Images/logo.png"));
			ImageView logoView = new ImageView(logo);
			this.getChildren().add(logoView);
			
			VBox conteneur = new VBox();
			conteneur.setPadding(new Insets(50));
			HBox conteneurLogin = new HBox();
			conteneurLogin.setPadding(new Insets(50));
			conteneurLogin.getChildren().add(texteLogin);
			conteneurLogin.getChildren().add(this.login);
			HBox conteneurPassword = new HBox();
			conteneurPassword.setPadding(new Insets(50));
			conteneurPassword.getChildren().add(textePassword);
			conteneurPassword.getChildren().add(this.password);
			conteneur.getChildren().add(conteneurLogin);
			conteneur.getChildren().add(conteneurPassword);
			
			this.getChildren().add(conteneur);
		} catch(IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
