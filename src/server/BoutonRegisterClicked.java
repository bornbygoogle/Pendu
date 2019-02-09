package server;

import commun.Joueur;
import commun.Utils;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class BoutonRegisterClicked implements EventHandler<MouseEvent> {

	private AjoutJoueur ajoutJoueur;

	public BoutonRegisterClicked(AjoutJoueur ajoutJoueur) {
		this.ajoutJoueur = ajoutJoueur;
	}

	@Override
	public void handle(MouseEvent arg0) {
		// Verif format identifiant
		if (this.ajoutJoueur.getLogin().getText().length() > 0) {
			// Verif format mot de passe
			if (this.ajoutJoueur.getPassword().getText().length() > 4
				&& Utils.validatePassword(this.ajoutJoueur.getPassword().getText())) {
				boolean verifExiste = false;
				for(Joueur j : this.ajoutJoueur.getServer().getListeJoueurs()) {
					if(j.getPseudo().toLowerCase().equals(this.ajoutJoueur.getLogin().getText().toLowerCase()))
						verifExiste = true;
				}
				if(!verifExiste) {
					this.ajoutJoueur.registerJoueurs();
					this.ajoutJoueur.setMessageText("Joueur " + this.ajoutJoueur.getLogin().getText() + " ajouté !");
					this.ajoutJoueur.setMessageColor(Color.GREEN);
				}
				else {
					this.ajoutJoueur.setMessageText("Le joueur existe déjà.");
					this.ajoutJoueur.setMessageColor(Color.RED);
				}
			}
			else 
			{
				this.ajoutJoueur.setMessageText("Le password n'est pas valide.");
				this.ajoutJoueur.setMessageColor(Color.RED);
			}
		} else {
			this.ajoutJoueur.setMessageText("Le login n'est pas valide.");
			this.ajoutJoueur.setMessageColor(Color.RED);
		}
	}

}
