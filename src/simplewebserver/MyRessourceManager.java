package simplewebserver;

import givenpackage.ContentType;
import givenpackage.HttpCommand;
import givenpackage.HttpStatus;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import create.AbstractSimpleRequest;
import create.AbstractSimpleResponse;
import create.RessourceManager;

public class MyRessourceManager implements RessourceManager {

	// Wurzelverzeichnis
	private String root;

	public MyRessourceManager(String string) {
		root = string; // Setzen des Wurzelverzeichnisses
	}

	@Override
	// File überprüfen: Datei oder Verzeichnis, existent oder nicht existent
	public void doGet(AbstractSimpleRequest request,
			AbstractSimpleResponse response) throws IOException {

		File f;

		// Pfad in File Objekt schreiben
		// Wenn RessourceURI = '/', dann Root
		if (request.getRessourceURI().equals("/")) {
			f = new File(root.replace('\\', '/'));
			System.out.println("Ist: ROOT");
			System.out.println(f);
		} else { // Sonst Root + RessourceURI
			f = new File(root.replace('\\', '/'), request.getRessourceURI());
			System.out.println("Ist nicht ROOT");
			System.out.println(f);
		}

		if (f.exists()) {
			if (f.isDirectory()) { // Wenn es sich um ein Verzeichnis handelt
				System.out.println("Ist ein Verzeichnis");
				writeDirectoryAsHtml(f, request, response);
			} else if (f.isFile()) { // Wenn es sich um eine Datei handelt
				System.out.println("Ist eine Datei");
				copyFile(f, response);
			}
		} else { // Wenn NOT_FOUND
			System.out.println("Verzeichnis und Datei nicht vorhanden");
			response.setHttpStatus(HttpStatus.NOT_FOUND);
			response.setContentType(ContentType.HTML);
			response.sendHeader();
			response.addToBody("<!DOCTYPE HTML> \n <html> <body> <img src=\"http://www.thinktraining.ca/portals/0/404_man.jpg\"></body> </html>");
			response.close();
		}
	}

	@Override
	// Überprüft auf GET Request
	public void doService(AbstractSimpleRequest request,
			AbstractSimpleResponse response) throws IOException {

		// Wenn Request Typ GET ist delegierung an doGet()
		if (request.getRequestType().equals(HttpCommand.GET) == true) {
			System.out.println("Ist GET");
			response.setHttpStatus(HttpStatus.OK);
			doGet(request, response);
		} else { // Wenn keine GET Request
			System.out.println("Kein GET-Request!");
			response.setHttpStatus(HttpStatus.BAD_REQUEST);
			response.sendHeader();
			response.addToBody("<!DOCTYPE HTML> \n <html> <body> <h1>Keine gültige GET Request. Nur GET Requests werden akzeptiert! <h1> </body> </html>");
			response.getOutputStream().close();
		}
	}

	@Override
	// Datei ausgeben
	public void copyFile(File file, AbstractSimpleResponse response)
			throws IOException {

		// Content Type aus Dateiendung ermitteln
		String a = Dateityp(file.getName());

		switch (a) {
		case "TXT":
			System.out.println("TXT");
			response.setContentType(ContentType.TEXT);
			break;
		case "HTML":
			System.out.println("HTML");
			response.setContentType(ContentType.HTML);
			break;
		case "MP3":
			System.out.println("MP3");
			response.setContentType(ContentType.MP3);
			break;
		case "MP4":
			System.out.println("MP4");
			response.setContentType(ContentType.MP4);
			break;
		case "JPG":
			System.out.println("JPG");
			response.setContentType(ContentType.JPEG);
			break;
		case "GIF":
			System.out.println("GIF");
			response.setContentType(ContentType.GIF);
			break;
		case "PNG":
			System.out.println("PNG");
			response.setContentType(ContentType.PNG);
			break;
		case "ICO":
			System.out.println("ICO");
			response.setContentType(ContentType.ICO);
			break;
		default:
			System.out.println("Ubekannter Typ");
			break;
		}

		response.sendHeader();

		// Datei kopieren
		FileInputStream fs = new FileInputStream(file);

		final byte[] buffer = new byte[1024];
		int count = 0;

		// Datei ausgeben
		while ((count = fs.read(buffer)) >= 0) {
			response.getOutputStream().write(buffer, 0, count); // Schreiben der
																// Datei zum
																// Outputstream
		}

		response.getOutputStream().flush();
		fs.close();
		response.getOutputStream().close(); // Outputstream schließen

		System.out.println("File ausgegeben");
	}

	// Gibt Dateityp zurück
	protected static String Dateityp(String fileName) {
		if (fileName.endsWith(".htm") || fileName.endsWith(".html")) {
			return "HTML";
		}
		if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")
				|| fileName.endsWith(".JPG") || fileName.endsWith(".JPEG")) {
			return "JPG";
		}
		if (fileName.endsWith(".mp4") || fileName.endsWith(".MP4")) {
			return "MP4";
		}
		if (fileName.endsWith(".mp3")) {
			return "MP3";
		}
		if (fileName.endsWith(".bmp")) {
			return "BMP";
		}
		if (fileName.endsWith(".txt")) {
			return "TXT";
		}
		if (fileName.endsWith(".ico")) {
			return "ICO";
		}
		if (fileName.endsWith(".css")) {
			return "CSS";
		}
		if (fileName.endsWith(".gif")) {
			return "GIF";
		} else
			return "Ordner";
	}

	@Override
	public void copyStreams(InputStream in, OutputStream out)
			throws IOException {

	}

	@Override
	// Ausgabe eines Verzeichnisses als HTML
	public void writeDirectoryAsHtml(File file, AbstractSimpleRequest request,
			AbstractSimpleResponse response) throws IOException {

		response.setContentType(ContentType.HTML);
		response.sendHeader();

		// Array, in dem Dateien und Ordner des Verzeichnisses gespeichert werden
		File[] farr = file.listFiles();

		// Stringbuilder um HTML Seite zu bauen
		StringBuilder htmlstringbuilder = new StringBuilder();
		htmlstringbuilder
				.append("<!DOCTYPE HTML> \n <html> \n <head>  <link rel=\"icon\"  href=\"favicon.ico\" /> \n <title>Run A Server</title> \n <meta name=\"author\" content=\"   \">  \n ");
		htmlstringbuilder
				.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\"/> </head> \n");
		htmlstringbuilder
				.append("<body> \n <div id=\"wrapper\"> \n <header> \n <h1>Simple Web-Server</h1> \n");
		htmlstringbuilder
				.append("<p><a href=\"./\"><button type=\"button\">Root-Directory</button></a> </p></header> \n");
		htmlstringbuilder
				.append("<table> \n <thead> \n <tr> <th>Datei</th> \n </thead> \n <tbody> \n");

		// Schreiben der Links zu den Dateien/Ordnern des Verzeichnisses in HTML
		for (int i = 0; i < farr.length; i++) {
			File f = farr[i];
			String str = f.getAbsolutePath();
			String[] path = str.split("htdocs");
			htmlstringbuilder.append("<tr> <td> <a href='"
					+ path[1].substring(1).replace('\\', '/')
							.replace(" ", "%20") + "'>" + f.getName());
		}

		htmlstringbuilder.append("<tbody> </table> \n"
				+ "</div> \n </body> \n </html>");

		// Senden der generierten HTML Seite
		response.addToBody(htmlstringbuilder.toString());

	}
}
