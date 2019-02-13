package server;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import commun.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainServer extends Application
{
	// Eléments JAVAFX
	private Group groupe;

	// Eléments Réseau
	private Server server;

	// Eléments jeu
	private Jeu jeu;

	// Eléments BDD
	private Connection connection = null;
	private List<Theme> themes = new ArrayList<Theme>();
	private List<Joueur> joueurs = new ArrayList<Joueur>();
	private List<Joueur> newJoueurs = new ArrayList<Joueur>();

	@Override
	public void start(Stage stage) throws Exception 
	{
		//////////////////////
		// Récupération BDD //
		//////////////////////

		// Recuperation des donnees necessaires de la BDD
		this.connection = ConnectionBDD.getInstance();
		this.themes = Methods.getListThemes(connection);
		this.joueurs = Methods.getListJoueurs(connection);
		
		this.afficherJoueursEnregistres();
		
		/////////////////////////
		// Démarrage du réseau //
		/////////////////////////

		this.server = new Server(this, 1033);

		///////////////////////
		// Configuration jeu //
		///////////////////////

		this.jeu = new Jeu(this);


		//////////////////////
		// Interface JAVAFX //
		//////////////////////

		// Config graphique de l'appli
		this.groupe = new Group();
		Scene scene = new Scene(this.groupe, 500, 925);

		stage.setTitle("Pendu serveur");
		stage.setScene(scene);
		
		// Affichage de la page de connexion
		this.afficherPanelAjouterJoueur();

		// Affichage
			// longrunning operation runs on different thread
		/*Thread thread = new Thread(new Runnable() 
			{
				@Override
				public void run() {
					Runnable updater = new Runnable() 
					{
						@Override
						public void run() {
							refreshStage(stage);
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
			thread.start();*/


		//refreshStage(stage);
		stage.show();

		///////////////////
		// Arr?t serveur //
		///////////////////

		stage.setOnCloseRequest(e -> 
		{
			// On enregistre les nouveaux joueurs en BDD
			try {
				if(this.newJoueurs.size() > 0) {
					System.out.println("Enregistrement des joueurs dans la BDD :");
					for(Joueur j : this.newJoueurs)
						System.out.println("\t- " + j.getPseudo());
					
					Methods.updateJoueur(connection, newJoueurs);
				}

				if (this.connection != null)
					this.connection.close();

			} catch (SQLException e2) {
				e2.printStackTrace();
			}

			// On stop le r�seau
			this.server.stopServerRunning();
			Platform.exit();
			System.exit(0);
		});
	}

	public static void main(String[] args) {
		launch(args);
	}


	public Server getServer() {
		return this.server;
	}

	public Jeu getJeu() {
		return this.jeu;
	}
	
	public List<Joueur> getListeJoueurs() {
		return this.joueurs;
	}
	
	public List<Theme> getThemes() {
		return this.themes;
	}


	public void ajouterJoueur(Joueur unJoueur) {
		this.joueurs.add(unJoueur);
		this.newJoueurs.add(unJoueur);
	}

	public void afficherPanelAjouterJoueur() {
		this.groupe.getChildren().add(new AjoutJoueur(this));
	}

	public void refreshStage(Stage stage)
	{
		stage.show();
	}

	public void afficherJoueursEnregistres() {
		System.out.println("Liste des joueurs enregistr�s :");
		for(Joueur j : this.joueurs) {
			System.out.println("\t- " + j.getPseudo());
		}
	}
}