package beforespring.yourfood.web.api.common;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GenericResponse<T> {
    private int statusCode;
    private T data;
    private String message;
    @Builder
    public GenericResponse(int statusCode, T data, String message) {
        this.statusCode = statusCode;
        this.data = data;
        this.message = message;
    }
}
