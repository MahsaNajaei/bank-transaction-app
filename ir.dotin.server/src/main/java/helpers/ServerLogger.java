package helpers;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.*;

public class ServerLogger {

    private static final Logger LOGGER = Logger.getLogger(ServerLogger.class.getName());

    static {
        try {
            LogManager.getLogManager().readConfiguration(new FileInputStream("log.properties"));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "helpers.ServerLogger failed reading log.properties file!" + e);
        }
    }

    public static void addFileHandler(String logPath) {
        try {
            Handler fileHandler = new FileHandler(logPath);
            LOGGER.addHandler(fileHandler);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "helpers.ServerLogger failed adding fileHandler!" + e);
        }
    }

    public static Logger getLogger() {
        return LOGGER;
    }
}