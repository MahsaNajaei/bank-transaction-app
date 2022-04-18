package helpers;

import Exceptions.DatabaseNotLoadedException;
import entities.requests.TerminalRequest;
import entities.responses.CustomResponseStatus;

public interface RequestValidator {
    CustomResponseStatus validate(TerminalRequest terminalRequest) throws DatabaseNotLoadedException;
}
