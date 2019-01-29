package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import commun.Joueur;

public class Connection implements Runnable {

	private List<Joueur> lesJoueurs;
	
	private Server server;
	private ServerSocket serverSocket;

    protected volatile boolean running = true;
	
	public Connection(List<Joueur> lesJoueurs, Server server) {
		this.lesJoueurs = lesJoueurs;
		this.server = server;
		try {
			this.serverSocket = new ServerSocket(server.getPort());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void arret() {
		this.running = false;
	}

	@Override
	public synchronized void run() {
		System.out.println("Server run");
		while(running) {
			//this.running = false;

			try {

				Socket sockNewClient = this.serverSocket.accept();

				System.out.println("Addresse : " + sockNewClient.getLocalAddress() + " port : " + sockNewClient.getPort());
				
				
				
				ConnectedClient newClient = new ConnectedClient(sockNewClient, this.lesJoueurs);
				this.server.addClient(newClient);
				Thread threadNewClient = new Thread(newClient);
				threadNewClient.start();
				if (!running)
					System.out.println("Server will off");
			} catch (Exception e) {
				e.printStackTrace();
			}
			finally
			{
				this.running = false;
			}
		}
	}
}
