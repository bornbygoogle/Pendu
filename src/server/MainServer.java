package server;

public class MainServer {

	public static void main(String[] args) {
		if(args.length != 1) {
			printUsage();
		} else {
			Integer port = new Integer(args[0]);
			new Server(port);
		}
	}
	
	private static void printUsage() {
		System.out.println("java server.Server <port>");
		System.out.println("\t<port>: server's port");
	}
}
