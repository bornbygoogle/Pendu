package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import commun.DemandeServeur;
import commun.Joueur;
import commun.StatusJoueur;

public class ConnectedClient implements Runnable {
	
	private static int idCounter = 0;
	private int id;
	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	
	private Joueur joueur;
	private MainServer main;
	
	private boolean statut;
	
	
	public ConnectedClient(Socket socket, MainServer main) {
		try {
			this.main = main;
			this.id = idCounter++;
			this.socket = socket;
			this.in = new ObjectInputStream(socket.getInputStream());
			this.out = new ObjectOutputStream(this.socket.getOutputStream());
			this.statut = true;
			System.out.println("Nouveau client connectÃ© : " + this.id);
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
			while(this.statut) {
				// On recupere les infos qui ont ete envoyÃ© via le client
				Object element = in.readObject();
				if(element != null) {
					// Si le client a envoye une classe joueur, cela veut dire qu'il veut s'identifier
					if (element instanceof Joueur) {
						Joueur joueur = (Joueur)element;

						// On va vï¿½rifier que le joueur existe
						Joueur joueurLocal = null;
						for(Joueur j : this.main.getListeJoueurs()) {
							if(j.getPseudo().equalsIgnoreCase(joueur.getPseudo())) {
								joueurLocal = j;
							}
						}
						if(joueurLocal != null) {
							if(joueur.getPass().equals(joueurLocal.getPass())) {
								joueurLocal.setStatus(true);
								joueur = joueurLocal;
								this.joueur = joueurLocal;
							} else
								joueur.setMessage("Le mot de passe n'est pas valide.");
						} else
							joueur.setMessage("Vous n'etes pas incrit.");
						
						this.envoyer(joueur);
					// Si le joueur fait une DemandeServeur, on lui renvoie l'info qu'il demande
					} else if(element instanceof DemandeServeur) {
						DemandeServeur demande = (DemandeServeur)element;
						switch (demande) {
							case StatusPartie:
								// Renvoyer le status de la partie actuel
								this.envoyer(this.main.getStatusPartie());
								// On attend 0.5s avant d'essayer de lancer une partie si il n'y en a pas une dÃ©jÃ  en cours
								Thread.sleep(500);
								this.main.lancementJeu();
								break;
							case Quitter:
								// TODO -> Il faut traiter la dÃ©connexion du client (L'enlever d'une partie si il était dedans (Si une partie est en cours), voir si la partie s'arrête si il part, prévenir tout les autres clients en renvoyant la partie en cours à jour)
								// Le client ferme son appli
								this.statut = false;
								this.main.MAJPartie();
								this.main.getServer().disconnectedClient(this);
								break;
						}
					} else if(element instanceof StatusJoueur) {
						StatusJoueur status = (StatusJoueur)element;
						if(status.equals(StatusJoueur.Perdu)) {
							// TODO
						} else if(status.equals(StatusJoueur.Trouve)) {
							// TODO
						}
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
				verif = true;
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return verif;
	}

	public void closeClient() {
		try {
			System.out.println("Client " + this.id + " déconnecté.");
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

	public Joueur getJoueur() {
		return this.joueur;
	}
}
