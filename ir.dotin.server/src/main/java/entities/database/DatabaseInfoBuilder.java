package entities.database;

import java.math.BigDecimal;

public class DatabaseInfoBuilder {
    private CustomerInfo customerInfo;
    private DepositInfo depositInfo;

    public DatabaseInfoBuilder(String fullName, int depositId, BigDecimal balance, BigDecimal upperBound) {
        customerInfo = new CustomerInfo(fullName);
        depositInfo = new DepositInfo(depositId, balance, upperBound);
    }

    public CustomerDeposit build() {
        return new CustomerDeposit(customerInfo, depositInfo);
    }
}
