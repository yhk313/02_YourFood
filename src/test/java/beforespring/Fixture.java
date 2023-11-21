package beforespring;

import beforespring.yourfood.app.member.domain.Member;
import beforespring.yourfood.app.restaurant.domain.AddressCode;
import beforespring.yourfood.app.restaurant.domain.CuisineType;
import beforespring.yourfood.app.restaurant.domain.Restaurant;
import beforespring.yourfood.app.review.domain.Review;
import beforespring.yourfood.app.utils.Coordinates;
import net.bytebuddy.utility.RandomString;

import java.math.BigDecimal;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

public class Fixture {
    static public final Random random = new Random();

    static public final CuisineType[] cuisineTypes = CuisineType.values();

    static public String randString() {
        return RandomString.make();
    }


    /**
     * @return 1 ~ 1,000,000 사이의 Long
     */
    static public Long randomPositiveLong() {
        return random.nextLong(0, 1000000);
    }


    static public Set<CuisineType> randomCuisineType() {
        Set<CuisineType> cuisineTypeSet = new TreeSet<>();
        cuisineTypeSet.add(cuisineTypes[random.nextInt(0, cuisineTypes.length)]);
        cuisineTypeSet.add(cuisineTypes[random.nextInt(0, cuisineTypes.length)]);
        cuisineTypeSet.add(cuisineTypes[random.nextInt(0, cuisineTypes.length)]);

        return cuisineTypeSet;
    }

    static public Member.MemberBuilder aMember() {
        return Member.builder()
                   .username(randString());
    }

    static public Restaurant.RestaurantBuilder aRestaurant() {
        Coordinates coordinates = new Coordinates(BigDecimal.valueOf(37), BigDecimal.valueOf(127));
        AddressCode addressCode = new AddressCode(randString(), randString());
        return Restaurant.builder()
                   .name(randString())
                   .coordinates(coordinates)
                   .address(randString())
                   .addressCode(addressCode)
                   .operating(true)
                   .description(randString() + randString() + randString())
                   .cuisineType(randomCuisineType());
    }

    static public Review.ReviewBuilder aReview() {
        return Review.builder()
                   .content(randString() + randString() + randString())
                   .rating(random.nextInt(0, 6));
    }

}
