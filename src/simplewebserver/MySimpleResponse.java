package simplewebserver;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.io.PrintStream;

import create.AbstractSimpleResponse;

public class MySimpleResponse extends AbstractSimpleResponse {

	// Konstruktor
	public MySimpleResponse(Socket socket) throws IOException {
		this.socket = socket;
		out = new PrintStream(
				new BufferedOutputStream(socket.getOutputStream()));
	}

	@Override
	// String der Response hinzufügen
	public void addToBody(String string) throws IOException {
		if (headerSend == false) {
			sendHeader();
		}
		if (headerSend == true) { // Wenn Header gesendet, dann sende auch Body
			System.out.println("Header gesendet, Body gesendet");
			out.println(string);
			out.flush();
		} else {
			System.out.println("Header und Body >nicht< gesendet");
		}

	}

	@Override
	public void sendHeader() throws IOException {
		
		if (headerSend == false) {
		
		out.write((this.myProtocolType + " " + this.status.getCode() + " "
				+ this.status + "\r\n").getBytes());
		System.out.println(this.myProtocolType + " " + this.status.getCode()
				+ " " + this.status);

		if (this.contentType != null) { // Da Falls Content-Type Null eine
										// NullPointerException Auftritt,
										// Content-Type also nur ausgeben, wenn
										// tatsächlich ein Content-Type
										// ermittelt wurde
			out.write(("Content-Type: " + this.contentType.getContentString() + "\r\n" + "\r\n").getBytes());
			System.out.println("Content-Type: " + this.contentType.getContentString() + ", utf-8");
		}
		headerSend = true;
		}
	}

}
