package entities.requests;

import java.io.Serializable;
import java.math.BigDecimal;

public class TransactionRequest implements TerminalRequest, Serializable {

    private int transactionId;
    private int depositId;
    private BigDecimal amount;
    private TransactionType transactionType;

    public TransactionRequest(int transactionId, int depositId, BigDecimal amount, TransactionType transactionType) {
        this.transactionId = transactionId;
        this.depositId = depositId;
        this.amount = amount;
        this.transactionType = transactionType;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public int getDepositId() {
        return depositId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }
}
