package server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import commun.Joueur;

public class Server {

	private int port;
	
	private boolean serverRunning;

	private Connection conn;

	private List<ConnectedClient> clients;
	
	public Server(List<Joueur> lesJoueurs, int _port) throws IOException 
	{
		this.port = _port;
		this.clients = new ArrayList<ConnectedClient>();
		System.out.println("Connect to server port " + port);

		conn = new Connection(lesJoueurs, this);
		Thread threadConnection = new Thread(conn);
		threadConnection.start();
	}
	
	public int getPort() {
		return this.port;
	}
	
	public void addClient(ConnectedClient newClient) {
		for(ConnectedClient client : this.clients) {
			//client.sendMessage(new Message("Le client " + newClient.getId() + " vient de se connecter"));
			System.out.println("Le client " + newClient.getId() + " vient de se connecter");
		}
		this.clients.add(newClient);
	}

	/*public void broadcastMessage(Message mess, int id) {
		for(ConnectedClient client : this.clients) {
			if(client.getId() != id) {
				client.sendMessage(mess);
			}
		}
	}

		public void broadcastMessage(Message message) {
		for (ConnectedClient client : clients) {
			client.sendMessage(message);
		}
	}

	public void broadcastMessageExceptSender(Message message, int id) {
		for (ConnectedClient client : clients) {
			if (client.getId() != id) {
				client.sendMessage(message);
			}
		}
	}*/

	public void stopServerRunning()
	{
		System.out.println("I'm here in stopServerRunning !");
		if (!(clients.isEmpty())) {
			for(ConnectedClient client : this.clients)
				disconnectedClient(client);
			this.clients.clear();
		}
		this.conn.arret();
	}

	public void disconnectedClient(ConnectedClient discClient) {
		discClient.closeClient();
		//this.conn.arret();
		/*Message mess = new Message("Le client " + discClient.getId() + " nous a quitt�");
		mess.setSender("server");
		for(ConnectedClient client : this.clients)
			client.sendMessage(mess);
		*/
	}
}
