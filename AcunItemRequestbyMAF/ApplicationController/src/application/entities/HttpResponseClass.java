package application.entities;

public class HttpResponseClass {
    private String returnCode, errorCode, errorExplanation;
    public HttpResponseClass() {
        super();
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorExplanation(String errorExplanation) {
        this.errorExplanation = errorExplanation;
    }

    public String getErrorExplanation() {
        return errorExplanation;
    }
}
