package client;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class BoutonConnexionClicked implements EventHandler<MouseEvent> {
	
	private Connexion connexion;
	
	public BoutonConnexionClicked(Connexion connexion) {
		this.connexion = connexion;
	}

	@Override
	public void handle(MouseEvent arg0) {
		// Verif des identifiants
		if(this.connexion.getLogin().getText().length() > 0) {
			if(this.connexion.getPassword().getText().length() > 0 && this.connexion.getPassword().getText().matches("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}")) {
				this.connexion.definirIdentifiantsJoueur();
				this.connexion.setMessageText("Champs valide.");
				this.connexion.setMessageColor(Color.GREEN);
				// Demande de connexion au serveur avec les identifiants
				this.connexion.getMain().getClient().envoyer(this.connexion.getMain().getJoueur());
				
			} else {
				this.connexion.setMessageText("Le password n'est pas valide.");
				this.connexion.setMessageColor(Color.RED);
			}
		} else {
			this.connexion.setMessageText("Le login n'est pas valide.");
			this.connexion.setMessageColor(Color.RED);
		}
	}

}
