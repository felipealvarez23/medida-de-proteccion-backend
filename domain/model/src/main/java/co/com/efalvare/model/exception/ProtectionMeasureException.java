package co.com.efalvare.model.exception;

public class ProtectionMeasureException extends ApiError {

    private static final String TITLE = "Protection measure exception";

    public ProtectionMeasureException(String code, String message) {
        super(TITLE, code, message);
    }
}
