package commun;

import java.util.List;

public class Theme {

	private int id;
	private String libelle;
	private List<Mot> mots;
	
	public Theme(int id, String libelle, List<Mot> mots) {
		this.id = id;
		this.libelle = libelle;
		this.mots = mots;
	}
}
