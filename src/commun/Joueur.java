package commun;

import java.time.LocalDateTime;
import java.util.Date;

public class Joueur 
{
	private String pseudo = "";
	private String pass = "";
	private int score = 0;
	private int nbParties = 0;
	private boolean status = false;
	private String	message = "";
	private Date	dateDernierCo = LocalDateTime.now();
	
	public String getPseudo() {
		return pseudo;
	}
	public void setPseudo(String _pseudo) {
		this.pseudo = _pseudo;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String _pass) {
		this.pass = _pass;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int _score) {
		this.score = _score;
	}
	public int getNbParties() {
		return nbParties;
	}
	public void setNbParties(int _nbParties) {
		this.nbParties = _nbParties;
	}

	public void setNbScore(int _nbParties)
	{
		this.nbParties = _nbParties;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
