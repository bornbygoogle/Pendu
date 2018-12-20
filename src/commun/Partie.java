package commun;

import java.util.List;

public class Partie {

	private int id;
	private Mot mot;
	private Joueur joueurGagnant;
	private List<Joueur> participants;
	
	public Partie(int id, Mot mot, Joueur joueurGagnant, List<Joueur> participants) {
		this.id = id;
		this.mot = mot;
		this.joueurGagnant = joueurGagnant;
		this.participants = participants;
	}
}
