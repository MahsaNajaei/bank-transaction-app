package helpers;

import java.io.IOException;
import java.util.logging.*;

public class ServerLogger {

    private static final Logger LOGGER = Logger.getLogger(ServerLogger.class.getName());

    public static void addFileHandler(String logPath) {
        try {
            Handler fileHandler = new FileHandler(logPath);
            fileHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fileHandler);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "helpers.ServerLogger failed adding fileHandler!" + e);
        }
    }

    public static Logger getLogger() {
        return LOGGER;
    }
}