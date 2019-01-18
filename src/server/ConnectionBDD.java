package server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionBDD
{
	private static Properties _prop;
	private static FileInputStream _input;
	private static String _bddurl;
	private static String _user;
	private static String _passwd;
	public static Connection connection = null;
	
	private ConnectionBDD() throws ClassNotFoundException, IOException, SQLException 
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
			_prop = new Properties();
			_input = new FileInputStream("./Libs/bdd.properties");
			_prop.load(_input);

			_bddurl = _prop.getProperty("url");
			_user = _prop.getProperty("user");
			_passwd = _prop.getProperty("passwd");
		} catch (FileNotFoundException e) {
			System.out.println("Cant read file properties !");
			e.printStackTrace();
		}

		connection = DriverManager.getConnection(_bddurl, _user, _passwd);
	}
	
	public static Connection getInstance() 
	{
		// singleton connection
		if(connection == null){
			try {
				new ConnectionBDD();
			} catch (ClassNotFoundException | IOException | SQLException e) {
				e.printStackTrace();
			}
		}
		return connection;
	}
}