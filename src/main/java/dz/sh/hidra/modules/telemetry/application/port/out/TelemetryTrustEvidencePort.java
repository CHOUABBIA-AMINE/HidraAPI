/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryTrustEvidencePort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-06
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.port.out
 *
 * @Description : Resolves locked Telemetry-owned facts used by the trust operation.
 *
 */
package dz.sh.hidra.modules.telemetry.application.port.out;

import dz.sh.hidra.modules.telemetry.domain.model.TelemetryReading;
import dz.sh.hidra.modules.telemetry.domain.value.*;
import java.time.Instant;
import java.util.List;

public interface TelemetryTrustEvidencePort {
    Evidence load(String readingId, String assessmentId, Instant trustedAt);
    record Binding(String id, String assetType, String assetId, String assetCode, String snapshotId) { }
    record Evidence(TelemetryReading reading, String assessmentId, String assessedReadingId,
            String assessedPointId, AssessmentStatus assessmentStatus, TrustLevel trustLevel,
            String qualityCodeId, TelemetryLifecycleStatus pointStatus, String effectiveUnitId,
            boolean qualityEligible, boolean unitExists, boolean batchExists, List<Binding> bindings) {
        public Evidence { bindings = List.copyOf(bindings); }
    }
}
