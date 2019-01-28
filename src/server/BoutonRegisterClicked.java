package server;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class BoutonRegisterClicked implements EventHandler<MouseEvent> {
	
	private AjoutJoueur ajoutjoueur;
	
	public BoutonRegisterClicked(AjoutJoueur ajoutjoueur) {
		this.ajoutjoueur = ajoutjoueur;
	}

	@Override
	public void handle(MouseEvent arg0) {
		// Verif format identifiant
		if(this.ajoutjoueur.getLogin().getText().length() > 0) {
			// Verif format mot de passe
			if(this.ajoutjoueur.getPassword().getText().length() > 0 
				//&& this.ajoutjoueur.getPassword().getText().matches("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}")
				&& this.ajoutjoueur.getPasswordVerif().getText().equals(this.ajoutjoueur.getPassword().getText())) 
			{
				this.ajoutjoueur.setMessageText("Enregistre ...");
				this.ajoutjoueur.setMessageColor(Color.GREEN);
				// Demande de connexion au serveur avec les identifiants
				this.ajoutjoueur.envoyerDemandeConnexion();
			} 
			else 
			{
				this.ajoutjoueur.setMessageText("Le password n'est pas valide.");
				this.ajoutjoueur.setMessageColor(Color.RED);
			}
		} else {
			this.ajoutjoueur.setMessageText("Le login n'est pas valide.");
			this.ajoutjoueur.setMessageColor(Color.RED);
		}
	}

}
