/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryQualityResponses
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-12
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.api.rest.response
 *
 * @Description : Typed immutable quality assessment and qualified observation responses.
 *
 */
package dz.sh.hidra.modules.telemetry.api.rest.response;

import java.math.BigDecimal;
import java.time.Instant;

public final class TelemetryQualityResponses {
    private TelemetryQualityResponses() { }
    public record Assessment(String id, String readingId, String pointId, String status,
            String inputQualityCodeId, String qualityCodeId, String trustLevel,
            String policyRuleId, String reasonCode, Instant assessedAt, String assessedByActorId,
            boolean replayed) { }
    public record Trusted(String id, String rawReadingId, String pointId, String assessmentId,
            String bindingId, String assetType, String assetId, String assetCode,
            String topologySnapshotId, BigDecimal numericValue, String textValue, Boolean booleanValue,
            String unitId, String qualityCodeId, String trustLevel, Instant sourceTimestamp,
            Instant receivedAt, Instant assessedAt, Instant trustedAt, String reasonCode,
            boolean replayed) { }
}
