package client;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import commun.Joueur;
import commun.Partie;
import commun.StatusJoueur;
import commun.StatusPartie;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

public class JeuPanel extends Parent implements Runnable {
	protected MainGUI gui;
	
	protected ImageView screen;
	protected TextFlow joueurs;
	protected List<TextFlow> letters;
	protected List<Button> alphabet;
	
	protected Partie partie;

	protected int dangerLevel;
	protected int winLevel;
	
	public JeuPanel(MainGUI game) {
		this.gui = game;
		this.partie = game.getPartie();
		this.dangerLevel = 1;
		this.winLevel = 0;
		
		this.setButtons();
		this.setTexts();
		this.setImage();
		this.setJoueurs();
		this.setButtonsActions();
	}
	
	public void setButtons() {
		alphabet = new ArrayList<Button>();
		
		int k = 0;
		for (int i=0; i<26; i++) {
			Button btn = new Button();
			if (i/9 == 0) {
				btn.setLayoutX(25 + i*50 + k);
				btn.setLayoutY(750);
			} else if (i/9 == 1) {
				btn.setLayoutX(25 + (i-9)*50 + k);
				btn.setLayoutY(805);
			} else {
				btn.setLayoutX(50 + (i-18)*50 + k);
				btn.setLayoutY(860);
			}
			btn.setPrefWidth(50);
			btn.setPrefHeight(50);
			char lettre = (char)(i+65);
			String btnLettre = "" + lettre;
			btn.setText(btnLettre);
			
			alphabet.add(btn);
			this.getChildren().add(btn);
			
			if(i == 8)
				k = 0;
			else if(i == 17) {
				k = 0;
			} else {
				k += 5;
			}

		}
	}
	
	public void setTexts() {
		letters = new ArrayList<TextFlow>();

		TextFlow theme = new TextFlow();
		theme.setLayoutX(500);
		theme.setLayoutY(170);
		Text themeText = new Text();
		themeText.setText("Theme : " + this.partie.getMot().getTheme().getLibelle());
		themeText.setFont(Font.font("Helvetica", FontPosture.REGULAR, 16));
		theme.getChildren().add(themeText);
		this.getChildren().add(theme);
		
		int k = 0;
		for (int i=0; i<this.partie.getMot().getMot().length(); i++) {
			TextFlow receivedText = new TextFlow();
			if (i/8 == 0) {
				receivedText.setLayoutX(50+i*50 + k);
				receivedText.setLayoutY(550);
			} else if (i/8 == 1) {
				receivedText.setLayoutX(50+(i-8)*50 + k);
				receivedText.setLayoutY(605);
			} else if (i/8 == 2) {
				receivedText.setLayoutX(50+(i-16)*50 + k);
				receivedText.setLayoutY(660);
			}
			receivedText.setPrefWidth(50);
			receivedText.setPrefHeight(50);
			receivedText.setTextAlignment(TextAlignment.CENTER);
			receivedText.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
			
			letters.add(receivedText);
			this.getChildren().add(receivedText);
			
			if(i == 7)
				k = 0;
			else if(i == 15) {
				k = 0;
			} else {
				k += 5;
			}
		}
	}
	
	public void setJoueurs() {
		this.joueurs = new TextFlow();
		this.joueurs.setLayoutX(500);
		this.joueurs.setLayoutY(200);
		this.joueurs.setPrefWidth(150);
		this.joueurs.setPrefHeight(200);
		this.joueurs.setTextAlignment(TextAlignment.LEFT);
		this.joueurs.setBorder(new Border(new BorderStroke(Color.GAINSBORO, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

		Text titre = new Text();
		titre.setText("Joueurs : \n");
		titre.setFont(Font.font("Helvetica", FontPosture.REGULAR, 18));
		this.joueurs.getChildren().add(titre);
		
		for (Joueur j : this.partie.getParticipants().keySet()) {
			Text joueur = new Text();
			if (this.partie.getParticipants().get(j) == StatusJoueur.EnJeu) {
				joueur.setText(j.getPseudo() + " - En cours\n");
			} else if (this.partie.getParticipants().get(j) == StatusJoueur.Perdu) {
				joueur.setText(j.getPseudo() + " - Perdu\n");
			} else if (this.partie.getParticipants().get(j) == StatusJoueur.Trouve) {
				joueur.setText(j.getPseudo() + " - GagnÃ©\n");
			}
			joueur.setFont(Font.font("Helvetica", FontPosture.REGULAR, 12));
			this.joueurs.getChildren().add(joueur);
		}
		
		this.getChildren().add(this.joueurs);
		
		TextFlow mainJoueur = new TextFlow();
		mainJoueur.setLayoutX(10);
		mainJoueur.setLayoutY(10);
		Text joueur = new Text();
		joueur.setText(this.gui.getJoueur().getPseudo());
		joueur.setFont(Font.font("Helvetica", FontPosture.REGULAR, 16));
		mainJoueur.getChildren().add(joueur);
		this.getChildren().add(mainJoueur);
		
	}
	
	public void setImage() {
		Image image;
		try {
			image = new Image(new FileInputStream("./Images/etape1.png"));
			screen = new ImageView(image);
			screen.setLayoutX(50);
			screen.setLayoutY(0);
			screen.setFitHeight(550);
			screen.setFitWidth(400);
			
			this.getChildren().add(screen);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setButtonsActions() {
		for (int i=0; i<alphabet.size(); i++) {
			alphabet.get(i).setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {	
					Boolean test = false;
					String button = event.getSource().toString();
					String label = button.substring(button.indexOf("'"));
					label = label.substring(1, 2);
					
					for (int i=0; i<partie.getMot().getMot().length(); i++) {
						String letterToFind = Character.toString(partie.getMot().getMot().toUpperCase().charAt(i));
						if (label.equals(letterToFind)) {
							letters.get(i).getChildren().clear();
							Text letter = new Text();
							letter.setText(label);
							letter.setFont(Font.font("Helvetica", FontPosture.REGULAR, 30));
							letters.get(i).getChildren().add(letter);
							test = true;
							winLevel++;
						}
					}
					
					if (test == false) {
						dangerLevel++;
						Image image;
						try {
							image = new Image(new FileInputStream("./Images/etape" + dangerLevel + ".png"));
							screen.setImage(image);
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						}
						if (dangerLevel == 7) {
							gui.getClient().envoyer(StatusJoueur.Perdu);
						}
					}
					

					if (winLevel == partie.getMot().getMot().length()) {
						gui.getClient().envoyer(StatusJoueur.Trouve);
					}
					
					int actualButton = ((int) label.charAt(0))-65;
					alphabet.get(actualButton).setDisable(true);
					
				}
				
			});
			
		}
	}

	@Override
	public void run() {
		boolean statut = true;
		while(statut) {
			try {
				Object element = this.gui.getClient().attenteReponse();
				if(element != null && element instanceof Partie) {
					this.partie = (Partie)element;

					System.out.println(((Partie)element).getStatusPartie().name());
					if (this.partie.getStatusPartie().equals(StatusPartie.Fini)) {
						if (this.partie.getJoueurGagnant() != null) {
							if (this.partie.getJoueurGagnant().getPseudo().equals(this.gui.getJoueur().getPseudo()))  {
								this.gui.AfficherMessage("Vous avez gagné ! ", Color.ORANGE, 1);
							} else {
								this.gui.AfficherMessage("Vous avez perdu ! \n " + this.partie.getJoueurGagnant().getPseudo() + " a gagné !", Color.ORANGE, 2);
							}
						} else {
							this.gui.AfficherMessage("Aucun joueur n'a gagné !", Color.ORANGE, 3);
						}
					} else {
						Platform.runLater(new Runnable() {
						    @Override
						    public void run() {
								setJoueurs();
						    }
						});
					}
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				statut = false;
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				statut = false;
				e.printStackTrace();
			}
		}
	}
}