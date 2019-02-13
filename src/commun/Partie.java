package commun;

import java.io.Serializable;
import java.util.HashMap;

public class Partie implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -121557802955103155L;
	private Mot mot;
	private Joueur joueurGagnant;
	private HashMap<Joueur, StatusJoueur> participants;
	private StatusPartie statusPartie;
	
	public Mot getMot() {
		return mot;
	}
	public void setMot(Mot mot) {
		this.mot = mot;
	}
	public Joueur getJoueurGagnant() {
		return joueurGagnant;
	}
	public void setJoueurGagnant(Joueur joueurGagnant) {
		this.joueurGagnant = joueurGagnant;
	}
	public HashMap<Joueur, StatusJoueur> getParticipants() {
		return participants;
	}
	public void setParticipants(HashMap<Joueur, StatusJoueur> participants) {
		this.participants = participants;
	}
	public StatusPartie getStatusPartie() {
		return statusPartie;
	}
	public void setStatusPartie(StatusPartie statusPartie) {
		this.statusPartie = statusPartie;
	}
}
