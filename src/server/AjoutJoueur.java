package server;

import java.io.FileInputStream;
import java.util.List;

import commun.Joueur;
import commun.Utils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class AjoutJoueur extends Parent 
{

	private MainServer mainServer;

	private TextField login;
	private PasswordField password;
	private Button boutonRegister;
	private Label message;

	private Joueur newJoueur;
	private List<Joueur> listJoueur;

	public AjoutJoueur(MainServer mainServeur) {
		this.mainServer = mainServeur;
		this.listJoueur = mainServeur.getListeJoueurs();
		try {		
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
			

			VBox conteneurListeJoueurs = new VBox();
			conteneurListeJoueurs.setFillWidth(true);
			conteneurListeJoueurs.setPrefWidth(500);

			VBox conteneurTexteListeJoueurs = new VBox();
			conteneurTexteListeJoueurs.setFillWidth(true);
			conteneurTexteListeJoueurs.setPrefWidth(500);

			Label texteListeJoueurs = new Label("Joueurs inscrits");
			texteListeJoueurs.setPadding(new Insets(0, 0, 0, 50));
			texteListeJoueurs.setFont(Font.font("Helvetica", FontPosture.REGULAR, 18));
			conteneurTexteListeJoueurs.getChildren().add(texteListeJoueurs);




			GridPane gridPane = new GridPane();
			gridPane.setPadding(new Insets(10, 0, 0, 90));
			gridPane.setHgap(70);
			gridPane.setVgap(10);
			gridPane.setPrefWidth(300);

			ScrollPane sp = new ScrollPane();
			sp.setContent(gridPane);
	 
			// Always show scroll bar
			sp.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
			sp.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);

			int row = 0;
			for (Joueur j : listJoueur)
			{
				gridPane.add(new Label(j.getPseudo()), 0, row, 1, 1);
				gridPane.add(new Label(Integer.toString(j.getScore())), 1, row, 1, 1);
				row += 1;
			}
			


			/*VBox joueur1 = new VBox();
			joueur1.setFillWidth(true);
			Label textPseudo = new Label("Joueur 1 :");
			texteListeJoueurs.setPadding(new Insets(0, 0, 0, 50));
			texteListeJoueurs.setFont(Font.font("Helvetica", FontPosture.REGULAR, 12));
			joueur1.getChildren().add(textPseudo);*/


			conteneurListeJoueurs.getChildren().addAll(conteneurTexteListeJoueurs, sp);

			conteneur.getChildren().addAll(conteneurConnexion, conteneurListeJoueurs);
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