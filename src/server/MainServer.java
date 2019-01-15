package server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import java.sql.*;
import java.sql.Connection;

import commun.Joueur;
import commun.Theme;

public class MainServer
{
	//private Bdd connBdd;
	FileInputStream		input;
	//private List<Joueur> joueurs;
	private List<Theme> themes;

	public void main(String[] args) throws IOException, SQLException, ClassNotFoundException 
	{
    	//connBdd = new Bdd();
    	//connBdd.getConnection();
			/*
			  //step3 create the statement object  
			 
			Statement stmt=con.createStatement();  
			  
			//step4 execute query  
			ResultSet rs=stmt.executeQuery("select * from emp");  
			while(rs.next())  
			System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));  
			  
			//step5 close the connection object  
			con.close(); 
			  
			}catch(Exception e){ System.out.println(e);}  */
	}
	
	/*private static void printUsage() {
		System.out.println("java server.Server <port>");
		System.out.println("\t<port>: server's port");
	}*/
} 