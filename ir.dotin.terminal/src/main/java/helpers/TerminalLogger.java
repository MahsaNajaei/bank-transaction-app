package helpers;

import java.io.IOException;
import java.util.logging.*;

public class TerminalLogger {

    private static final Logger LOGGER = Logger.getLogger(TerminalLogger.class.getName());

    public static void addFileHandler(String logPath) {
        try {
            Handler fileHandler = new FileHandler(logPath);
            fileHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fileHandler);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "helpers.TerminalLogger failed adding fileHandler!" + e);
        }
    }

    public static Logger getLogger() {
        return LOGGER;
    }
}
