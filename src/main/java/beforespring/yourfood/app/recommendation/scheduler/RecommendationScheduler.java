package beforespring.yourfood.app.recommendation.scheduler;


import beforespring.yourfood.app.recommendation.domain.RecommendationJobLauncher;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class RecommendationScheduler {
    private final RecommendationJobLauncher recommendationJobLauncher;

    @SneakyThrows
    @Scheduled(cron = "${beforespring.schedules.lunch}")
    public void trigger() {
        LocalDateTime triggeredAt = LocalDateTime.now().minusMinutes(1);
        recommendationJobLauncher.trigger(triggeredAt);
    }
}
