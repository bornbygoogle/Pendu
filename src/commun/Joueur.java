package commun;

import java.sql.Date;

public class Joueur {

	private String	pseudo;
	private String	pass;
	private int 	score;
	private int 	nbParties;
	private Date	dateInscription;
	private Date	dateLastConnection;
	
	public Joueur(String pseudo, String pass, int score, int nbParties) {
		this.pseudo = pseudo;
		this.pass = pass;
		this.score = score;
		this.nbParties = nbParties;
	}

	public String getPseudo()
	{
		return this.pseudo;
	}

	public void setId(String _pseudo)
	{
		this.pseudo = _pseudo;
	}

	public String getPass()
	{
		return this.pass;
	}

	public void setPass(String _pass)
	{
		this.pass = _pass;
	}

	public int getScore()
	{
		return this.score;
	}

	public void setScore(int _score)
	{
		this.score = _score;
	}

	public int getNbParties()
	{
		return this.nbParties;
	}

	public void setNbScore(int _nbParties)
	{
		this.nbParties = _nbParties;
	}
}
