package beforespring.yourfood.web.api.common;
import lombok.Builder;
import java.util.List;

public record PageResponse<T>(Integer page, Integer size, Long totalElements, Integer totalPages, List<T> contents) {
    @Builder
    public PageResponse {
    }
}
