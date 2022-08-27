package co.com.efalvare.model.exception;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class ApiError extends Exception {
    private String title;
    private String code;
    private String message;
    public ApiError(String title, String code, String message) {
        super(message);
        this.message = message;
        this.title = title;
        this.code = code;
    }
}
