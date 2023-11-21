package beforespring.yourfood.app.restaurant.domain;

import beforespring.yourfood.app.restaurant.infra.CuisineTypeConverter;
import beforespring.yourfood.app.review.domain.Review;
import beforespring.yourfood.app.utils.Coordinates;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.context.event.EventListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Entity
@Table(
    name = "restaurant",
    indexes = {
        @Index(
            name = "idx__restaurant__name__address",
            columnList = "name, address",
            unique = true
        ),
        @Index(
            name = "idx__restaurant__name",
            columnList = "name"
        ),
        @Index(
            name = "idx__restaurant__sido__sigungu",
            columnList = "sido, sigungu"
        )
    }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Restaurant {
    @Id
    @Column(name = "restaurant_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String address;

    @Embedded
    private AddressCode addressCode;

    @Embedded
    private Coordinates coordinates;

    // todo: refactoring
    @Column(name = "cuisine_type")
    @Convert(converter = CuisineTypeConverter.class)
    private Set<CuisineType> cuisineType = new TreeSet<>();

    @Column(nullable = false, precision = 7, scale = 5, columnDefinition = "DECIMAL(7,5)")
    private BigDecimal rating;

    private Integer updatedRatingNum;

    @Column(name = "rating_updated_at")
    private LocalDateTime ratingUpdatedAt;

    @Column(nullable = false)
    private boolean operating;

    @Column(nullable = false)
    private boolean deleted;

    @Builder
    public Restaurant(
        String name,
        String description,
        String address,
        AddressCode addressCode,
        Coordinates coordinates,
        Set<CuisineType> cuisineType,
        Boolean operating) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.addressCode = addressCode;
        this.coordinates = coordinates;
        this.cuisineType = cuisineType;
        this.rating = BigDecimal.valueOf(0.0);
        this.updatedRatingNum = 0;
        this.operating = operating;
        this.deleted = false;
    }

    /**
     * 식당 운영 상태 수정
     *
     * @param operating 운영 중인지 여부
     */
    public void updateOperating(Boolean operating) {
        this.operating = operating;
    }

    /**
     * 식당 설명 수정
     *
     * @param description 식당 설명
     */
    public void updateDescription(String description) {
        this.description = description;
    }

    /**
     * 레스토랑 db를 삭제 상태로 변경
     */
    public void deleteRestaurantDb() {
        this.deleted = true;
    }

    /**
     * 새로운 리뷰 중 반영되지 않은 평점을 업데이트 함.
     *
     * @param reviews 새로운 리뷰 List
     */
    public void updateNewReviewRatings(List<Review> reviews) {
        int ratingSum = reviews.stream()
                            .map(Review::getRating)
                            .reduce(0, Integer::sum);

        this.rating = this.rating
                          .multiply(new BigDecimal(this.updatedRatingNum))
                          .add(new BigDecimal(ratingSum))
                          .divide(new BigDecimal(this.updatedRatingNum + reviews.size()), RoundingMode.HALF_UP)
                          .setScale(5, RoundingMode.HALF_UP);
        this.updatedRatingNum += reviews.size();
        this.ratingUpdatedAt = LocalDateTime.now();
    }

    /**
     * 수정된 리뷰 중 반영되지 않은 평점을 업데이트 함
     *
     * @param reviews 수정된 리뷰 List
     */
    public void updateModifiedReviewRatings(List<Review> reviews) {
        int beforeRatingSum = reviews.stream()
                                  .map(Review::getBeforeRating)
                                  .reduce(0, Integer::sum);
        int ratingSum = reviews.stream()
                            .map(Review::getRating)
                            .reduce(0, Integer::sum);

        this.rating = this.rating
                          .multiply(new BigDecimal(this.updatedRatingNum))
                          .add(new BigDecimal(ratingSum - beforeRatingSum))
                          .divide(new BigDecimal(this.updatedRatingNum), RoundingMode.HALF_UP)
                          .setScale(5, RoundingMode.HALF_UP);
        this.ratingUpdatedAt = LocalDateTime.now();
    }

    /**
     * 수정된 리뷰의 평점을 반영함.
     * 리뷰가 100개 초과되면 즉각 반영하지 않음.
     *
     * @param review 수정된 리뷰
     */
    public void updateModifiedReviewRating(Review review) {
        if (this.updatedRatingNum >= 100)
            return;
        this.rating = this.rating
                          .multiply(new BigDecimal(this.updatedRatingNum))
                          .add(new BigDecimal(review.getRating() - review.getBeforeRating()))
                          .divide(new BigDecimal(this.updatedRatingNum), RoundingMode.HALF_UP)
                          .setScale(5, RoundingMode.HALF_UP);
        this.ratingUpdatedAt = LocalDateTime.now();
    }

    /**
     * 새로운 리뷰의 평점을 반영함.
     * 리뷰가 100개 초과되면 즉각 반영하지 않음.
     *
     * @param review 새로운 리뷰
     */
    public void updateNewReviewRating(Review review) {
        if (this.updatedRatingNum >= 100)
            return;
        this.rating = this.rating
                          .multiply(BigDecimal.valueOf(this.updatedRatingNum).setScale(5))
                          .add(BigDecimal.valueOf(review.getRating()).setScale(5))
                          .divide(BigDecimal.valueOf(++this.updatedRatingNum).setScale(5), RoundingMode.HALF_UP)
                          .setScale(5, RoundingMode.HALF_UP);
        this.ratingUpdatedAt = LocalDateTime.now();
    }

    public void addCuisineType(Set<CuisineType> cuisineTypes) {
        this.cuisineType.addAll(cuisineTypes);
    }

    public void addCuisineType(CuisineType cuisineType) {
        this.cuisineType.add(cuisineType);
    }
}