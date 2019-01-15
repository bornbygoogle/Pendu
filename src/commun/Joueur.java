package commun;

public class Joueur 
{
	private int id = 0;
	private String login = "";
	private String pseudo = "";
	private String pass = "";
	private int score = 0;
	private int nbParties = 0;
	private boolean status = false;
	private String message = "";
	
	public int getId() {
		return id;
	}

	public void setId(int _id) {
		this.id = _id;
	}

	public String getLogin() {
		return login;
	}
	public void setLogin(String _login) {
		this.login = _login;
	}
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
	
	public void setId(String _pseudo)
	{
		this.pseudo = _pseudo;
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
