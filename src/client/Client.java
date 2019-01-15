package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {

	private String address;
	private int port;
	private Socket socket;
	private Thread threadSend;
	private ClientSend clientSend;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	public Client(String address, int port) {
		this.address = address;
		this.port = port;
		try {
			this.socket = new Socket(this.address, this.port);
			this.out = new ObjectOutputStream(this.socket.getOutputStream());
			this.clientSend = new ClientSend(this.out);
			this.threadSend = new Thread(this.clientSend);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void envoyer(Object element) {
		this.clientSend.envoyer(this.threadSend, element); 
	}

	public void disconnectedServer() {
		try {
			if(this.in != null)
				this.in.close();
			if(this.out != null)
				this.out.close();
			if(this.socket != null)
				this.socket.close();
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
