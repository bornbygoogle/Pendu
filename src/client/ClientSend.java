package client;

import java.io.ObjectOutputStream;

public class ClientSend implements Runnable {
	
	private ObjectOutputStream out;
	private Object element;
	
	public ClientSend(ObjectOutputStream out) {
		this.out = out;
	}

	@Override
	public void run() {
		try {
			if(this.element != null) {
				out.writeObject(this.element);
				out.flush();
				this.element = null;
			} else {
				System.out.println("Un �l�ment vide a tent� d'�tre envoy� !");
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public boolean envoyer(Thread thread, Object element) {
		boolean verif = false;
		try {
			if(element != null) {
				this.element = element;
				thread.start();
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return verif;
	}
}
