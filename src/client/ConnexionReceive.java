package client;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import commun.Joueur;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ConnexionReceive implements Runnable {
	
	private Connexion connexion;
	
	private Client client;
	private Socket socket;
	private ObjectInputStream in;
	
	public ConnexionReceive(Connexion connexion) {
		this.connexion = connexion;
		this.client = this.connexion.getMain().getClient();
		this.socket = this.client.getSocket();
	}

	@Override
	public void run() {
		try {
			// Lancement de l'�coute du r�seau
			this.in = new ObjectInputStream(this.socket.getInputStream());
			// Tant que le joueur n'est pas connect�, ce theard tournera toujours
			while(this.connexion.getMain().isConnecte()) {
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
} 
