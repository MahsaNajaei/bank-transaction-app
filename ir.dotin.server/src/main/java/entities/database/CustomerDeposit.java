package entities.database;

public class CustomerDeposit {
    private CustomerInfo customerInfo;
    private DepositInfo depositInfo;

    public CustomerDeposit(CustomerInfo customerInfo, DepositInfo depositInfo) {
        this.customerInfo = customerInfo;
        this.depositInfo = depositInfo;
    }

    public CustomerInfo getCustomerInfo() {
        return customerInfo;
    }

    public DepositInfo getDepositInfo() {
        return depositInfo;
    }
}
