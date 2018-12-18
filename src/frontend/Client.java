package frontend;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {

	private String address;
	private int port;
	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	public Client(String address, int port) {
		this.address = address;
		this.port = port;
		try {
			this.socket = new Socket(this.address, this.port);
			this.out = new ObjectOutputStream(this.socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		Thread threadClientSend = new Thread(new ClientSend(this.socket, this.out));
		threadClientSend.start();
		Thread threadClientReceive = new Thread(new ClientReceive(this, this.socket));
		threadClientReceive.start();
	}
	/*
	public void messageReceived(Message mess) {
		System.out.println("\nMessage reçu : " + mess.getContent());
	}
	*/
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
