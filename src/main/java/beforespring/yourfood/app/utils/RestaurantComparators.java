package beforespring.yourfood.app.utils;

import beforespring.yourfood.app.restaurant.domain.Restaurant;

import java.util.Comparator;

import static beforespring.yourfood.app.utils.Coordinates.calculateDistance;
import static java.util.Comparator.comparing;

public class RestaurantComparators {
    public static Comparator<Restaurant> byRatingAverage(boolean desc) {
        Comparator<Restaurant> ret = comparing(Restaurant::getRating);
        return desc ? ret.reversed() : ret;
    }

    public static Comparator<Restaurant> byDistance(boolean desc, Coordinates currentCoords) {
        Comparator<Restaurant> ret = comparing(
            restaurant ->
                calculateDistance(restaurant.getCoordinates(), currentCoords)
        );
        return desc ? ret.reversed() : ret;
    }
    public static Comparator<Restaurant> sortBy(OrderBy orderBy, boolean descendingOrder,
        Coordinates coordinates) {
        return orderBy == OrderBy.RATING
                   ? byRatingAverage(descendingOrder)
                   : byDistance(descendingOrder, coordinates);
    }

}
