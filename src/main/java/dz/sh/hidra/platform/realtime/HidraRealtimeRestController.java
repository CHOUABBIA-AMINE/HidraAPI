/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HidraRealtimeRestController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-11
 *
 * @Type        : Class
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.realtime
 *
 * @Description : Exposes verified realtime transport capabilities and the Server-Sent Event stream.
 *
 */
package dz.sh.hidra.platform.realtime;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * Realtime transport contract and SSE registration boundary.
 */
@RestController
@Validated
@RequestMapping("/api/v1/realtime")
@Tag(name = "Realtime", description = "Verified realtime transports, event envelope metadata, and recovery semantics.")
public final class HidraRealtimeRestController {

    private final HidraSseRegistry sseRegistry;
    private final String stompEndpoint;
    private final String sseEndpoint;
    private final long heartbeatMs;

    public HidraRealtimeRestController(
            HidraSseRegistry sseRegistry,
            @Value("${hidra.platform.realtime.stomp-endpoint:/api/v1/realtime/ws}") String stompEndpoint,
            @Value("${hidra.platform.realtime.sse-endpoint:/api/v1/realtime/sse}") String sseEndpoint,
            @Value("${hidra.platform.realtime.heartbeat-ms:30000}") long heartbeatMs
    ) {
        this.sseRegistry = Objects.requireNonNull(sseRegistry, "HidraSseRegistry must not be null.");
        this.stompEndpoint = requireText(stompEndpoint, "STOMP endpoint");
        this.sseEndpoint = requireText(sseEndpoint, "SSE endpoint");
        this.heartbeatMs = heartbeatMs;
    }

    @GetMapping("/capabilities")
    @Operation(
            summary = "Get realtime capabilities",
            description = "Returns only transports and event publications verified in the current repository."
    )
    public RealtimeCapabilities capabilities() {
        return new RealtimeCapabilities(
                "realtime",
                stompEndpoint,
                sseEndpoint,
                "/app",
                List.of("/topic", "/queue"),
                "/user",
                heartbeatMs,
                "1",
                List.of(
                        "schemaVersion",
                        "eventId",
                        "eventType",
                        "occurredAt",
                        "module",
                        "aggregateType",
                        "aggregateId",
                        "correlationId",
                        "payload"
                ),
                "transport-configured-no-domain-publishers",
                "query-after-reconnect",
                List.of(),
                sseRegistry.activeClients()
        );
    }

    @GetMapping(value = "/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @Operation(summary = "Open realtime SSE stream")
    public SseEmitter sse() {
        return sseRegistry.register();
    }

    private static String requireText(String value, String label) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(label + " must not be blank.");
        }
        return value.trim();
    }

    public record RealtimeCapabilities(
            String module,
            String stompEndpoint,
            String sseEndpoint,
            String applicationDestinationPrefix,
            List<String> brokerPrefixes,
            String userDestinationPrefix,
            long heartbeatMs,
            String envelopeSchemaVersion,
            List<String> envelopeFields,
            String publicationStatus,
            String recoveryStrategy,
            List<RealtimeEventContract> eventFamilies,
            int activeSseClients
    ) { }

    public record RealtimeEventContract(
            String eventType,
            String destination,
            String payloadSchema,
            String ordering,
            String recovery
    ) { }
}
