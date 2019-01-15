package commun;

import java.sql.Date;

public class Joueur {


	private String login = "";
	private String pseudo = "";
	private String pass = "";
	private int score = 0;
	private int nbParties = 0;
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPseudo() {
		return pseudo;
	}
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getNbParties() {
		return nbParties;
	}
	public void setNbParties(int nbParties) {
		this.nbParties = nbParties;
	}
	
	public void setId(String _pseudo)
	{
		this.pseudo = _pseudo;
	}

	public void setNbScore(int _nbParties)
	{
		this.nbParties = _nbParties;
	}
}
