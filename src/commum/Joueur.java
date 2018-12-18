package commum;

public class Joueur {

	private String pseudo;
	private String pass;
	private int score;
	private int nbParties;
	
	public Joueur(String pseudo, String pass, int score, int nbParties) {
		this.pseudo = pseudo;
		this.pass = pass;
		this.score = score;
		this.nbParties = nbParties;
	}
}
