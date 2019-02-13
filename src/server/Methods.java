package server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import commun.Joueur;
import commun.Mot;
import commun.Theme;
import commun.Utils;

public class Methods 
{
	private static Statement statement = null;

	public Methods() {}
	
	public static List<Joueur> getListJoueurs(Connection connectionDB) throws SQLException
	{
		List<Joueur> lesJoueurs = new ArrayList<Joueur>();

		String selectJoueurSQL = "SELECT PSEUDO, PASS, SCORE, DATEINSCRIPTION, DATEDERNIERECO, NBPARTIES FROM PENDU_JOUEUR";
		ResultSet rs = SQLQuery(connectionDB, selectJoueurSQL);

		while (rs.next()) 
		{
			Joueur unJoueur = new Joueur();

			unJoueur.setPseudo(rs.getString("PSEUDO"));
			//System.out.println(unJoueur.getPseudo());
			unJoueur.setPass(rs.getString("PASS"));
			//TODO : convertion date to String
			//unJoueur.setDateDernierCo(rs.getDate("DATEDERNIERECO"));
			unJoueur.setNbParties(Integer.valueOf(rs.getString("NBPARTIES")));
			unJoueur.setNbScore(Integer.valueOf(rs.getString("SCORE")));

			lesJoueurs.add(unJoueur);
		}
		SQLStatementClean();
		return lesJoueurs;
	}

	public static void updateJoueur(Connection connectionDB, List<Joueur> nouveauxJoueurs) throws SQLException
	{
		int rowCount = 0;

		for (Joueur j : nouveauxJoueurs)
		{
			while (rowCount == 0)
			{
				statement = connectionDB.createStatement();
				statement.executeUpdate("INSERT INTO PENDU_JOUEUR (pseudo, pass, dateInscription, dateDerniereCo, score, nbParties) " +
										"VALUES ('" + j.getPseudo() + "', '" + j.getPass() + "', to_date( '" + Utils.getCurrentTimeUsingCalendar() + "' ,'dd/MM/YYYY') ', '" + Utils.getCurrentTimeUsingCalendar() + "', 0, 0)");
				if (statement != null)
					statement.close();

				// Verification l'ajout de joueur
				String selectJoueurSQL = "SELECT PSEUDO FROM PENDU_JOUEUR WHERE PSEUDO = '" + j.getPseudo() + "'";
				ResultSet rs = SQLQuery(connectionDB, selectJoueurSQL);
				while (rs.next()) 
					rowCount = rs.getRow();
					
				SQLStatementClean();
			}
		}
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

	public static String getMot(List<Mot> mots)
	{
		Random randomGenerator = new Random();
		int index = randomGenerator.nextInt(mots.size());
		return mots.get(index).getMot().toUpperCase();
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
