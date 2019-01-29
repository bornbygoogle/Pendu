package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class PartieReceive implements IReceive {
	
	private Client client;
	private Socket socket;
	private ObjectInputStream in;
	
	public PartieReceive(MainGUI main) {
		this.client = main.getClient();
		this.socket = this.client.getSocket();
		try {
			this.in = new ObjectInputStream(this.socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Object attenteReponse() {
		try {
			return this.in.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Pendu");
			alert.setHeaderText(null);
			alert.setContentText("Impossible de joindre le serveur.");

			alert.showAndWait();
		}
		return null;
	}

	/*
	@Override
	public void run() {
		try {
			// Lancement de l'�coute du r�seau
			this.in = new ObjectInputStream(this.socket.getInputStream());
			// Tant que le joueur n'est pas connect�, ce theard tournera toujours
			while(this.main.isConnecte()) {
				// R�cup�ration de l'objet envoy� par le serveur
				Object element = in.readObject();
				// Si cet objet n'est pas null et que c'est un objet de type joueur, sinon on attend que ce soit bien un objet de type joueur qui nous est renvoy�
				if(element != null && element instanceof Joueur)
					// Verif de la connexion
					this.connexion.verifierReponseConnexion((Joueur)element);
			}
			// Lorsque la connexion est �tablie (c'est � dire que le bool connexion est pass� � true), la boucle est stopp� et le theard ne fait plus rien
		} catch(EOFException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Pendu");
			alert.setHeaderText(null);
			alert.setContentText("Impossible de joindre le serveur.");

			alert.showAndWait();
		}
	}
	*/
	
	public void close() {
		try {
			this.in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}