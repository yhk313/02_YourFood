package beforespring.yourfood.batch.config;

import beforespring.yourfood.app.restaurant.domain.Restaurant;
import beforespring.yourfood.app.restaurant.domain.RestaurantRepository;
import beforespring.yourfood.batch.rawrestaurant.RawRestaurantFetcherImpl;
import beforespring.yourfood.batch.rawrestaurant.RawRestaurantRepository;
import beforespring.yourfood.batch.rawrestaurant.fetch.RawRestaurantItemWriter;
import beforespring.yourfood.batch.rawrestaurant.fetch.RawRestaurantPagingItemReader;
import beforespring.yourfood.batch.rawrestaurant.fetch.RawRestaurantReaderResult;
import beforespring.yourfood.batch.rawrestaurant.fetch.RawRestaurantResultProcessor;
import beforespring.yourfood.batch.rawrestaurant.model.RawRestaurant;
import beforespring.yourfood.batch.rawrestaurant.update.RestaurantUpdateDataJpaWriter;
import beforespring.yourfood.batch.rawrestaurant.update.RestaurantUpdateProcessor;
import beforespring.yourfood.batch.rawrestaurant.update.RestaurantUpdateReader;
import beforespring.yourfood.batch.rawrestaurant.update.RestaurantUpdateReaderRepository;
import beforespring.yourfood.batch.rawrestaurant.update.RestaurantUpdateReaderRepositoryQueryDslImpl;
import beforespring.yourfood.batch.rawrestaurant.update.RestaurantUpdateReaderResult;
import beforespring.yourfood.batch.scheduler.BatchScheduler;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.StringUtils;

@Slf4j
@Configuration
@EnableScheduling
@EnableBatchProcessing
@EnableAsync
@RequiredArgsConstructor
public class BatchConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final RawRestaurantRepository rawRestaurantRepository;
    private final RestaurantRepository restaurantRepository;
    private final EntityManager em;
    private final RawRestaurantFetcherImpl rawRestaurantFetcher;
    private final JobLauncher jobLauncher;
    private final static String SYNC_JOB_NAME = "syncJob";
    private final static DateTimeFormatter dateTimeFormatter = DateTimeFormatter
                                                                   .ofPattern("yyyy-MM-dd'T'HH:mm:ss.nnnnnn");
    private final static int CHUNK_SIZE = 500;

    @Bean
    public BatchScheduler batchScheduler() {
        return new BatchScheduler(jobLauncher, restApiSyncJob());
    }

    @Bean
    public Job restApiSyncJob() {
        return jobBuilderFactory.get(SYNC_JOB_NAME)
                   .start(fetchRestaurantApi(null, null))
                   .next(updateRestaurant(null, null))
                   .build();
    }

    @Bean
    @JobScope
    public Step fetchRestaurantApi(
        @Value("#{jobParameters[requestedAt]}") String requestedAt,
        @Value("#{jobParameters[customStartParam]}") String customStartParam
    ) {
        return stepBuilderFactory.get("fetchRawRestaurantFromExternal")
                   .<RawRestaurantReaderResult, RawRestaurant>chunk(CHUNK_SIZE)
                   .reader(rawRestaurantPagingItemReader())
                   .processor(rawRestaurantResultProcessor(null, null))
                   .writer(rawRestaurantItemWriter())
                   .build();
    }

    /**
     * DB에 저장된 RawRestaurant 중, requestedAt 이후에 업데이트된 레코드를 불러오고, 이를 Restaurant에 반영함.
     * 파라미터는 직접 넣지 않고, JobParameters를 통해 전달됨.
     * @param requestedAt Job이 시작된 시간. 이 시점 이후에 변경된 데이터만 처리함.
//     * @param customStartParam 파라미터가 필요 없다면 빈 값을 반환. 빈 값이 아닌 경우에 이 시점 이후에 변경된 데이터를 처리함.
     * @return Step
     */
    @Bean
    @JobScope
    public Step updateRestaurant(
        @Value("#{jobParameters[requestedAt]}") String requestedAt,
        @Value("#{jobParameters[customStartParam]}") String customStartParam
    ) {
        LocalDateTime requestedAtParsed = LocalDateTime.parse(requestedAt).minusMinutes(1);

        if (StringUtils.hasText(customStartParam)) {
            requestedAtParsed = LocalDateTime.parse(customStartParam).minusMinutes(1);
        }

        log.info("startedAtParsed = {}", requestedAtParsed);
        return stepBuilderFactory.get("updateRestaurantFromRawRestaurant")
                   .<RestaurantUpdateReaderResult, Restaurant>chunk(CHUNK_SIZE)
                   .reader(new RestaurantUpdateReader(requestedAtParsed, restaurantUpdateReaderRepository()))
                   .processor(restaurantUpdateProcessor())
                   .writer(restaurantUpdateDataJpaWriter())
                   .build();
    }

    // todo refactor sido, cuisineType 정보는 fetcher에서 붙여서 반환해야함.
    @Bean
    @JobScope
    public RawRestaurantResultProcessor rawRestaurantResultProcessor(
        @Value("#{jobParameters[sido]}") String sido,
        @Value("#{jobParameters[cuisineType]}") String cuisineType
        ) {
        return new RawRestaurantResultProcessor(sido);
    }


    @Bean
    public RestaurantUpdateDataJpaWriter restaurantUpdateDataJpaWriter() {
        return new RestaurantUpdateDataJpaWriter(restaurantRepository);
    }

    @Bean
    public RestaurantUpdateReaderRepository restaurantUpdateReaderRepository() {
        return new RestaurantUpdateReaderRepositoryQueryDslImpl(em);
    }

    @Bean
    public RestaurantUpdateProcessor restaurantUpdateProcessor() {
        return new RestaurantUpdateProcessor();
    }

    @Bean
    public RawRestaurantPagingItemReader rawRestaurantPagingItemReader() {
        return new RawRestaurantPagingItemReader(
            rawRestaurantFetcher,
            rawRestaurantRepository,
            CHUNK_SIZE
        );
    }

    @Bean
    public RawRestaurantItemWriter rawRestaurantItemWriter() {
        return new RawRestaurantItemWriter(rawRestaurantRepository);
    }

}
