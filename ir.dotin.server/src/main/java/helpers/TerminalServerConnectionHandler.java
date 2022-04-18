package helpers;

import entities.requests.TerminalRequest;
import entities.responses.TerminalResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TerminalServerConnectionHandler {
    private Socket terminalServerSocket;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;

    public TerminalServerConnectionHandler(Socket terminalServerSocket) throws IOException {
        this.terminalServerSocket = terminalServerSocket;
        objectInputStream = new ObjectInputStream(terminalServerSocket.getInputStream());
        objectOutputStream = new ObjectOutputStream(terminalServerSocket.getOutputStream());
    }

    public TerminalRequest readRequest() throws IOException, ClassNotFoundException {
        return (TerminalRequest) objectInputStream.readObject();
    }

    public void writeResponse(TerminalResponse terminalResponse) throws IOException {
        objectOutputStream.writeObject(terminalResponse);
        objectOutputStream.flush();
    }

    public void closeConnection() throws IOException {
        objectInputStream.close();
        objectOutputStream.flush();
        objectOutputStream.close();
        terminalServerSocket.close();
    }
}
