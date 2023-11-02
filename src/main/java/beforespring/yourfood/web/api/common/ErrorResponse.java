package beforespring.yourfood.web.api.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public class ErrorResponse<T> {
    private int statusCode;
    private T message;
    private String path;

    public ErrorResponse(int statusCode, T message, String path) {
        this.statusCode = statusCode;
        this.message = message;
        this.path = path;
    }
}
