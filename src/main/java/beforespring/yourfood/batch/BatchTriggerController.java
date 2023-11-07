package beforespring.yourfood.batch;

import beforespring.yourfood.app.restaurant.domain.CuisineType;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/batch/trigger")
@RequiredArgsConstructor
public class BatchTriggerController {

    private final JobLauncher jobLauncher;
    private final Job fetchJob;

    @PostMapping("/fetch/{sido}/{cuisineType}")
    void trigger(
        @PathVariable String sido,
        @PathVariable CuisineType cuisineType,
        @RequestParam(required = false) String customStartParam
    )
        throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        jobLauncher.run(fetchJob, new JobParametersBuilder()
                                      .addString("sido", sido)
                                      .addString("customStartParam", customStartParam)
                                      .addString("cuisineType", cuisineType.toString())
                                      .addString("requestedAt", LocalDateTime.now().toString())
                                      .toJobParameters());
    }
}
