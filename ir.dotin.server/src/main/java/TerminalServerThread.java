import Exceptions.DatabaseNotLoadedException;
import entities.requests.Notification;
import entities.requests.TerminalRequest;
import entities.requests.TransactionRequest;
import entities.responses.CustomResponseStatus;
import entities.responses.DefaultResponse;
import entities.responses.TerminalResponse;
import helpers.*;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;

public class TerminalServerThread extends Thread {

    private TerminalServerConnectionHandler terminalServerConnectionHandler;
    private TransactionHandler transactionHandler;
    private RequestValidator requestValidator;

    public TerminalServerThread(Socket terminalServerSocket) throws IOException {
        terminalServerConnectionHandler = new TerminalServerConnectionHandler(terminalServerSocket);
        transactionHandler = new TransactionHandler();
        requestValidator = new DefaultRequestValidator();
    }

    @Override
    public void run() {
        try {
            while (true) {
                TerminalRequest terminalRequest = terminalServerConnectionHandler.readRequest();
                ServerLogger.getLogger().info("New request is received from terminal!");

                CustomResponseStatus responseStatus = requestValidator.validate(terminalRequest);
                if (responseStatus != CustomResponseStatus.REQUEST_SUCCEEDED) {
                    ServerLogger.getLogger().warning("" + responseStatus);
                    terminalServerConnectionHandler.writeResponse(new DefaultResponse(responseStatus));
                    ServerLogger.getLogger().info("Response is sent with status:" + responseStatus);
                    continue;
                }

                if (terminalRequest instanceof TransactionRequest transactionRequest) {
                    TerminalResponse terminalResponse = transactionHandler.performTransaction(transactionRequest);
                    terminalServerConnectionHandler.writeResponse(terminalResponse);
                    ServerLogger.getLogger().info("Response is sent with status:" + terminalResponse.getResponseStatus());
                    continue;
                }

                if (terminalRequest instanceof Notification notification) {
                    String message = notification.getMessage();
                    if (message.equalsIgnoreCase("Bye!")) {
                        terminalServerConnectionHandler.closeConnection();
                        ServerLogger.getLogger().info("terminal-server connection is closed due to terminal request!");
                        break;
                    }
                    ServerLogger.getLogger().warning("unknown notification message is received! [message:" + message);
                }
            }
        } catch (IOException e) {
            ServerLogger.getLogger().log(Level.SEVERE, "Unsuccessful IO operation on terminal-server socket!", e);
        } catch (ClassNotFoundException e) {
            ServerLogger.getLogger().log(Level.SEVERE, "Illegal request object! request must be of type TerminalRequest!", e);
        } catch (DatabaseNotLoadedException e) {
            ServerLogger.getLogger().log(Level.SEVERE, "Database is not loaded in BankServer Constructor!", e);
        }
    }
}
