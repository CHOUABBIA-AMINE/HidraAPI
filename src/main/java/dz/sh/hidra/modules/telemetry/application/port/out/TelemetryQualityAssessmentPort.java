/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryQualityAssessmentPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-12
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.port.out
 *
 * @Description : Owns transactional authoritative raw assessment and readback.
 *
 */
package dz.sh.hidra.modules.telemetry.application.port.out;

import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryReadingIngestionReferencePort.Scope;
import dz.sh.hidra.modules.telemetry.domain.value.AssessmentStatus;
import dz.sh.hidra.modules.telemetry.domain.value.TrustLevel;
import java.time.Instant;

public interface TelemetryQualityAssessmentPort {
    Scope current(String pointId, Instant at);
    Assessment assess(Scope scope, String readingId, String actorId, Instant at);
    Assessment find(String pointId, String readingId);
    void lock(String pointId, String readingId);
    void requireCurrentEligibility(Scope scope, Assessment decision, Instant at);

    record Assessment(String id, String readingId, String pointId, AssessmentStatus status,
            String inputQualityCodeId, String resolvedQualityCodeId, TrustLevel trustLevel,
            String ruleId, String reasonCode, Instant assessedAt, String actorId, boolean replayed) { }
}
