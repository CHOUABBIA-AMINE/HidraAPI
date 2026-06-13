/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HidraRealtimeMessageController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Class
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.realtime
 *
 * @Description : Handles basic STOMP messages for realtime client connectivity checks.
 *
 */
package dz.sh.hidra.platform.realtime;

import java.time.Instant;
import java.util.Map;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * Handles basic STOMP messages for realtime client connectivity checks.
 */
@Controller
public class HidraRealtimeMessageController {

    @MessageMapping("/ping")
    @SendTo("/topic/hidra.ping")
    public Map<String, Object> ping(Map<String, Object> payload) {
        return Map.of(
                "status", "pong",
                "timestamp", Instant.now(),
                "payload", payload == null ? Map.of() : payload
        );
    }
}
