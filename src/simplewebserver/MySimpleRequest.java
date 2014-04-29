package simplewebserver;

import givenpackage.HttpCommand;
import givenpackage.InvalidRequestException;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.logging.Logger;

import create.AbstractSimpleRequest;

public class MySimpleRequest extends AbstractSimpleRequest {

	Logger logger = Logger.getLogger(ServOneClient.class.getName());

	// Konstruktor
	public MySimpleRequest(Socket socket) throws IOException {
		this.socket = socket;
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		headers = new HashMap<String, String>();
	}

	@Override
	public void parseRequest() throws IOException, InvalidRequestException {

		// Weise line die erste Zeile des InputStreams zu
		String line = in.readLine();

		// Analysiere die Statuszeile des HTTP Requests
		ScanFirstLine(line);

		System.out.println("Ausgabe des HTTP Request");

		// Restlichen HTTP Request ausgeben und in HashMap sichern
		int c = 1;
		while (!line.equals("")) {
			System.out.println(c + ".: " + line);
			c++;
			line = in.readLine();
			ScanHeader(line); // Methode um Header in HashMap zu schreiben
		}

		System.out.println();
		System.out.println("Ausgabe der Header und Parameter in der HashMap");

		// Ausgabe der HashMap mit den Header Daten
		for (String name : headers.keySet()) {
			System.out.println("KEY: " + name + "   VALUE: "
					+ headers.get(name));
		}

	}

	// HTTP Statuszeile analysieren
	void ScanFirstLine(String line) {
		String[] parts = line.split("\\s");
		System.out.println("Statutszeile der Request zerlegt:");
		for (int i = 0; i < parts.length; i++) {
			System.out.println(i + ": " + parts[i]);
		}

		if (parts.length != 3) {
			System.out.println("--> Keine 3 String Teile");
			return;
		} else {
			System.out.println("---> Checked: 3 String Teile");
		}

		System.out.println();

		// Zuweisen des Request Typs
		switch (parts[0]) {
		case "GET":
			this.requestType = HttpCommand.GET;
			break;
		case "POST":
			this.requestType = HttpCommand.POST;
			break;
		case "PUT":
			this.requestType = HttpCommand.PUT;
			break;
		case "DELETE":
			this.requestType = HttpCommand.DELETE;
			break;
		case "HEAD":
			this.requestType = HttpCommand.HEAD;
			break;
		case "OPTIONS":
			this.requestType = HttpCommand.OPTIONS;
			break;
		default:
			this.requestType = HttpCommand.POST; // Default, wenn Request Typ
													// nicht Angegeben erfolgt
													// Zuweisung eines nicht
													// unterstützten Request
													// Typs
			break;
		}

		this.ressourceURI = parts[1]; // Zuweisen der URI
		this.protocolType = parts[2]; // Zuweisen des HTTP Typs
	}

	void ScanHeader(String line) {
		String[] parts = line.split(":", 20);

		try {
			headers.put(parts[0], parts[1]);
			// System.out.println("Hashmap added: " + parts[0]+" "+ parts[1]);
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
}
