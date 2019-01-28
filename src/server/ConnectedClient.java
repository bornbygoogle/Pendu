package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

import commun.Joueur;

public class ConnectedClient implements Runnable {
	
	private static int idCounter = 0;
	private int id;
	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	
	private Joueur joueur;
	
	private List<Joueur> lesJoueurs;
	
	
	public ConnectedClient(Socket socket, List<Joueur> lesJoueurs) {
		try {
			this.lesJoueurs = lesJoueurs;
			this.id = idCounter++;
			this.socket = socket;
			this.out = new ObjectOutputStream(this.socket.getOutputStream());
			System.out.println("Nouvelle connexion, id = " + this.id);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int getId() {
		return this.id;
	}

	@Override
	public synchronized void run() {
		try {
			this.in = new ObjectInputStream(socket.getInputStream());
			boolean isActive = true;
			while(isActive) {
				// On récupère les infos qui ont été envoyé via le client
				Object element = in.readObject();
				if(element != null) {
					// Si le client a envoyé une classe joueur, cela veut dire qu'il veut s'identifier
					if(element instanceof Joueur) {
						Joueur joueur = (Joueur)element;
						// On va vérifier que le joueur existe
						Joueur vraiJoueur = null;
						for(Joueur j : this.lesJoueurs) {
							if(j.getPseudo().equalsIgnoreCase(joueur.getPseudo()))
								vraiJoueur = j;
						}
						if(vraiJoueur != null) {
							if(joueur.getPass().equals(vraiJoueur.getPass())) {
								vraiJoueur.setStatus(true);
								this.joueur = vraiJoueur;
								this.envoyer(this.joueur);
							} else
								joueur.setMessage("Le mot de passe n'est pas valide.");
						} else
							joueur.setMessage("Vous n'êtes pas incrit.");
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean envoyer(Object element) {
		boolean verif = false;
		try {
			if(element != null) {
				this.out.writeObject(element);
				this.out.flush();
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return verif;
	}

	public void closeClient() {
		try {
			this.in.close();
			this.out.close();
			this.socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
