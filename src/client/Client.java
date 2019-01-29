package client;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Client {

	private String address;
	private int port;
	private Socket socket;
	private Thread threadSend;
	private ClientSend clientSend;
	private ObjectOutputStream out;
	
	public Client(String address, int port) {
		// D�finition des attributs pass�s
		this.address = address;
		this.port = port;
		
		try {
			// Connexion au serveur
			System.out.println(this.address);
			System.out.println(this.port);

			this.socket = new Socket(this.address, this.port);

			System.out.println(this.socket.getLocalAddress());
			System.out.println(this.socket.getPort());

			// Cr�ation du thread d'envoie
			this.out = new ObjectOutputStream(this.socket.getOutputStream());
			this.clientSend = new ClientSend(this.out);
			this.threadSend = new Thread(this.clientSend);
		} catch (IOException e) {
			// Si probl�me lors de connexion au serveur, on affiche un message et on ferme l'appli
			e.printStackTrace();/*
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Pendu");
			alert.setHeaderText(null);
			alert.setContentText("Le serveur n'est pas lanc�.");

			alert.showAndWait();
			
			System.exit(0);*/
		}
	}
	
	public void envoyer(Object element) {
		this.clientSend.envoyer(this.threadSend, element); 
	}

	public void disconnectedServer() {
		try {
			if(this.out != null)
				this.out.close();
			if(this.socket != null)
				this.socket.close();
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Socket getSocket() {
		return socket;
	}
}
