package helpers;

import Exceptions.DatabaseNotLoadedException;
import entities.database.DefaultServerDAO;
import entities.database.DepositInfo;
import entities.requests.TransactionRequest;
import entities.requests.TransactionType;
import entities.responses.CustomResponseStatus;
import entities.responses.DefaultResponse;
import entities.responses.TerminalResponse;

import java.math.BigDecimal;

public class TransactionHandler {

    public TerminalResponse performTransaction(TransactionRequest transactionRequest) throws DatabaseNotLoadedException {

        TransactionType transactionType = transactionRequest.getTransactionType();
        CustomResponseStatus customResponseStatus;
        String responseMessage;
        synchronized (DefaultServerDAO.getDefaultServerDAO()) {
            DepositInfo depositInfo = DefaultServerDAO.getDefaultServerDAO().retrieveDepositInfo(transactionRequest.getDepositId());
            customResponseStatus = switch (transactionType) {
                case DEPOSIT -> deposit(transactionRequest.getAmount(), depositInfo);
                case WITHDRAW -> withdraw(transactionRequest.getAmount(), depositInfo);
            };
            responseMessage = generateResponseMessage(transactionRequest.getTransactionId(), depositInfo.getDepositId(), DefaultServerDAO.getDefaultServerDAO().retrieveDepositInfo(depositInfo.getDepositId()).getBalance());
        }
        return new DefaultResponse(responseMessage, customResponseStatus);
    }

    private CustomResponseStatus deposit(BigDecimal amount, DepositInfo depositInfo) throws DatabaseNotLoadedException {
        BigDecimal tempAmount = depositInfo.getBalance().add(amount);
        if (tempAmount.compareTo(depositInfo.getUpperBound()) > 0) {
            ServerLogger.getLogger().warning("deposit is not permitted due to " + CustomResponseStatus.UPPER_BOUND_VIOLATION);
            return CustomResponseStatus.UPPER_BOUND_VIOLATION;
        }
        DefaultServerDAO.getDefaultServerDAO().updateDepositBalance(depositInfo.getDepositId(), tempAmount);
        ServerLogger.getLogger().info("deposit increased successfully for depositId: " + depositInfo.getDepositId());
        return CustomResponseStatus.REQUEST_SUCCEEDED;
    }

    private CustomResponseStatus withdraw(BigDecimal amount, DepositInfo depositInfo) throws DatabaseNotLoadedException {
        if (depositInfo.getBalance().compareTo(amount) < 0) {
            ServerLogger.getLogger().warning("withdraw is not permitted due to " + CustomResponseStatus.INSUFFICIENT_BALANCE);
            return CustomResponseStatus.INSUFFICIENT_BALANCE;
        }
        BigDecimal updatedBalance = depositInfo.getBalance().subtract(amount);
        DefaultServerDAO.getDefaultServerDAO().updateDepositBalance(depositInfo.getDepositId(), updatedBalance);
        ServerLogger.getLogger().info("withdraw from deposit: " + depositInfo.getDepositId());
        return CustomResponseStatus.REQUEST_SUCCEEDED;
    }

    private String generateResponseMessage(int transactionId, int depositId, BigDecimal balance) {
        return "id:" + transactionId + ", deposit:" + depositId + ", currentBalance:" + balance ;
    }
}
