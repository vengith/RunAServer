package create;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.logging.Logger;

import givenpackage.HttpCommand;
import givenpackage.InvalidRequestException;

public abstract class AbstractSimpleRequest {

	protected static Logger logger = Logger
			.getLogger(AbstractSimpleRequest.class.getName());
	protected HashMap<String, String> headers;
	protected HashMap<String, String> params;
	protected Socket socket;
	protected HttpCommand requestType;
	protected String ressourceURI;
	protected String protocolType;
	protected BufferedReader in;

	public AbstractSimpleRequest() {
		super();
	}

	/**
	 * @return the headers
	 */
	public HashMap<String, String> getHeaders() {
		return headers;
	}

	/**
	 * @return the params
	 */
	public HashMap<String, String> getParams() {
		return params;
	}

	/**
	 * @return the requestType
	 */
	public HttpCommand getRequestType() {
		return requestType;
	}

	/**
	 * @return the ressourceURI
	 */
	public String getRessourceURI() {
		return this.ressourceURI;
	}

	/**
	 * @return the protocolType
	 */
	public String getProtocolType() {
		return protocolType;
	}

	@Override
	public String toString() {
		return "SimpleRequest [headers=" + headers + ", params=" + params
				+ ", socket=" + socket + ", requestType=" + requestType
				+ ", ressourceURI=" + ressourceURI + ", protocolType="
				+ protocolType + "]";
	}

	/**
	 * Liest zeilenweise den Request und füllt damit das Request-Objekt.
	 * 
	 * @throws IOException
	 *             bei Problemen mit der Socket-Verbindung
	 * @throws InvalidRequestException
	 *             wenn der Request nicht Standard-konform ist
	 */
	public abstract void parseRequest() throws IOException,
			InvalidRequestException;

}