package controller.exceptions;

public class MyException extends Exception {

    private String errorTitle;
    private String errorHeader;

    public MyException(String errorTitle, String errorHeader, String message) {
        super(message);
        this.errorTitle = errorTitle;
        this.errorHeader = errorHeader;
    }

    public String getErrorTitle() {
        return errorTitle;
    }

    public String getErrorHeader() {
        return errorHeader;
    }
}
