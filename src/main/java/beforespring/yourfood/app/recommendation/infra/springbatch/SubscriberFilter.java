package beforespring.yourfood.app.recommendation.infra.springbatch;

import beforespring.yourfood.app.recommendation.domain.Subscriber;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class SubscriberFilter implements ItemProcessor<Subscriber, Subscriber> {
    @Override
    public Subscriber process(Subscriber item) throws Exception {
        return item.notificationAgreed() ? item : null;
    }
}
