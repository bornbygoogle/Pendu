package commun;

public class Joueur 
{
	private String pseudo = "";
	private String pass = "";
	private int score = 0;
	private int nbParties = 0;
	private boolean status = false;
	private String	message = "";
	private String	dateDernierCo = Utils.getCurrentTimeUsingCalendar();
	
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

	public void setNbScore(int _nbParties)
	{
		this.nbParties = _nbParties;
	}
	public boolean getStatus() {
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
	
	public String getDateDernierCo() {
		return dateDernierCo;
	}
	public void setDateDernierCo(String _dateDernierCo) {
		this.dateDernierCo = _dateDernierCo;
	}
}
