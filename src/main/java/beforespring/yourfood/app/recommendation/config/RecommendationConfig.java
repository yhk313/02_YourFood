package beforespring.yourfood.app.recommendation.config;

import beforespring.yourfood.app.recommendation.domain.RecommendationDomainService;
import beforespring.yourfood.app.recommendation.domain.RecommendationJobLauncher;
import beforespring.yourfood.app.recommendation.domain.Subscriber;
import beforespring.yourfood.app.recommendation.domain.SubscriberRepository;
import beforespring.yourfood.app.recommendation.infra.springbatch.SpringBatchRecommendationJobLauncher;
import beforespring.yourfood.app.recommendation.infra.springbatch.SubscriberFilter;
import beforespring.yourfood.app.recommendation.infra.springbatch.SubscriberReader;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
@RequiredArgsConstructor
public class RecommendationConfig {
    private final RecommendationDomainService recommendationDomainService;

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final SubscriberRepository subscriberRepository;
    private final JobLauncher jobLauncher;
    private int chunkSize;

    @Value("${chunkSize:100}")
    public void setChunkSize(int chunkSize) {
        this.chunkSize = chunkSize;
    }

    @Bean
    public Job lunchRecommendationJob() {
        return jobBuilderFactory.get("lunchRecommendation")
                .start(recommend(null))
                .build();
    }

    @Bean
    @JobScope
    public Step recommend(
            @Value("#{jobParameters[triggeredDate]}") String triggeredDate
    ) {
        return stepBuilderFactory.get("recommend")
                .<Subscriber, Subscriber>chunk(chunkSize)
                .reader(subscriberReader(triggeredDate))
                .processor(subscriberItemProcessor())
                .writer(subscribers -> subscribers.forEach(recommendationDomainService::recommend))
                .build();
    }

    @Bean
    public SubscriberFilter subscriberItemProcessor() {
        return new SubscriberFilter();
    }

    @Bean
    @JobScope
    public SubscriberReader subscriberReader(
            @Value("#{jobParameters[triggeredDate]}") String triggeredDate
    ) {
        return new SubscriberReader(subscriberRepository, LocalDateTime.parse(triggeredDate), chunkSize);
    }

    @Bean
    public RecommendationJobLauncher recommendationJobLauncher() {
        return new SpringBatchRecommendationJobLauncher(jobLauncher, lunchRecommendationJob());
    }
}
