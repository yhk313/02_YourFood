package beforespring.yourfood.batch.config;

import beforespring.yourfood.batch.rawrestaurant.DummyRawRestaurantFetcher;
import beforespring.yourfood.batch.rawrestaurant.RawRestaurantFetcher;
import beforespring.yourfood.batch.rawrestaurant.fetch.RawRestaurantItemWriter;
import beforespring.yourfood.batch.rawrestaurant.fetch.RawRestaurantPagingItemReader;
import beforespring.yourfood.batch.rawrestaurant.fetch.RawRestaurantReaderResult;
import beforespring.yourfood.batch.rawrestaurant.RawRestaurantRepository;
import beforespring.yourfood.batch.rawrestaurant.fetch.RawRestaurantResultProcessor;
import beforespring.yourfood.batch.rawrestaurant.model.RawRestaurant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

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
    private final static String FETCH_JOB_NAME = "fetchJob";

    @Bean
    public Job restApiSyncJob() {
        return jobBuilderFactory.get(FETCH_JOB_NAME)
                   .start(fetchRestaurantApi(null))
                   .build();
    }

    // properties 값 불러오도록 고려해야함.
    @Bean
    @JobScope
    public Step fetchRestaurantApi(@Value("#{jobParameters[cuisineType]}") String cuisineType) {
        return stepBuilderFactory.get("updateRawRestaurantInfo")
                   .<RawRestaurantReaderResult, RawRestaurant>chunk(500)
                   .reader(rawRestaurantPagingItemReader())
                   .processor(rawRestaurantResultProcessor())
                   .writer(rawRestaurantItemWriter())
                   .build();
    }

    @Bean
    public RawRestaurantPagingItemReader rawRestaurantPagingItemReader() {
        return new RawRestaurantPagingItemReader(
            rawRestaurantFetcher(),
            rawRestaurantRepository,
            500
        );
    }

    @Bean
    public RawRestaurantFetcher rawRestaurantFetcher() {
        return new DummyRawRestaurantFetcher(11000L);
    }

    @Bean
    public RawRestaurantItemWriter rawRestaurantItemWriter() {
        return new RawRestaurantItemWriter(rawRestaurantRepository);
    }

    @Bean
    public RawRestaurantResultProcessor rawRestaurantResultProcessor() {
        return new RawRestaurantResultProcessor();
    }
}
