package create;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import create.AbstractSimpleRequest;
import create.AbstractSimpleResponse;

public interface RessourceManager {

	/**
	 * Bearbeitet einen GET-Request. Wenn die geforderte Ressource nicht
	 * existiert, wird eine Response mit entsprechendem Fehlerstatus geliefert.
	 * Ist die Ressource ein Verzeichnis, wir eine HTML-Seite generiert, die den
	 * Inhalt des Verzeichnisses darstellt und navigierbar macht. Ist die
	 * Ressource eine Datei, wird sie über die Response ausgeliefert.
	 * 
	 * @param request
	 *            beinhaltet den Get-Request
	 * @param response
	 *            verwaltet die HTTP Response
	 * @throws IOException
	 *             bei Problemen mit der Socket-Verbindung
	 */
	void doGet(AbstractSimpleRequest request, AbstractSimpleResponse response)
			throws IOException;

	/**
	 * Wenn der Request ein GET-Request ist, wird die Bearbeitung an die
	 * doGet-Methode delegiert. Ansonsten wird eine HTTP-Reponse erstellt mit
	 * Status-Code INVALID_REQUEST.
	 * 
	 * @param request
	 *            beinhaltet den Request
	 * @param response
	 *            verwaltet die HTTP Response
	 * @throws IOException
	 *             bei Problemen mit der Socket-Verbindung
	 */
	void doService(AbstractSimpleRequest request,
			AbstractSimpleResponse response) throws IOException;

	/**
	 * Kopiert den Inhalt einer Datei unter Angabe des Content-Types und der
	 * Größe in die HTTP Response.
	 * 
	 * @param file
	 *            Datei, die zu kopieren ist
	 * @param response
	 *            verwaltet die HTTP-Response
	 * @throws IOException
	 *             bei Problemen mit der Socket-Verbindung
	 */
	void copyFile(File file, AbstractSimpleResponse response)
			throws IOException;

	/**
	 * Kopiert binär die Daten aus einem Eingabestrom in den Ausgabestrom.
	 * 
	 * @param in
	 *            Eingabestrom, in diesem Kontext üblicherweise ein
	 *            FileInputStream
	 * @param out
	 *            Ausgabestrom, in diesem Kontext üblicherweise der OutputStream
	 *            der Response, d.h. des Sockets.
	 * @throws IOException
	 */
	void copyStreams(InputStream in, OutputStream out) throws IOException;

	/**
	 * Stellt den Inhalt eines Verzeichnisses als HTML-Seite dar, so dass die
	 * Inhalte auch verlinkt sind.
	 * 
	 * @param file
	 *            Das darzustellende Verzeichnis
	 * @param request
	 *            beinhaltet den Request
	 * @param response
	 *            verwaltet die HTTP Response
	 * @throws IOException
	 *             bei Problemen mit der Socket-Verbindung
	 */
	void writeDirectoryAsHtml(File file, AbstractSimpleRequest request,
			AbstractSimpleResponse response) throws IOException;

}