package beforespring.yourfood.app.recommendation.domain;

import java.time.LocalDateTime;

public interface RecommendationJobLauncher {
    void trigger(LocalDateTime triggeredAt) throws RecommendationJobLauncherException;
}
