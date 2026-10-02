/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HidraRealtimePublisher
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Class
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.realtime
 *
 * @Description : Publishes realtime operational events to STOMP topics and SSE clients.
 *
 */
package dz.sh.hidra.platform.realtime;

import java.time.Instant;
import java.util.Map;
import java.util.Objects;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

/**
 * Publishes realtime operational events to STOMP topics and SSE clients.
 */
@Component
public class HidraRealtimePublisher {

    private final SimpMessagingTemplate messagingTemplate;
    private final HidraSseRegistry sseRegistry;

    public HidraRealtimePublisher(SimpMessagingTemplate messagingTemplate, HidraSseRegistry sseRegistry) {
        this.messagingTemplate = Objects.requireNonNull(messagingTemplate, "SimpMessagingTemplate must not be null.");
        this.sseRegistry = Objects.requireNonNull(sseRegistry, "HidraSseRegistry must not be null.");
    }

    public void publish(String topic, Object payload) {
        String normalizedTopic = normalizeTopic(topic);
        Map<String, Object> envelope = Map.of(
                "topic", normalizedTopic,
                "timestamp", Instant.now(),
                "payload", payload == null ? Map.of() : payload
        );
        Message<Map<String, Object>> stompMessage = MessageBuilder.withPayload(envelope).build();
        messagingTemplate.send("/topic/" + normalizedTopic, stompMessage);
        sseRegistry.broadcast(normalizedTopic, envelope);
    }

    private static String normalizeTopic(String topic) {
        if (topic == null || topic.isBlank()) {
            return "hidra.events";
        }
        return topic.trim().replaceAll("^/topic/", "").replaceAll("^/", "");
    }
}
