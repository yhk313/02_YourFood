package beforespring.yourfood.web.api.restaurant;

import beforespring.yourfood.app.restaurant.service.RestaurantServiceImpl;
import beforespring.yourfood.app.restaurant.service.dto.RestaurantWithReviewDto;
import beforespring.yourfood.web.api.common.GenericResponse;

import beforespring.yourfood.web.api.common.StatusCode;
import beforespring.yourfood.web.api.restaurant.response.RegionListResponse;
import beforespring.yourfood.web.api.restaurant.response.RestaurantListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/restaurants")
@RequiredArgsConstructor
public class RestaurantController {
    private final RestaurantServiceImpl restaurantService;

    /**
     * 모든 시군구 목록 조회
     *
     * @return
     */
    @GetMapping("/regions")
    public GenericResponse<RegionListResponse> getRegions() {
        return null;
    }

    /**
     * 맛집 상세 정보 조회
     *
     * @param restaurantId 맛집 id
     * @return 레스토랑의 상세 정보
     */

    @GetMapping("/{restaurantId}")
    public GenericResponse<RestaurantWithReviewDto> getRestaurantDetail(@PathVariable Long restaurantId) {
        RestaurantWithReviewDto restaurantDto = restaurantService.getRestaurantDetail(restaurantId);

        return GenericResponse.<RestaurantWithReviewDto>builder()
            .statusCode(StatusCode.OK)
            .message("Success")
            .data(restaurantDto).build();
    }

    /**
     * 지역별 맛집 목록 조회
     *
     * @param region  조회할 지역명
     * @param range   조회 반경 거리
     * @param orderBy 정렬 기준
     * @return
     */
    @GetMapping
    public GenericResponse<RestaurantListResponse> getRestaurantsByRegion(@RequestParam String region,
                                                                          @RequestParam int range,
                                                                          @RequestParam(required = false) String orderBy) {
        return null;
    }

    /**
     * 내 주변 맛집 목록 조회
     *
     * @param range   조회 반경 거리
     * @param lat     위도
     * @param lon     경도
     * @param orderBy 정렬 기준
     * @return
     */
    @GetMapping("/nearby")
    public GenericResponse<RestaurantListResponse> getNearbyRestaurants(@RequestParam int range,
                                                                        @RequestParam String lat,
                                                                        @RequestParam String lon,
                                                                        @RequestParam(required = false) String orderBy) {

        return null;
    }

}
