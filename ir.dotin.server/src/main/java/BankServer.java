import configs.ConfigProperties;
import entities.database.DefaultServerDAO;
import helpers.ServerLogger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;

public class BankServer implements Server {
    private boolean isActive;
    private final ServerSocket serverSocket;


    public BankServer(ConfigProperties configProperties) throws IOException {
        DefaultServerDAO.loadDB(configProperties.getDatabaseInfoByDepositId());
        ServerLogger.getLogger().info("Database is loaded successfully!");
        isActive = true;
        serverSocket = new ServerSocket(configProperties.getPort());
        ServerLogger.getLogger().log(Level.INFO, "successful server port binding! [port:" + configProperties.getPort() + "]");
    }

    @Override
    public void startSever() {
        while (isActive) {
            try {
                Socket terminalServerSocket = serverSocket.accept();
                ServerLogger.getLogger().log(Level.INFO, "new Terminal is connected!");
                new TerminalServerThread(terminalServerSocket).start();
            } catch (IOException e) {
                ServerLogger.getLogger().log(Level.SEVERE, "Terminal-Server socket is unable to get IOStreams!", e);
            }
        }
    }

    @Override
    public void stop() {
        isActive = false;
    }
}
