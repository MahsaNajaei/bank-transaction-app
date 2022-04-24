package helpers;

import Exceptions.DatabaseNotLoadedException;
import entities.database.DefaultServerDAO;
import entities.requests.TerminalRequest;
import entities.requests.TransactionRequest;
import entities.responses.CustomResponseStatus;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class DefaultRequestValidator implements RequestValidator {
    private static Set<Integer> transactionIdArchive = new HashSet<>();

    @Override
    public CustomResponseStatus validate(TerminalRequest terminalRequest) throws DatabaseNotLoadedException {
        if (terminalRequest instanceof TransactionRequest transactionRequest) {
            if (checkNullComponent(transactionRequest))
                return CustomResponseStatus.NULL_CONTENT;
            if (checkDuplicatedTransactionId(transactionRequest))
                return CustomResponseStatus.DUPLICATED_TRANSACTION_ID;
            if (checkNumberNegativity(transactionRequest.getAmount()) ||
                    checkNumberNegativity(new BigDecimal(transactionRequest.getTransactionId())) ||
                    checkNumberNegativity(new BigDecimal(transactionRequest.getDepositId()))) {
                return CustomResponseStatus.ILLEGAL_NEGATIVE_NUMBER;
            }
            if (!DefaultServerDAO.getDefaultServerDAO().depositIdExists(transactionRequest.getDepositId()))
                return CustomResponseStatus.INVALID_DEPOSIT;
        }
        return CustomResponseStatus.REQUEST_SUCCEEDED;
    }

    private synchronized boolean checkDuplicatedTransactionId(TransactionRequest transactionRequest) {
        boolean isDuplicated = transactionIdArchive.contains(transactionRequest.getTransactionId());
        transactionIdArchive.add(transactionRequest.getTransactionId());
        return isDuplicated;
    }

    private boolean checkNumberNegativity(BigDecimal amount) {
        return amount.compareTo(BigDecimal.ZERO) < 0;
    }

    private boolean checkNullComponent(TransactionRequest transactionRequest) {
        return transactionRequest.getTransactionType() == null || transactionRequest.getAmount() == null;
    }

}
