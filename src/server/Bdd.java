package server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import commun.Joueur;
import commun.Mot;
import commun.Theme;

public class Bdd 
{
	static Properties prop;
	static FileInputStream input;
	static String bddurl;
	static String user;
	static String passwd;
	static Connection connection = null;
	
	public Bdd() 
	{
    	// sigleyton connection
	}
	
	public static Connection getDBConnection() throws ClassNotFoundException, IOException, SQLException 
	{
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}
    	catch (ClassNotFoundException e) 
    	{
    		System.out.println("Need Oracle Driver!");
			e.printStackTrace();
		}

    	try {
        	prop = new Properties();
    		input = new FileInputStream("./Libs/bdd.properties");
	    	prop.load(input);

	    	bddurl = prop.getProperty("url");
	    	user = prop.getProperty("user");
	    	passwd = prop.getProperty("passwd");
    	} catch (FileNotFoundException e) {
    		System.out.println("Cant read file properties !");
			e.printStackTrace();
		}

    	connection = DriverManager.getConnection(bddurl, user, passwd);  

        if (connection != null) {
            System.out.println("You made it, take control your database now!");
        } else {
            System.out.println("Failed to make connection!");
        }
        return connection;
	}
	
	private List<Joueur> getListJoueurs() 
	{
		List<Joueur> lesJoueurs = new ArrayList<Joueur>();
		
		
		
		
		return lesJoueurs;
	}
	
	private static List<Theme> getListThemes() throws SQLException
	{
		List<Theme> lesThemes = new ArrayList<Theme>();
		
		Connection dbConnection = null;
		Statement statement = null;

		String selectThemeSQL = "SELECT ID, LIBELLE FROM PENDU_THEME";

		try 
		{
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();

			System.out.println(selectThemeSQL);

			// execute select SQL statement
			ResultSet rs = statement.executeQuery(selectThemeSQL);

			while (rs.next()) 
			{
				//Theme unTheme = new Theme();
				String themeid = rs.getString("ID");
				String themelibelle = rs.getString("LIBELLE");

				String selectMotsSQL = "SELECT ID, MOT FROM PENDU_MOT WHERE IDTHEME = " + themeid + "";
				System.out.println(selectMotsSQL);
				
				// execute select SQL statement
				ResultSet rs2 = statement.executeQuery(selectMotsSQL);
				
				List<Mot> mots = new ArrayList<Mot>();
				while (rs2.next()) 
				{
					 mots.add(new Mot((int)rs2.getString("ID"), rs2.getString("MOT"), themeid));
				}
				
				
				System.out.println("userid : " + themeid);
				System.out.println("username : " + username);

			}

		} catch (SQLException e) { System.out.println(e.getMessage());}
		finally 
		{
			if (statement != null) { statement.close(); }
			if (dbConnection != null) { dbConnection.close(); }
		}
		
		return lesThemes;
	}
}