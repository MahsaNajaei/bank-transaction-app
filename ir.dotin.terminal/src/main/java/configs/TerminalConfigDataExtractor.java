package configs;

import entities.requests.TerminalRequest;
import org.w3c.dom.Document;

import java.util.List;

public interface TerminalConfigDataExtractor {

    String extractServerIp(Document document);

    int extractServerPort(Document document);

    String extractLogPath(Document document);

    List<TerminalRequest> extractTransactions(Document document);
}
