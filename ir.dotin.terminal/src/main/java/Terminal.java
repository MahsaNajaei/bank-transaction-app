import entities.requests.Notification;
import entities.requests.TerminalRequest;
import entities.responses.TerminalResponse;
import helpers.Recorder;
import helpers.TerminalConnectionHandler;
import helpers.TerminalDataRecorder;
import helpers.TerminalLogger;

import java.io.IOException;

public class Terminal {

    private TerminalConnectionHandler terminalConnectionHandler;
    private Recorder outputRecorder;

    public Terminal(String serverIP, int serverPort) throws IOException {
        terminalConnectionHandler = new TerminalConnectionHandler(serverIP, serverPort);
        outputRecorder = new TerminalDataRecorder("terminal.out");
    }

    void processRequest(TerminalRequest request) {

        if (terminalConnectionHandler.sendRequest(request)) {
            TerminalLogger.getLogger().info("request is sent successfully!");
            TerminalResponse response = terminalConnectionHandler.getResponse();
            TerminalLogger.getLogger().info("Response is received with status:" + response.getResponseStatus());
            try {
                String output = response.getMessage() + ", status:" + response.getResponseStatus();
                outputRecorder.record(output);
                TerminalLogger.getLogger().info("Response is recorded successfully!" + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void close() {
        terminalConnectionHandler.sendRequest(new Notification("Bye!"));
        terminalConnectionHandler.closeConnection();
        TerminalLogger.getLogger().info("Terminal is closed!");
    }


}
