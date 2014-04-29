package simplewebserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import givenpackage.ConfigurationException;

public class SimpleWebServer {

	private static final int STANDARD_PORT = 8080;

	public SimpleWebServer() {
	}

	public static void main(String[] args) throws IOException,
			ConfigurationException {
		int port = STANDARD_PORT;
		MyRessourceManager ressourceManager = new MyRessourceManager(
				"./testdata/htdocs");
		ServerSocket welcome = new ServerSocket(port);
		while (true) {
			Socket socket = welcome.accept();
			new ServOneClient(socket, ressourceManager);
		}
	}

}
