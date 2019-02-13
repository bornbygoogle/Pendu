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
		this.partie.setStatusPartie(StatusPartie.EnAttenteJoueur);
	}
	
	public StatusPartie getStatusPartie() {
		return this.partie.getStatusPartie();
	}
	
	public void lancerPartie() {
		// Si une partie n'est pas dÈj‡ en cours
		if(this.partie.getStatusPartie().equals(StatusPartie.EnAttenteJoueur) || this.partie.getStatusPartie().equals(StatusPartie.Fini)) {
			int cptAuthentifier = 0;
			for(ConnectedClient client : this.main.getServer().getClients().keySet()) {
				if(client.getJoueur() != null)
					cptAuthentifier++;
			}
			// Si au moins 2 clients sont authentifi√©s
			if(cptAuthentifier >= 2) {
				// On lance une nouvelle partie
				this.partie.setStatusPartie(StatusPartie.ChargementPartie);
				this.definirPartie();
				// On envoie la partie aux joueur concern√©s
				this.envoyerPartie();
			} else {
				this.partie.setStatusPartie(StatusPartie.EnAttenteJoueur);
			}
		}
	}

	private void definirPartie() {
		// On ajoute les joueurs authentifi√©s √† la partie
		HashMap<Joueur, StatusJoueur> lesParticipants = new HashMap<Joueur, StatusJoueur>();
		for(ConnectedClient client : this.main.getServer().getClients().keySet()) {
			if(client.getJoueur() != null)
				lesParticipants.put(client.getJoueur(), StatusJoueur.EnJeu);
		}
		this.partie.setParticipants(lesParticipants);
		// On d√©finit le mot √† rechercher al√©atoirement
		Random rand = new Random();
		Theme randomTheme = this.main.getThemes().get(rand.nextInt(this.main.getThemes().size()));
		Mot randomMot = randomTheme.getMots().get(rand.nextInt(randomTheme.getMots().size()));
		this.partie.setMot(randomMot);
		this.partie.setStatusPartie(StatusPartie.EnCours);
	}

	private void envoyerPartie() {
		System.out.println(this.partie.getStatusPartie().name());
		for(Joueur j : this.partie.getParticipants().keySet()) {
			for(ConnectedClient client : this.main.getServer().getClients().keySet()) {
				if(client.getJoueur() != null && client.getJoueur().equals(j)) {
					client.envoyer(this.partie);
					break;
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
				// On vÈrifie qu'il reste au minimum 1 joueur en vie dans la partie sinon on stop tout et on rÈinitialise le jeu
				boolean verifEnVie = false;
				for(StatusJoueur s : this.partie.getParticipants().values()) {
					if(s.equals(StatusJoueur.EnJeu))
						verifEnVie = true;
				}
				if(!verifEnVie) {
					// On arrete la partie si plus de joueur en vie
					this.partie.setStatusPartie(StatusPartie.Fini);
				}
				this.envoyerPartie();
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
			for(Joueur j : this.partie.getParticipants().keySet())
				this.partie.getParticipants().replace(j, StatusJoueur.Perdu);
			this.partie.getParticipants().replace(unJoueur, StatusJoueur.Trouve);
			this.partie.setStatusPartie(StatusPartie.Fini);
			this.partie.setJoueurGagnant(unJoueur);
			this.envoyerPartie();
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
			if(!verifJoueurEnJeu) {
				// On arrÍte la partie
				this.partie.setStatusPartie(StatusPartie.Fini);
			}
			this.envoyerPartie();
		}
	}
}
