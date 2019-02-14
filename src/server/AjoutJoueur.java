package server;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import commun.Joueur;
import commun.Utils;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Insets;
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

public class AjoutJoueur extends Parent 
{
	private MainServer mainServer;

	private GridPane gridPane;

	private TextField login;
	private PasswordField password;
	private Button boutonRegister;
	private Label message;

	private Joueur newJoueur;
	private List<Joueur> listJoueur;
	private List<Label> listLabel;

	public AjoutJoueur(MainServer mainServeur) 
	{
		this.mainServer = mainServeur;
		this.listJoueur = mainServeur.getListeJoueurs();

		listLabel = new ArrayList<Label>();
		for (Joueur j : this.listJoueur)
		{
			listLabel.add(new Label(j.getPseudo()));
		}


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
			texteListeJoueurs.setFont(Font.font("Helvetica", FontPosture.REGULAR, 22));
			conteneurTexteListeJoueurs.getChildren().add(texteListeJoueurs);

			this.gridPane = new GridPane();
			this.gridPane.setPadding(new Insets(10, 0, 0, 90));
			this.gridPane.setHgap(70);
			this.gridPane.setVgap(10);
			this.gridPane.setPrefWidth(300);
			this.gridPane.setPrefHeight(200);

			ScrollPane sp = new ScrollPane();
			sp.setPadding(new Insets(10, 0, 0, 90));
			sp.setContent(gridPane);
			sp.setPrefWidth(300);
			sp.setPrefHeight(200);

			// Always show scroll bar
			sp.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
			sp.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);


			// longrunning operation runs on different thread
			Thread thread = new Thread(new Runnable() 
			{
				@Override
				public void run() 
				{
					Runnable updater = new Runnable() 
					{
						@Override
						public void run() 
						{
							updateGridPane();
						}
					};
			
					while (true) {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException ex) {
						}
					// UI update is run on the Application thread
					Platform.runLater(updater);
					}
				}
			});
			// don't let thread prevent JVM shutdown
			thread.setDaemon(true);
			thread.start();

			updateGridPane();
			
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

	public void registerJoueurs() 
	{
		this.newJoueur = new Joueur();
		this.newJoueur.setPseudo(this.getLogin().getText());
		this.newJoueur.setPass(Utils.encrypt(this.getPassword().getText(), Utils.getSecretKey()));
		this.newJoueur.setDateDernierCo(Utils.getCurrentTimeUsingCalendar());
		this.mainServer.ajouterJoueur(this.newJoueur);
	}

	public void updateGridPane()
	{
		int row = 0;
		for (Joueur j : this.listJoueur)
		{
			Label pseudo = new Label(j.getPseudo());
			pseudo.setFont(Font.font("Helvetica", FontPosture.REGULAR, 14));
			/*pseudo.textFillProperty().bind(
				Bindings.when(new SimpleBooleanProperty(j.getStatus()))
						.then(Color.GREEN)
						.otherwise(Color.BLACK) );*/
			Label score = new Label();
			score.setFont(Font.font("Helvetica", FontPosture.REGULAR, 14));

			score.textProperty().bind(new SimpleIntegerProperty(j.getScore()).asString());

			/*if (j.getStatus())
				pseudo.setTextFill(Color.GREEN);
			else
				pseudo.setTextFill(Color.BLACK);*/

			this.gridPane.add(pseudo, 0, row, 1, 1);
			this.gridPane.add(score, 1, row, 1, 1);
			row += 1;
		}
	}
}