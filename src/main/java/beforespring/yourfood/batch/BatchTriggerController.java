package beforespring.yourfood.batch;

import beforespring.yourfood.batch.scheduler.BatchScheduler;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/batch/trigger")
@RequiredArgsConstructor
public class BatchTriggerController {

    private final BatchScheduler batchScheduler;

    @PostMapping("/fetch")
    void trigger(
        @RequestParam(required = false) String customStartParam
    ) {
        batchScheduler.runFetchJob();
    }
}
