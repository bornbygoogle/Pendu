package commun;

import java.util.List;

public class Theme {

	private int id;
	private String libelle;
	private List<Mot> mots;
	
	public Theme(int id, String libelle) {
		this.id = id;
		this.libelle = libelle;
		//this.mots = mots;
	}

	public int getId()
	{
		return this.id;
	}

	public void setId(int _id)
	{
		this.id = _id;
	}

	public String getLibelle()
	{
		return this.libelle;
	}

	public void setLibelle(String _libelle)
	{
		this.libelle = _libelle;
	}

	public List<Mot> getMots()
	{
		return this.mots;
	}

	public void setMots(List<Mot> _mots)
	{
		this.mots = _mots;
	}
}
