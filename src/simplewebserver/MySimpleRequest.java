package simplewebserver;

import givenpackage.HttpCommand;
import givenpackage.InvalidRequestException;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Logger;

import create.AbstractSimpleRequest;

public class MySimpleRequest extends AbstractSimpleRequest {

	Logger logger = Logger.getLogger(ServOneClient.class.getName());

	// Konstruktor
	public MySimpleRequest(Socket socket) throws IOException {
		this.socket = socket;
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}

	@Override
	public void parseRequest() throws IOException, InvalidRequestException {

		 String line = null;
		    boolean firstline = true;
//		    lies solange vom Socket bis nichts mehr darin steht ..
		    while( ( line = in.readLine() ) != null ) {
		    	if (line.equals("")){ 
		    		break; 
		    	}
		        System.out.println(">>>>" + line );
		        if(firstline){
		        	firstline = false ;
		        	scanFirstLine(line);
		        }
		    }
	}

	// HTTP Statuszeile analysieren
	private void scanFirstLine(String line) throws InvalidRequestException {
		String[] parts = line.split("\\s");
		
		System.out.println("Statuszeile Request");
		for (int i = 0; i < parts.length; i++) {
			System.out.println(i + ": " + parts[i]);
		}

		if(parts.length!= 3){
			throw new InvalidRequestException("autsch es werden " + parts.length + " Teile gefunden.");
		}

		System.out.println("----------------");

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
		}

		this.ressourceURI = parts[1];
		this.protocolType = parts[2];
	}

}
