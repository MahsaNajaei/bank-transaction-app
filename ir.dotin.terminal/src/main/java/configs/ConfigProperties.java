package configs;

import entities.requests.TerminalRequest;

import java.util.List;

public class ConfigProperties {
    String serverIp;
    int serverPort;
    String outLogPath;
    List<TerminalRequest> requests;

    public ConfigProperties(String serverIp, int serverPort, String outLogPath, List<TerminalRequest> requests) {
        this.serverIp = serverIp;
        this.serverPort = serverPort;
        this.outLogPath = outLogPath;
        this.requests = requests;

    }

    public String getServerIp() {
        return serverIp;
    }

    public int getServerPort() {
        return serverPort;
    }

    public String getOutLogPath() {
        return outLogPath;
    }

    public List<TerminalRequest> getRequests() {
        return requests;
    }
}
