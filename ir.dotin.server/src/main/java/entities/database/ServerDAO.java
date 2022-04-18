package entities.database;

import java.math.BigDecimal;

public interface ServerDAO {
    CustomerDeposit retrieveCustomerDepositByDepositId(int depositID);

    void updateDepositBalance(int depositId, BigDecimal updatedBalance);

    boolean depositIdExists(int depositId);

    DepositInfo retrieveDepositInfo(int depositId);
}
