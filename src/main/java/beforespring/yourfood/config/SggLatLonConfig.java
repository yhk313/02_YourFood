package beforespring.yourfood.config;

import beforespring.yourfood.app.utils.SggLatLon;
import beforespring.yourfood.app.utils.SggLatLonRepository;
import beforespring.yourfood.app.utils.infra.SggLatLonRepositoryImpl;
import beforespring.yourfood.config.exception.CsvIOException;
import lombok.Getter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
@Getter
public class SggLatLonConfig {
    /**
     * sggLatLon.csv 파일을 SggLatLon List로 변환하기 위한 파서
     *
     */
    private List<SggLatLon> parseSggCsv() {
        Resource resource = new ClassPathResource("sggLatLon.csv");
        List<SggLatLon> sggLatLons = new ArrayList<>();
        try {
            InputStream inputStream = resource.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            reader.readLine();
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                sggLatLons.add(
                    SggLatLon.builder()
                        .siDo(data[0])
                        .siGunGu(data[1])
                        .lon(data[2])
                        .lat(data[3]).build()
                );
            }
        } catch (IOException e) {
            throw new CsvIOException(e);
        }
        return Collections.unmodifiableList(sggLatLons);
    }

    @Bean
    public SggLatLonRepository sggLatLonRepository() {
        return new SggLatLonRepositoryImpl(parseSggCsv());
    }
}
