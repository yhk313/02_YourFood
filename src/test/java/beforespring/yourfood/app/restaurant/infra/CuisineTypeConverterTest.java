package beforespring.yourfood.app.restaurant.infra;

import beforespring.yourfood.app.restaurant.domain.CuisineType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class CuisineTypeConverterTest {
    CuisineTypeConverter cuisineTypeConverter = new CuisineTypeConverter();

    @Test
    @DisplayName("CuisineType이 정렬되어 문자열에 저장되어야 함")
    void convert_cuisine_type_to_string_test() {
        //given
        Set<CuisineType> toConvert = Set.of(CuisineType.KOREAN, CuisineType.WESTERN, CuisineType.JAPANESE, CuisineType.CHINESE);
        //when
        String convertedString = cuisineTypeConverter.convertToDatabaseColumn(toConvert);

        //then
        assertThat(convertedString)
            .describedAs("문자열로 변환이 되고 알파벳 순서로 정렬되어야 함")
            .isEqualTo("CHINESE JAPANESE KOREAN WESTERN");
    }

    @Test
    @DisplayName("중복되는 값은 제거하고 CuisineType으로 변환되어야 함")
    void convert_string_to_cuisine_type_test() {
        //given
        String toConvert = "JAPANESE KOREAN JAPANESE";

        //when
        Set<CuisineType> convertedCuisineType = cuisineTypeConverter.convertToEntityAttribute(toConvert);

        //then
        assertThat(convertedCuisineType)
            .describedAs("중복값을 제거하고 size가 2여야 함").hasSize(2);
    }

    @Test
    @DisplayName("문자열은 CuisineType으로 변환되어야 함")
    void convert_string_to_cuisine_type_sorted_test() {
        //given
        String toConvert = "JAPANESE KOREAN JAPANESE CHINESE";
        Set<CuisineType> expectedCuisineTypeSet = Set.of(CuisineType.KOREAN, CuisineType.CHINESE, CuisineType.JAPANESE);
        //when
        Set<CuisineType> convertedCuisineTypes = cuisineTypeConverter.convertToEntityAttribute(toConvert);

        //then
        assertThat(convertedCuisineTypes)
            .describedAs("변환된 cuisineType set은 KOREAN, CHINESE, JAPANESE type을 가지고 있어야 함")
            .containsExactlyInAnyOrderElementsOf(expectedCuisineTypeSet);
    }
}