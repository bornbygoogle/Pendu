package server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;

import commun.Joueur;
import commun.Mot;
import commun.Theme;

public class Methods 
{
	private static Statement statement = null;

	public Methods() {}
	
	public static List<Joueur> getListJoueurs(Connection connectionDB) throws SQLException
	{
		List<Joueur> lesJoueurs = new ArrayList<Joueur>();

		String selectJoueurSQL = "SELECT ID, LOGIN, PSEUDO, PASS, SCORE, NBPARTIES FROM PENDU_THEME";
		ResultSet rs = SQLQuery(connectionDB, selectJoueurSQL);

		while (rs.next()) 
		{
			Joueur unJoueur = new Joueur();

			unJoueur.setId(Integer.valueOf(rs.getString("ID")));
			unJoueur.setLogin(rs.getString("LOGIN"));
			unJoueur.setPseudo(rs.getString("PSEUDO"));
			unJoueur.setPass(rs.getString("PASS"));
			unJoueur.setNbParties(Integer.valueOf(rs.getString("NBPARTIES")));
			unJoueur.setNbScore(Integer.valueOf(rs.getString("SCORE")));

			lesJoueurs.add(unJoueur);
		}
		SQLStatementClean();
		return lesJoueurs;
	}
	
	public static List<Theme> getListThemes(Connection connectionDB) throws SQLException
	{
		List<Theme> lesThemes = new ArrayList<Theme>();
		
		String selectThemeSQL = "SELECT ID, LIBELLE FROM PENDU_THEME";
		ResultSet rs = SQLQuery(connectionDB, selectThemeSQL);

		while (rs.next()) 
		{
			Theme unTheme = new Theme();
			unTheme.setId(Integer.valueOf(rs.getString("ID")));
			unTheme.setLibelle(rs.getString("LIBELLE"));
			List<Mot> mots = getListMots(connectionDB, unTheme);
			unTheme.setMots(mots);	
			lesThemes.add(unTheme);
		}
		SQLStatementClean();
		return lesThemes;
	}

	public static List<Mot> getListMots(Connection connectionDB, Theme unTheme) throws SQLException
	{
		List<Mot> mots = new ArrayList<>();

		String selectMotsSQL = "SELECT ID, MOT FROM PENDU_MOT WHERE IDTHEME = " + unTheme.getId();
		ResultSet rs = SQLQuery(connectionDB, selectMotsSQL);

		while (rs.next())
		{
			Mot unMot = new Mot();
			unMot.setId(Integer.valueOf(rs.getString("ID")));
			unMot.setMot(rs.getString("MOT"));
			unMot.setTheme(unTheme);
			mots.add(unMot);
		}
		SQLStatementClean();
		return mots;
	}

	private static ResultSet SQLQuery(Connection connectionDB, String requeteSQL) throws SQLException
	{
		statement = connectionDB.createStatement();
		ResultSet rs = statement.executeQuery(requeteSQL);
		return rs;
	}

	private static void SQLStatementClean() throws SQLException
	{
		if (statement != null) {
			statement.close();
		}
	}
}
