package beforespring.yourfood.web.api.common;

import lombok.Builder;
import java.util.List;
import org.springframework.data.domain.Page;

public record PageResponse<T>(
    Integer page,
    Integer size,
    Long totalElements,
    Integer totalPages,
    List<T> contents) {

    @Builder
    public PageResponse {
    }

    public static <T> PageResponse<T> fromPage(Page<T> page) {
        return PageResponse.<T>builder()
                   .page(page.getNumber())
                   .size(page.getSize())
                   .totalElements(page.getTotalElements())
                   .totalPages(page.getTotalPages())
                   .contents(page.getContent())
                   .build();
    }
}
