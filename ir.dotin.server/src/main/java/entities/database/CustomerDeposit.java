package entities.database;

import java.math.BigDecimal;

public class CustomerDeposit {
    private CustomerInfo customerInfo;
    private DepositInfo depositInfo;

    public CustomerDeposit(CustomerInfo customerInfo, DepositInfo depositInfo) {
        this.customerInfo = customerInfo;
        this.depositInfo = depositInfo;
    }
    public CustomerDeposit(String fullName, int depositId, BigDecimal balance, BigDecimal upperBound) {
        customerInfo = new CustomerInfo(fullName);
        depositInfo = new DepositInfo(depositId, balance, upperBound);
    }

    public CustomerInfo getCustomerInfo() {
        return customerInfo;
    }

    public DepositInfo getDepositInfo() {
        return depositInfo;
    }
}
