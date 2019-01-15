package server;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import commun.*;

public class MainServer
{
	private static int port = 1025;

	private static Connection connection = null;

	public static void main(String[] argv) throws SQLException 
	{
		Server server = new Server(port);

		connection = ConnectionBDD.getInstance();
		List<Theme> themes = Methods.getListThemes(connection);
		List<Joueur> joueurs = Methods.getListJoueurs(connection);

		System.out.println(Methods.getMot(themes.get(1).getMots()));

		Joueur unJoueur = new Joueur();
		unJoueur.setPseudo("dylan");

		if (un)
		if(Methods.getJoueurStatus(unJoueur))
			System.out.println("true");

	}

}