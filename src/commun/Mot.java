package commun;

import java.io.Serializable;

public class Mot implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -280041720314104803L;
	private int id;
	private String mot;
	private Theme theme = new Theme();
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMot() {
		return mot;
	}
	public void setMot(String mot) {
		this.mot = mot;
	}
	public Theme getTheme() {
		return theme;
	}
	public void setTheme(Theme theme) {
		this.theme = theme;
	}
}
