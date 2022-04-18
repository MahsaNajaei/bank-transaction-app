package configs;

import entities.database.CustomerDeposit;

import java.util.Map;

public class ConfigProperties {
    private int port;
    private Map<Integer, CustomerDeposit> databaseInfoByDepositId;
    private String logPath;

    public ConfigProperties(int port, Map<Integer, CustomerDeposit> databaseInfoByDepositId, String logPath) {
        this.port = port;
        this.databaseInfoByDepositId = databaseInfoByDepositId;
        this.logPath = logPath;
    }

    public int getPort() {
        return port;
    }

    public Map<Integer, CustomerDeposit> getDatabaseInfoByDepositId() {
        return databaseInfoByDepositId;
    }

    public String getLogPath() {
        return logPath;
    }
}
