package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.net.Socket;

import commun.Partie;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Client {

	private String address;
	private int port;
	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	
	public Client(String address, int port) {
		
		// D�finition des attributs pass�s
		this.address = address;
		this.port = port;
		
		try {
			// Connexion au serveur
			this.socket = new Socket(this.address, this.port);

			// Cr�ation du thread d'envoie
			
			this.out = new ObjectOutputStream(this.socket.getOutputStream());
			
		} catch (IOException e) {
			// Si probl�me lors de connexion au serveur, on affiche un message et on ferme l'appli
			e.printStackTrace();
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Pendu");
			alert.setHeaderText(null);
			alert.setContentText("Le serveur n'est pas lanc�.");

			alert.showAndWait();
			
			System.exit(0);
		}
	
	}
	
	public Object attenteReponse() throws IOException, ClassNotFoundException, StreamCorruptedException {
		if(this.in == null)
			this.in = new ObjectInputStream(this.socket.getInputStream());
		Object reponse = this.in.readObject();
		return reponse;
	}
	
	public boolean envoyer(Object element) {
		boolean verif = false;
		try {
			if(element != null) {
				this.out.writeObject(element);
				this.out.flush();
				verif = true;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return verif;
	}

	public void disconnectedServer() {
		try {
			if(this.in != null)
				this.in.close();
			if(this.out != null)
				this.out.close();
			if(this.socket != null)
				this.socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
