package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConnectedClient implements Runnable {
	
	private static int idCounter = 0;
	private int id;
	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private Server server; 
	
	public ConnectedClient(Server server, Socket socket) {
		try {
			this.server = server;
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
				/*
				Message mess = (Message) in.readObject();
				if(mess != null) {
					mess.setSender(String.valueOf(this.id));
					this.server.broadcastMessage(mess, this.id);
				} else {
					this.server.disconnectedClient(this);
					isActive = false;
				}
				*/
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*
	public void sendMessage(Message mess) {
		try {
			this.out.writeObject(mess);
			this.out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	*/
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
