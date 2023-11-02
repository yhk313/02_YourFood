package beforespring.yourfood.web.api.restaurant;

import beforespring.yourfood.web.api.common.GenericResponse;

import beforespring.yourfood.web.api.restaurant.response.RegionListResponse;
import beforespring.yourfood.web.api.restaurant.response.RestaurantDetailResponse;
import beforespring.yourfood.web.api.restaurant.response.RestaurantListResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/restaurants")
public class RestaurantController {
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
     * @param restaurant_id 맛집 id
     * @return
     */

    @GetMapping("/{restaurant_id}")
    public GenericResponse<RestaurantDetailResponse> getRestaurantDetail(@PathVariable Long restaurant_id) {
        return null;
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
