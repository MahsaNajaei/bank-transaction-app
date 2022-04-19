import entities.requests.TerminalRequest;
import configs.DefaultConfigDataExtractor;
import configs.ConfigProperties;
import configs.TerminalConfigHandler;
import helpers.TerminalLogger;
import helpers.OutputFormatter;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

public class TerminalTest {
    public static void main(String[] args) {

        try {
            Thread.sleep(1300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ConfigProperties configProperties = loadConfigs();
        if (configProperties == null) {
            TerminalLogger.getLogger().severe("Terminal configuration failure!");
            return;
        }
        TerminalLogger.addFileHandler(configProperties.getOutLogPath());
        runApplication(configProperties);
    }

    private static ConfigProperties loadConfigs() {
        return new TerminalConfigHandler(new DefaultConfigDataExtractor()).loadConfigs("ir.dotin.terminal\\src\\terminal.xml");
    }

    private static void runApplication(ConfigProperties configProperties) {
        try {
            Terminal terminal = new Terminal(configProperties.getServerIp(), configProperties.getServerPort());
            List<TerminalRequest> terminalRequests = configProperties.getRequests();
            for (TerminalRequest request : terminalRequests) {
                terminal.processRequest(request);
            }

            terminal.close();
        } catch (IOException e) {
            TerminalLogger.getLogger().log(Level.SEVERE, "Terminal initialization failed!", e);
        }
    }
}
