package create;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Logger;

import givenpackage.ContentType;
import givenpackage.HttpStatus;

public abstract class AbstractSimpleResponse {

	private static Logger logger = Logger
			.getLogger(AbstractSimpleResponse.class.getName());
	protected static final String CONTENT_TYPE = "content-type";
	protected HttpStatus status = HttpStatus.OK;
	boolean statusInitialized = false;
	protected Socket socket;
	protected PrintStream out;
	protected String myProtocolType = "HTTP/1.0";
	protected ContentType contentType;
	protected boolean headerSend;

	public AbstractSimpleResponse() {
		super();
	}

	public void setHttpStatus(HttpStatus status) {
		this.status = status;
	}

	public void close() {
		logger.info("closing socket and co ...");
		out.println();
		out.println();
		out.close();
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void setContentType(ContentType type) {
		logger.info("Content-Type to be set to " + type.getContentString());
		this.contentType = type;
	}

	public OutputStream getOutputStream() throws IOException {
		return socket.getOutputStream();
	}

	/**
	 * Fügt den String dem Body der Response hinzu. Die Implementierung stellt
	 * sicher, dass vor der Ausgabe des Strings in die Response der Header
	 * gesendet wurde.
	 * 
	 * @param string
	 *            String, der dem Body hinzugefügt werden soll
	 * @throws IOException
	 *             bei Problemen mit der Socket-Verbindung
	 */
	public abstract void addToBody(String string) throws IOException;

	/**
	 * Sendet die Statuszeile der Response und die nachfolgenden Header-Daten,
	 * insbesondere den Content-Typ. Der Header darf nur einmal gesendet werden.
	 * 
	 * @throws IOException
	 *             bei Problemen mit der Socket-Verbindung.
	 */
	public abstract void sendHeader() throws IOException;

}