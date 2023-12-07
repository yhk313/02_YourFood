package beforespring.yourfood.web.api.restaurant;

import beforespring.yourfood.app.utils.OrderBy;
import beforespring.yourfood.app.restaurant.service.RestaurantServiceImpl;
import beforespring.yourfood.app.restaurant.service.dto.RestaurantWithReviewDto;
import beforespring.yourfood.app.utils.Coordinates;
import beforespring.yourfood.app.utils.SggLatLonService;
import beforespring.yourfood.web.api.common.GenericResponse;

import beforespring.yourfood.web.api.restaurant.response.RegionDto;
import beforespring.yourfood.web.api.restaurant.response.RegionListResponse;
import beforespring.yourfood.app.restaurant.service.dto.RestaurantDto;
import beforespring.yourfood.web.api.restaurant.response.RestaurantListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/restaurants")
@RequiredArgsConstructor
public class RestaurantController {
    private final RestaurantServiceImpl restaurantService;
    private final SggLatLonService sggLatLonService;

    /**
     * 모든 시군구 목록 조회
     *
     * @return 시군구 목록
     */
    @GetMapping("/regions")
    public GenericResponse<RegionListResponse> getRegions() {
        List<RegionDto> allSggLatLon = sggLatLonService.getAllSggLatLon();
        RegionListResponse regionListResponse = new RegionListResponse(allSggLatLon);
        return GenericResponse.ok(regionListResponse);
    }

    /**
     * 맛집 상세 정보 조회
     *
     * @param restaurantId 맛집 id
     * @return 레스토랑의 상세 정보
     */
    @GetMapping("/{restaurantId}")
    public GenericResponse<RestaurantWithReviewDto> getRestaurantDetail(
        @PathVariable Long restaurantId) {
        RestaurantWithReviewDto restaurantDto = restaurantService.getRestaurantDetail(restaurantId);

        return GenericResponse.ok(restaurantDto);
    }

    /**
     * 맛집 목록 조회
     *
     * @param rangeInMeter    반경
     * @param lat             위도
     * @param lon             경도
     * @param orderBy         정렬 기준 (평점 또는 거리)
     * @param descendingOrder 내림차순 정렬 여부
     * @return 맛집 목록
     */
    @GetMapping("")
    public GenericResponse<RestaurantListResponse> getRestaurants(
        @RequestParam Integer rangeInMeter,
        @RequestParam String lat,
        @RequestParam String lon,
        @RequestParam(required = false) OrderBy orderBy,
        @RequestParam(required = false) boolean descendingOrder) {
        BigDecimal latDecimal = new BigDecimal(String.valueOf(lat));
        BigDecimal lonDecimal = new BigDecimal(String.valueOf(lon));
        Coordinates coordinates = new Coordinates(latDecimal, lonDecimal);

        List<RestaurantDto> restaurantDtos = restaurantService.getRestaurants(
            orderBy,
            descendingOrder,
            coordinates,
            rangeInMeter);

        RestaurantListResponse restaurantListResponse = new RestaurantListResponse(restaurantDtos);

        return GenericResponse.ok(restaurantListResponse);
    }
}
