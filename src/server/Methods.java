package server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

		String selectJoueurSQL = "SELECT PSEUDO, PASS, SCORE, DATEINSCRIPTION, DATEDERNIERECO NBPARTIES FROM PENDU_JOUEUR";
		ResultSet rs = SQLQuery(connectionDB, selectJoueurSQL);

		while (rs.next()) 
		{
			Joueur unJoueur = new Joueur();

			unJoueur.setPseudo(rs.getString("PSEUDO"));
			unJoueur.setPseudo(rs.getString("PASS"));
			unJoueur.setDate
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

	public static String getMot(List<Mot> mots)
	{
		Random randomGenerator = new Random();
		int index = randomGenerator.nextInt(mots.size());
		return mots.get(index).getMot().toUpperCase();
	}

// status true --> renvoie les infos : nbr de parties
// status false --> mise a jour message : "Login failed !"

	private static String getJoueurPass(Connection _connection, String _pseudo) throws SQLException
	{
		String selectUserPassSQL = "SELECT PASS FROM PENDU_JOUEUR WHERE PSEUDO = '" + _pseudo + "'";
		Statement statement = _connection.createStatement();
		ResultSet rs = statement.executeQuery(selectUserPassSQL);
		if (/*rs.first() &&*/ rs.next())
		{
			System.out.println(rs.getString("PASS").toString());
			return rs.getString("PASS").toString();
		}
		return null;
	}

	public static boolean getJoueurStatus(Joueur unJoueur) throws SQLException
	{
		Connection connection = null;
		connection = ConnectionBDD.getInstance();
		System.out.println(unJoueur.getPseudo().toString());
		if (unJoueur.getPass().equals(getJoueurPass(connection, unJoueur.getPseudo())))
		{
			System.out.println("true");
			return true;
		}
		else
		{
			System.out.println("false");
			return true;
		}
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
