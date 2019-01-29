package commun;

import java.io.Serializable;
import java.util.List;

public class Theme implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String libelle;
	private List<Mot> mots;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) 
	{
		this.libelle = libelle;
	}

	public List<Mot> getMots() {
		return mots;
	}
	public void setMots(List<Mot> mots) {
		this.mots = mots;
	}

	
}
