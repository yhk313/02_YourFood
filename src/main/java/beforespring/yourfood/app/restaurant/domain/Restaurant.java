package beforespring.yourfood.app.restaurant.domain;

import beforespring.yourfood.app.utils.Coordinate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

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
    private Coordinate coordinate;

    @Enumerated(EnumType.STRING)
    @Column(name = "cuisine_type", nullable = false)
    private CuisineType cuisineType;

    @Column(nullable = false, precision = 3, scale = 8, columnDefinition = "DECIMAL(3,8)")
    private BigDecimal rating;

    @Column(nullable = false)
    private boolean operating;

    @Column(nullable = false)
    private boolean deleted;

    @Builder
    public Restaurant(
        String name,
        String description,
        String address,
        Coordinate coordinate,
        CuisineType cuisineType,
        Boolean operating) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.coordinate = coordinate;
        this.cuisineType = cuisineType;
        this.rating = BigDecimal.valueOf(0.0);
        this.operating = operating;
        this.deleted = false;
    }

    public void updateOperating(Boolean operating) {
        this.operating = operating;
    }

    public void updateDescription(String description) {
        this.description = description;
    }

    /**
     * 레스토랑 db를 삭제 상태로 변경
     */
    public void deleteRestaurantDb() {
        this.deleted = true;
    }

    public void updateRating(BigDecimal rating) {
        this.rating = rating;
    }
}
