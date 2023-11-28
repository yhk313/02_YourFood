package beforespring.yourfood.app.recommendation.infra.springbatch;

import beforespring.yourfood.app.recommendation.domain.RecommendationJobLauncher;
import beforespring.yourfood.app.recommendation.domain.RecommendationJobLauncherException;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class SpringBatchRecommendationJobLauncher implements RecommendationJobLauncher {
    private final JobLauncher jobLauncher;
    private final Job recommendationJob;

    @Override
    public void trigger(LocalDateTime triggeredAt) throws RecommendationJobLauncherException {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("triggeredAt", triggeredAt.toString())
                .toJobParameters();
        try {
            jobLauncher.run(recommendationJob, jobParameters);
        } catch (Exception e) {
            throw new RecommendationJobLauncherException(e);
        }
    }
}
