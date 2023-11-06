package beforespring.yourfood.app.utils.infra;

import beforespring.yourfood.app.utils.SggLatLon;
import beforespring.yourfood.app.utils.SggLatLonRepository;
import beforespring.yourfood.config.SggLatLonConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class SggLatLonRepositoryImplTest {
    private SggLatLonConfig sggLatLonConfig = new SggLatLonConfig();
    private SggLatLonRepository sggLatLonRepository = sggLatLonConfig.sggLatLonRepository();

    @Test
    @DisplayName("sggLatLonRepository.findAll()을 싱글톤 객체를 반환해야 함")
    void sgg_find_all_test() {
        //given

        //when
        List<SggLatLon> sggLatLonList1 = sggLatLonRepository.findAll();
        List<SggLatLon> sggLatLonList2 = sggLatLonRepository.findAll();

        //then
        assertThat(sggLatLonList1).isNotEmpty().isEqualTo(sggLatLonList2);
    }

    @Test
    @DisplayName("sggLatLonRepository.findAll()로 반환된 List는 수정이 불가함")
    void sgg_list_read_only_test() {
        //given
        SggLatLon sggLatLon = new SggLatLon("뿡뿡", "빵빵", "127", "37");

        //when
        List<SggLatLon> sggLatLonList = sggLatLonRepository.findAll();

        //then
        assertThatThrownBy(() ->
                               sggLatLonList.add(sggLatLon)
        ).describedAs("list 수정 시 UnsupportedOperationException이 발생해야 합니다.").isInstanceOf(UnsupportedOperationException.class);

    }


}