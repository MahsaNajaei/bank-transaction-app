import configs.ConfigHandler;
import configs.ConfigProperties;
import helpers.ServerLogger;

import java.io.IOException;
import java.util.logging.Level;

public class ServerTest {
    public static void main(String[] args) {
        ConfigProperties configProperties = null;
        try {
            configProperties = ConfigHandler.getConfigHandler().loadConfigs();
            ServerLogger.addFileHandler(configProperties.getLogPath());
        } catch (IOException e) {
            ServerLogger.getLogger().log(Level.SEVERE, "unsuccessful config loading!", e);
        }

        try {
            Server server = new BankServer(configProperties);
            server.startSever();
        } catch (IOException e) {
            ServerLogger.getLogger().log(Level.SEVERE, "unsuccessful port binding!", e);
        }
    }
}
