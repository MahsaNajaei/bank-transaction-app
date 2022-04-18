package helpers;

import Exceptions.DatabaseNotLoadedException;
import entities.database.DefaultServerDAO;
import entities.requests.TerminalRequest;
import entities.requests.TransactionRequest;
import entities.responses.CustomResponseStatus;

import java.math.BigDecimal;

public class DefaultRequestValidator implements RequestValidator {
    @Override
    public CustomResponseStatus validate(TerminalRequest terminalRequest) throws DatabaseNotLoadedException {
        if (terminalRequest instanceof TransactionRequest transactionRequest) {
            if (checkNullComponent(transactionRequest))
                return CustomResponseStatus.NULL_CONTENT;
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

    private boolean checkNumberNegativity(BigDecimal amount) {
        return amount.compareTo(BigDecimal.ZERO) < 0;
    }

    private boolean checkNullComponent(TransactionRequest transactionRequest) {
        return transactionRequest.getTransactionType() == null || transactionRequest.getAmount() == null;
    }

}
