package beforespring.yourfood.app.utils;

import beforespring.yourfood.web.api.restaurant.response.RegionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SggLatLonServiceImpl implements SggLatLonService {
    private final SggLatLonRepository sggLatLonRepository;

    @Override
    public List<RegionDto> getAllSggLatLon() {
        List<SggLatLon> allRegion = sggLatLonRepository.findAll();
        return allRegion.stream()
            .map(sggLatLon -> new RegionDto(
                sggLatLon.getSiGunGu(),
                sggLatLon.getSiDo(),
                sggLatLon.getLat(),
                sggLatLon.getLon()
            ))
            .collect(Collectors.toList());
    }
}
