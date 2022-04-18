package configs;

import entities.requests.TerminalRequest;
import entities.requests.TransactionRequest;
import entities.requests.TransactionType;
import helpers.TerminalLogger;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DefaultConfigDataExtractor implements TerminalConfigDataExtractor {
    @Override
    public String extractServerIp(Document document) {
        NamedNodeMap serverAttributes = document.getElementsByTagName("server").item(0).getAttributes();
        return serverAttributes.getNamedItem("ip").getTextContent();
    }

    @Override
    public int extractServerPort(Document document) {
        NamedNodeMap serverAttributes = document.getElementsByTagName("server").item(0).getAttributes();
        return Integer.parseInt(serverAttributes.getNamedItem("port").getTextContent());
    }

    @Override
    public String extractLogPath(Document document) {
        return document.getElementsByTagName("outLog").item(0).getAttributes().getNamedItem("path").getTextContent();
    }

    @Override
    public List<TerminalRequest> extractTransactions(Document document) {
        NodeList transactionNodes = document.getElementsByTagName("transaction");
        List<TerminalRequest> terminalRequests = new ArrayList<>();
        for (int i = 0; i < transactionNodes.getLength(); i++) {
            NamedNodeMap transactionAttributes = transactionNodes.item(i).getAttributes();
            try {
                int transactionId = Integer.parseInt(transactionAttributes.getNamedItem("id").getNodeValue());
                int depositId = Integer.parseInt(transactionAttributes.getNamedItem("deposit").getNodeValue());
                BigDecimal amount = new BigDecimal(transactionAttributes.getNamedItem("amount").getNodeValue().replaceAll(",", ""));
                TransactionType type = TransactionType.valueOf(transactionAttributes.getNamedItem("type").getTextContent().toUpperCase(Locale.ROOT));
                TransactionRequest transactionRequest = new TransactionRequest(transactionId, depositId, amount, type);
                terminalRequests.add(transactionRequest);
            }catch (NullPointerException e){
                TerminalLogger.getLogger().warning("transaction request is invalid due to missing standard attributes !" + e);
            }catch (IllegalArgumentException e){
                TerminalLogger.getLogger().warning("Invalid data type in transaction attributes!" + e);
            }
        }
        return terminalRequests;
    }
}
