/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RealtimeEventEnvelope
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-11
 *
 * @Type        : Record
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.realtime
 *
 * @Description : Defines the stable technical envelope for future Hidra realtime domain-event publications.
 *
 */
package dz.sh.hidra.platform.realtime;

import java.time.Instant;
import java.util.Map;

/**
 * Technical event envelope shared by all future realtime event publishers.
 */
public record RealtimeEventEnvelope(
        String schemaVersion,
        String eventId,
        String eventType,
        Instant occurredAt,
        String module,
        String aggregateType,
        String aggregateId,
        String correlationId,
        Map<String, Object> payload
) { }
