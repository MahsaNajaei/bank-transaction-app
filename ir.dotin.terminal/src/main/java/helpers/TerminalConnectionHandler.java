package helpers;

import entities.requests.TerminalRequest;
import entities.responses.TerminalResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;

public class TerminalConnectionHandler {
    private final Socket socket;
    private final ObjectOutputStream requestOutputStream;
    private final ObjectInputStream responseInputStream;

    public TerminalConnectionHandler(String ip, int port) throws IOException {
        socket = new Socket(ip, port);
        ServerLogger.getLogger().info("Terminal is connected successfully to the server! [" + this.socket.getInetAddress() + "]");
        requestOutputStream = new ObjectOutputStream(this.socket.getOutputStream());
        responseInputStream = new ObjectInputStream(this.socket.getInputStream());
    }

    public boolean sendRequest(TerminalRequest request) {
        try {
            requestOutputStream.writeObject(request);
            requestOutputStream.flush();
        } catch (IOException e) {
            TerminalLogger.getLogger().log(Level.SEVERE, "Sending request is failed!", e);
            return false;
        }
        return true;
    }

    public TerminalResponse getResponse() {
        TerminalResponse response = null;
        try {
            response = (TerminalResponse) responseInputStream.readObject();
        } catch (IOException e) {
            TerminalLogger.getLogger().log(Level.SEVERE, "Reading response failed!", e);
        } catch (ClassNotFoundException e) {
            TerminalLogger.getLogger().log(Level.SEVERE, "Unknown response object!", e);
        }
        return response;
    }

    public void closeConnection() {
        try {
            requestOutputStream.flush();
            requestOutputStream.close();
            responseInputStream.close();
            socket.close();
        } catch (IOException e) {
            TerminalLogger.getLogger().log(Level.SEVERE, "Closing terminal-server connection is failed!", e);
        }
    }
}
