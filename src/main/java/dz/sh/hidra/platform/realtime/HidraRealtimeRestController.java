/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HidraRealtimeRestController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Class
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.realtime
 *
 * @Description : Exposes realtime capabilities and Server-Sent Event stream endpoints.
 *
 */
package dz.sh.hidra.platform.realtime;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * Exposes realtime capabilities and Server-Sent Event stream endpoints.
 */
@RestController
@Validated
@RequestMapping("/api/v1/realtime")
public class HidraRealtimeRestController {

    private final HidraSseRegistry sseRegistry;

    public HidraRealtimeRestController(HidraSseRegistry sseRegistry) {
        this.sseRegistry = Objects.requireNonNull(sseRegistry, "HidraSseRegistry must not be null.");
    }

    @GetMapping("/capabilities")
    public Map<String, Object> capabilities() {
        return Map.of(
                "module", "realtime",
                "stompEndpoint", "/api/v1/realtime/ws",
                "sseEndpoint", "/api/v1/realtime/sse",
                "applicationDestinationPrefix", "/app",
                "brokerTopics", List.of("/topic/hidra.heartbeat", "/topic/hidra.events", "/topic/hidra.notification"),
                "activeSseClients", sseRegistry.activeClients()
        );
    }

    @GetMapping(value = "/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter sse() {
        return sseRegistry.register();
    }
}
