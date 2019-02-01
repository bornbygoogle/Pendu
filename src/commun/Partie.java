package commun;

import java.io.Serializable;
import java.util.HashMap;

public class Partie implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id = 0;
	private Mot mot = new Mot();
	private Joueur joueurGagnant = new Joueur();
	private HashMap<Joueur, StatusJoueur> participants = new HashMap<Joueur, StatusJoueur>();
	private StatusPartie statusPartie = StatusPartie.EnCours;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
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
