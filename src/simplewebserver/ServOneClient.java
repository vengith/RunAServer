package simplewebserver;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;

import givenpackage.HttpStatus;
import givenpackage.InvalidRequestException;

public class ServOneClient extends Thread {
	Logger logger = Logger.getLogger(ServOneClient.class.getName());

	private static final String INVALID_REQUEST = null;
	private Socket socket;
	private MyRessourceManager ressourceManager;

	public ServOneClient(Socket socket, MyRessourceManager ressourceManager) {
		this.socket = socket;
		this.ressourceManager = ressourceManager;
		this.start();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {

		MySimpleRequest request;
		MySimpleResponse response;
		try {
			request = new MySimpleRequest(socket);
			response = new MySimpleResponse(socket);
		} catch (IOException e1) {
			// If we cannot create a request or a response on the socket, we
			// cannot communicate with the client, so just print an error log
			// message and stop further processing.
			e1.printStackTrace();
			return;
		}

		try {
			request.parseRequest();
		} catch (InvalidRequestException e) {
			// We have no valid request so send this info back to the user.
			e.printStackTrace();
			response.setHttpStatus(HttpStatus.BAD_REQUEST);
			try {
				response.addToBody("The request was invalid.\n");
				response.addToBody("Details:\n");
				response.addToBody(e.getMessage());
				return;
			} catch (IOException e1) {
				// communication problem, see above
				e1.printStackTrace();
				return;
			}
		} catch (IOException e) {
			// communication problem, see above
			e.printStackTrace();
			return;
		}
		logger.info(request.toString());

		try {
			ressourceManager.doService(request, response);
		} catch (IOException e) {
			// communication problem, see above
			e.printStackTrace();
			return;
		}

		// End communication
		response.close();
	}

}
