package beforespring.yourfood.batch.rawrestaurant.fetch.fetcherimplement;

import beforespring.yourfood.app.restaurant.domain.CuisineType;
import beforespring.yourfood.batch.rawrestaurant.fetch.RawRestaurantFetchResult;
import beforespring.yourfood.batch.rawrestaurant.fetch.RawRestaurantFetcher;
import beforespring.yourfood.batch.rawrestaurant.fetch.fetcherimplement.gyeonggi.GyeonggiOpenApiManagerFactory;
import beforespring.yourfood.batch.rawrestaurant.fetch.fetcherimplement.gyeonggi.GyeonggiRawRestaurantMapper;

public class GyeonggiFetcherImpl implements RawRestaurantFetcher {
    private final CuisineType cuisineType;
    private final OpenApiManager openApiManager;
    private final GyeonggiRawRestaurantMapper gyeonggiRawRestaurantMapper;

    public GyeonggiFetcherImpl(GyeonggiOpenApiManagerFactory gyeonggiOpenApiManagerFactory, CuisineType cuisineType) {
        this.cuisineType = cuisineType;
        this.openApiManager = gyeonggiOpenApiManagerFactory.createOpenApiManager(cuisineType);
        this.gyeonggiRawRestaurantMapper = new GyeonggiRawRestaurantMapper();  // todo 싱글톤
    }

    public GyeonggiFetcherImpl(GyeonggiOpenApiManagerFactory gyeonggiOpenApiManagerFactory, CuisineType cuisineType, GyeonggiRawRestaurantMapper gyeonggiRawRestaurantMapper) {
        this.cuisineType = cuisineType;
        this.openApiManager = gyeonggiOpenApiManagerFactory.createOpenApiManager(cuisineType);
        this.gyeonggiRawRestaurantMapper = gyeonggiRawRestaurantMapper;
    }

    @Override
    public RawRestaurantFetchResult find(int page, int pageSize) {
        return gyeonggiRawRestaurantMapper.mapFrom(
                openApiManager.fetch(page, pageSize),
                cuisineType,
                page,
                pageSize
        );
    }
}
