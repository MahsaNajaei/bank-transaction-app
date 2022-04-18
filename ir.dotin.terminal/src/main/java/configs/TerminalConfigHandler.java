package configs;

import entities.requests.TerminalRequest;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

public class TerminalConfigHandler implements ConfigHandler {

    private TerminalConfigDataExtractor extractor;

    public TerminalConfigHandler(TerminalConfigDataExtractor extractor) {
        this.extractor = extractor;
    }

    public ConfigProperties loadConfigs() {
        ConfigProperties configProperties = null;
        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse("ir.dotin.terminal\\src\\terminal.xml");

            String serverIp = extractor.extractServerIp(document);
            int serverPort = extractor.extractServerPort(document);
            String logPath = extractor.extractLogPath(document);
            List<TerminalRequest> transactions = extractor.extractTransactions(document);
            configProperties = new ConfigProperties(serverIp, serverPort, logPath, transactions);

        } catch (SAXException | IOException | ParserConfigurationException e) {
            e.printStackTrace();
        }

        return configProperties;
    }
}