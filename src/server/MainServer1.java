package server;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import commun.*;

public class MainServer1
{

	private static Connection connection = null;

	public static void main(String[] argv) throws SQLException 
	{

		connection = ConnectionBDD.getInstance();
		List<Theme> themes = Methods.getListThemes(connection);
		List<Joueur> joueurs = Methods.getListJoueurs(connection);

	}

}