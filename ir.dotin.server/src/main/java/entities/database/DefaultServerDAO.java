package entities.database;

import Exceptions.DatabaseNotLoadedException;
import configs.ConfigHandler;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultServerDAO implements ServerDAO {

    private static Map<Integer, CustomerDeposit> databaseInfoByDepositID = new ConcurrentHashMap<>();
    private static DefaultServerDAO defaultServerDAO = new DefaultServerDAO();

    private DefaultServerDAO() {
    }

    @Override
    public synchronized CustomerDeposit retrieveCustomerDepositByDepositId(int depositId) {
        return databaseInfoByDepositID.get(depositId);
    }

    @Override
    public synchronized void updateDepositBalance(int depositId, BigDecimal updatedBalance) {
        DepositInfo depositInfo = databaseInfoByDepositID.get(depositId).getDepositInfo();
        depositInfo.setBalance(updatedBalance);
        ConfigHandler.getConfigHandler().updateConfigFile(databaseInfoByDepositID);
    }

    @Override
    public synchronized boolean depositIdExists(int depositId) {
        return databaseInfoByDepositID.containsKey(depositId);
    }

    @Override
    public synchronized DepositInfo retrieveDepositInfo(int depositId) {
        return databaseInfoByDepositID.get(depositId).getDepositInfo();
    }

    public static void loadDB(Map<Integer, CustomerDeposit> dbInfo) {
        if (databaseInfoByDepositID.size() == 0)
            databaseInfoByDepositID = dbInfo;
    }

    public static synchronized ServerDAO getDefaultServerDAO() throws DatabaseNotLoadedException {
        if (databaseInfoByDepositID.size() == 0)
            throw new DatabaseNotLoadedException("Database should be loaded in order to use DefaultServerDAO!");
        return defaultServerDAO;
    }

}
