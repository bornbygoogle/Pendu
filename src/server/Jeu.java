package server;

import java.util.HashMap;
import java.util.Random;

import commun.Joueur;
import commun.Mot;
import commun.Partie;
import commun.StatusJoueur;
import commun.StatusPartie;
import commun.Theme;

public class Jeu {
	
	private MainServer main;
	private Partie partie;
	
	public Jeu(MainServer main) {
		this.main = main;
		this.partie = new Partie();
	}
	
	public Partie getPartie() {
		return this.partie;
	}
	
	public void lancerPartie() {
		// Si une partie n'est pas déjà en cours
		if(this.partie.getStatusPartie().equals(StatusPartie.EnAttenteJoueur) || this.partie.getStatusPartie().equals(StatusPartie.Fini)) {
			int cptAuthentifier = 0;
			for(ConnectedClient client : this.main.getServer().getClients().keySet()) {
				if(client.getJoueur() != null)
					cptAuthentifier++;
			}
			// Si au moins 2 clients sont authentifiÃ©s
			if(cptAuthentifier >= 2) {
				// On lance une nouvelle partie
				this.partie.setStatusPartie(StatusPartie.ChargementPartie);
				this.definirPartie();
				// On envoie la partie aux joueur concernÃ©s
				this.envoyerPartie();
			} else {
				this.partie.setStatusPartie(StatusPartie.EnAttenteJoueur);
			}
		}
	}

	private void definirPartie() {
		// On ajoute les joueurs authentifiÃ©s Ã  la partie
		HashMap<Joueur, StatusJoueur> lesParticipants = new HashMap<Joueur, StatusJoueur>();
		for(ConnectedClient client : this.main.getServer().getClients().keySet()) {
			if(client.getJoueur() != null)
				lesParticipants.put(client.getJoueur(), StatusJoueur.EnJeu);
		}
		this.partie.setParticipants(lesParticipants);
		// On dÃ©finit le mot Ã  rechercher alÃ©atoirement
		Random rand = new Random();
		Theme randomTheme = this.main.getThemes().get(rand.nextInt(this.main.getThemes().size()));
		Mot randomMot = randomTheme.getMots().get(rand.nextInt(randomTheme.getMots().size()));
		this.partie.setMot(randomMot);
		this.partie.setStatusPartie(StatusPartie.EnCours);
	}

	private void envoyerPartie() {
		for(Joueur j : this.partie.getParticipants().keySet()) {
			for(ConnectedClient client : this.main.getServer().getClients().keySet()) {
				if(client.getJoueur().equals(j)) {
					client.envoyer(this.partie);
				}
			}
		}
	}
	
	public void decoJoueur(Joueur unJoueur) {
		boolean verifExiste = false;
		for(Joueur j : this.partie.getParticipants().keySet()) {
			if(j.equals(unJoueur))
				verifExiste = true;
		}
		if(verifExiste) {
			this.partie.getParticipants().remove(unJoueur);
			if(this.partie.getStatusPartie().equals(StatusPartie.EnCours)) {
				// On vérifie qu'il reste au minimum 1 joueur en vie dans la partie sinon on stop tout et on réinitialise le jeu
				boolean verifEnVie = false;
				for(StatusJoueur s : this.partie.getParticipants().values()) {
					if(s.equals(StatusJoueur.EnJeu))
						verifEnVie = true;
				}
				// Si joueur encore en partie, on envoie la partie mise à jour
				if(verifEnVie) {
					this.envoyerPartie();
				} else {
					// Sinon on arrête la partie
					this.partie.setStatusPartie(StatusPartie.Fini);
					try {
						Thread.sleep(1500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					this.lancerPartie();
				}
			}
		}
	}
	
	public void joueurGagne(Joueur unJoueur) {
		boolean verifExiste = false;
		for(Joueur j : this.partie.getParticipants().keySet()) {
			if(j.equals(unJoueur))
				verifExiste = true;
		}
		if(verifExiste) {
			for(Joueur j : this.partie.getParticipants().keySet()) {
				this.partie.getParticipants().replace(j, StatusJoueur.Perdu);
				j.setMessage("message test");
			}
			this.partie.getParticipants().replace(unJoueur, StatusJoueur.Trouve);
			this.partie.setStatusPartie(StatusPartie.Fini);
			this.partie.setJoueurGagnant(unJoueur);
			this.envoyerPartie();
			/*try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.lancerPartie();*/
		}
	}
	
	public void joueurPerdu(Joueur unJoueur) {
		boolean verifExiste = false;
		for(Joueur j : this.partie.getParticipants().keySet()) {
			if(j.equals(unJoueur))
				verifExiste = true;
		}
		if(verifExiste) {
			this.partie.getParticipants().replace(unJoueur, StatusJoueur.Perdu);
			// Si il reste des joueurs en jeu
			boolean verifJoueurEnJeu = false;
			for(Joueur j : this.partie.getParticipants().keySet()) {
				if(this.partie.getParticipants().get(j).equals(StatusJoueur.EnJeu)) {
					verifJoueurEnJeu = true;
				}
			}
			if(verifJoueurEnJeu) {
				this.envoyerPartie();
			} else {
				// On arrête la partie
				this.partie.setStatusPartie(StatusPartie.Fini);
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				this.lancerPartie();
			}
		}
	}
}
