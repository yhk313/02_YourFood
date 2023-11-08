package beforespring.yourfood.app.restaurant.infra;

import beforespring.yourfood.app.restaurant.domain.CuisineType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.SortedSet;
import java.util.TreeSet;

import static org.assertj.core.api.Assertions.assertThat;

class CuisineTypeConverterTest {
    CuisineTypeConverter cuisineTypeConverter = new CuisineTypeConverter();
    SortedSet<CuisineType> cuisineTypeList = new TreeSet<>();

    @Test
    @DisplayName("CuisineType이 정렬되어 문자열에 저장되어야 함")
    void convert_cuisine_type_to_string_test() {
        //given
        cuisineTypeList.add(CuisineType.KOREAN);
        cuisineTypeList.add(CuisineType.WESTERN);
        cuisineTypeList.add(CuisineType.JAPANESE);
        cuisineTypeList.add(CuisineType.CHINESE);

        //when
        String dbData = cuisineTypeConverter.convertToDatabaseColumn(cuisineTypeList);

        //then
        assertThat(dbData).isEqualTo("CHINESE JAPANESE KOREAN WESTERN");
    }

    @Test
    @DisplayName("중복되는 값은 제거하고 CuisineType으로 변환되어야 함")
    void convert_string_to_cuisine_type_test() {
        //given
        String dbData = "JAPANESE KOREAN JAPANESE";

        //when
        SortedSet<CuisineType> attribute = cuisineTypeConverter.convertToEntityAttribute(dbData);

        //then
        assertThat(attribute.size()).isEqualTo(2);
    }
}