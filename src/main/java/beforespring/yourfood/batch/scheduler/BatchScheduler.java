package beforespring.yourfood.batch.scheduler;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@RequiredArgsConstructor
public class BatchScheduler {

    private final JobLauncher jobLauncher;
    private final Job apiSyncJob;


    // 매일 새벽 4시에 실행
    @SneakyThrows
    @Scheduled(cron = "${beforespring.schedules.sync}")
    public void runJob() {
        jobLauncher.run(apiSyncJob, new JobParametersBuilder()
                                      .addString("sido", "경기도") // todo 임시 값임. fetcher에 들어가도록 수정해야함.
                                      .addString("requestedAt", LocalDateTime.now().toString())
                                      .toJobParameters());
    }
}
