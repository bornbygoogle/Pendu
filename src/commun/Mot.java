package commun;

public class Mot {
	
	private int id;
	private String mot;
	private Theme theme;
	
	public Mot(int id, String mot, Theme theme) {
		this.id = id;
		this.mot = mot;
		this.theme = theme;
	}

	public int getId()
	{
		return this.id;
	}

	public void setId(int _id)
	{
		this.id = _id;
	}

	public String getMot()
	{
		return this.mot;
	}

	public void setLibelle(String _mot)
	{
		this.mot = _mot;
	}

	public Theme getTheme()
	{
		return this.theme;
	}

	public void setTheme(Theme _theme)
	{
		this.theme = _theme;
	}

}
