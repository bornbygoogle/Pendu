package server;

import java.util.ArrayList;
import java.util.List;

public class Server {

	private int port;
	private List<ConnectedClient> clients;
	
	public Server(int port) {
		this.port = port;
		System.out.println("Connect to server port " + port);
		/*this.clients = new ArrayList<ConnectedClient>();
		Thread threadConnection = new Thread(new Connection(this));
		threadConnection.start();*/
	}
	
	public int getPort() {
		return this.port;
	}
	
	public void addClient(ConnectedClient newClient) {
		/*
		for(ConnectedClient client : this.clients) {
			client.sendMessage(new Message("Le client " + newClient.getId() + " vient de se connecter"));
		}
		*/
		this.clients.add(newClient);
	}
	/*
	public void broadcastMessage(Message mess, int id) {
		for(ConnectedClient client : this.clients) {
			if(client.getId() != id) {
				client.sendMessage(mess);
			}
		}
	}
	*/
	public void disconnectedClient(ConnectedClient discClient) {
		discClient.closeClient();
		this.clients.remove(discClient);
		/*
		Message mess = new Message("Le client " + discClient.getId() + " nous a quitt�");
		mess.setSender("server");
		for(ConnectedClient client : this.clients)
			client.sendMessage(mess);
		*/
	}
}
