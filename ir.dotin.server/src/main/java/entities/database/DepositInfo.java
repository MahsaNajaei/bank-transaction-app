package entities.database;

import java.math.BigDecimal;

public class DepositInfo {
    private int depositId;
    private BigDecimal balance;
    private BigDecimal upperBound;

    public DepositInfo(int depositId, BigDecimal balance, BigDecimal upperBound) {
        this.depositId = depositId;
        this.balance = balance;
        this.upperBound = upperBound;
    }

    public int getDepositId() {
        return depositId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public BigDecimal getUpperBound() {
        return upperBound;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
