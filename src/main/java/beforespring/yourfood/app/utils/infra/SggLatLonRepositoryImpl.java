package beforespring.yourfood.app.utils.infra;

import beforespring.yourfood.app.utils.SggLatLon;
import beforespring.yourfood.app.utils.SggLatLonRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SggLatLonRepositoryImpl implements SggLatLonRepository {
    private final List<SggLatLon> sggLatLonList;

    @Override
    public List<SggLatLon> findAll() {
        return Collections.unmodifiableList(sggLatLonList);
    }
}
