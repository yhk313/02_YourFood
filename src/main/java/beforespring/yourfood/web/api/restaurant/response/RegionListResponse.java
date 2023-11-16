package beforespring.yourfood.web.api.restaurant.response;

import lombok.Builder;

import java.util.List;

public record RegionListResponse(List<RegionDto> regionDto) {
    @Builder
    public RegionListResponse {
    }
}
