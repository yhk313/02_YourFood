package beforespring.yourfood.batch.scheduler;

import beforespring.yourfood.app.restaurant.domain.CuisineType;
import java.time.LocalDateTime;
import java.util.Arrays;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
public class BatchScheduler {

    private final JobLauncher jobLauncher;

    private final Job fetchJob;
    private final Job updateJob;

    public BatchScheduler(JobLauncher jobLauncher, Job fetchJob, Job updateJob) {
        this.jobLauncher = jobLauncher;
        this.fetchJob = fetchJob;
        this.updateJob = updateJob;
    }

    // 매일 새벽 4시에 실행
    @SneakyThrows
    @Scheduled(cron = "${beforespring.schedules.sync}")
    public void runFetchJob() {
        LocalDateTime requestedAt = LocalDateTime.now();

        // fetch job 실행
        Arrays.stream(CuisineType.values())
            .forEach(cuisineType -> {
                try {
                    jobLauncher.run(
                        fetchJob,
                        new JobParametersBuilder()
                            .addString("requestedAt", requestedAt.toString())
                            .addString("cuisineType", cuisineType.toString())
                            .toJobParameters()
                    );
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

        // fetch job이 모두 완료되면 update job 실행
        runUpdateJob(requestedAt);
    }

    public void runUpdateJob(LocalDateTime fetchJobRequestedAt)
        throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
        jobLauncher.run(
            updateJob,
            new JobParametersBuilder()
                .addString("requestedAt", fetchJobRequestedAt.toString())
                .toJobParameters()
        );
    }
}
