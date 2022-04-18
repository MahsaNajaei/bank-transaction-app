package entities.responses;

import java.io.Serializable;

public class DefaultResponse implements TerminalResponse, Serializable {
    private String message;
    private CustomResponseStatus responseStatus;

    public DefaultResponse(CustomResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public DefaultResponse(String message, CustomResponseStatus responseStatus) {
        this.message = message;
        this.responseStatus = responseStatus;
    }

    public String getMessage() {
        return message;
    }

    public CustomResponseStatus getResponseStatus() {
        return responseStatus;
    }

}
