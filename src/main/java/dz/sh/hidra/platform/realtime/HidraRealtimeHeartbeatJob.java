/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HidraRealtimeHeartbeatJob
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Class
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.realtime
 *
 * @Description : Publishes heartbeat events for realtime clients.
 *
 */
package dz.sh.hidra.platform.realtime;

import java.time.Instant;
import java.util.Map;
import java.util.Objects;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Publishes heartbeat events for realtime clients.
 */
@Component
public class HidraRealtimeHeartbeatJob {

    private final HidraRealtimePublisher publisher;

    public HidraRealtimeHeartbeatJob(HidraRealtimePublisher publisher) {
        this.publisher = Objects.requireNonNull(publisher, "HidraRealtimePublisher must not be null.");
    }

    @Scheduled(fixedDelayString = "${hidra.platform.realtime.heartbeat-ms:30000}")
    public void publishHeartbeat() {
        publisher.publish("hidra.heartbeat", Map.of(
                "status", "alive",
                "timestamp", Instant.now()
        ));
    }
}
