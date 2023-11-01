package beforespring.yourfood.web.api.restaurant;

import beforespring.yourfood.web.response.restaurant.RegionListResponse;
import beforespring.yourfood.web.response.restaurant.RestaurantDetailResponse;
import beforespring.yourfood.web.response.restaurant.RestaurantListResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/restaurants")
public class RestaurantController {
    /**
     * 모든 시군구 목록 조회
     * @return
     */
    @GetMapping("/regions")
    public RegionListResponse getRegions() {
        return new RegionListResponse();
    }

    /**
     * 맛집 상세 정보 조회
     * @param restaurant_id 맛집 id
     * @return
     */

    @GetMapping("/{restaurant_id}")
    public RestaurantDetailResponse getRestaurantDetail(@PathVariable Long restaurant_id) {
        return new RestaurantDetailResponse();
    }

    /**
     * 지역별 맛집 목록 조회
     * @param region 조회할 지역명
     * @param range 조회 반경 거리
     * @param orderBy 정렬 기준
     * @return
     */
    @GetMapping
    public RestaurantListResponse getRestaurantsByRegion(@RequestParam String region,
                                                         @RequestParam int range,
                                                         @RequestParam(required = false) String orderBy) {
        return new RestaurantListResponse();
    }

    /**
     * 내 주변 맛집 목록 조회
     * @param range 조회 반경 거리
     * @param lat 위도
     * @param lon 경도
     * @param orderBy 정렬 기준
     * @return
     */
    @GetMapping("/nearby")
    public RestaurantListResponse getNearbyRestaurants(@RequestParam int range,
                                                       @RequestParam String lat,
                                                       @RequestParam String lon,
                                                       @RequestParam(required = false) String orderBy) {

        return new RestaurantListResponse();
    }

}
