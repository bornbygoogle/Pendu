package commun;

import java.util.ArrayList;
import java.util.List;

public class Partie {

	private int id = 0;
	private Mot mot = new Mot();
	private Joueur joueurGagnant = new Joueur();
	private List<Joueur> participants = new ArrayList<Joueur>();
	
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
	public List<Joueur> getParticipants() {
		return participants;
	}
	public void setParticipants(List<Joueur> participants) {
		this.participants = participants;
	}
	
	
}
