package controller.exceptions;

public class InvalidTextFieldValue extends MyException {
    public InvalidTextFieldValue(String errorTitle, String errorHeader, String message) {
        super(errorTitle, errorHeader, message);
    }
}
