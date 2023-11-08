package beforespring.yourfood.app.restaurant.infra;

import beforespring.yourfood.app.restaurant.domain.CuisineType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Converter
public class CuisineTypeConverter implements AttributeConverter<SortedSet<CuisineType>, String> {
    /**
     * Enum type의 CuisineType SortedSet을 공백으로 구분하여 String으로 반환
     *
     * @param attribute the entity attribute value to be converted
     * @return 공백으로 구분된 cuisinType String
     */
    @Override
    public String convertToDatabaseColumn(SortedSet<CuisineType> attribute) {
        if (attribute == null || attribute.isEmpty()) {
            return null;
        }
        return attribute.stream()
                   .map(Enum::name)
                   .sorted()
                   .collect(Collectors.joining(" "));

    }

    /**
     * String을 공백으로 구분하여 분리하고 CusineType SortedSet로 반환
     *
     * @param dbData the data from the database column to be
     *               converted
     * @return 분리된 CuisineType SortedSet
     */
    @Override
    public SortedSet<CuisineType> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return new TreeSet<>();
        }

        String[] cuisines = dbData.split(" ");
        SortedSet<CuisineType> cuisineTypeSet = new TreeSet<>();
        for (String cuisine : cuisines) {
            cuisineTypeSet.add(CuisineType.valueOf(cuisine));
        }
        return cuisineTypeSet;
    }
}
