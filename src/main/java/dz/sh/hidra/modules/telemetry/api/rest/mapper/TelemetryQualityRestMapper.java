/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryQualityRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-12
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.api.rest.mapper
 *
 * @Description : Maps protected quality service facts to explicit REST DTOs.
 *
 */
package dz.sh.hidra.modules.telemetry.api.rest.mapper;

import dz.sh.hidra.modules.telemetry.api.rest.response.TelemetryQualityResponses;
import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryQualityAssessmentPort;
import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryTrustedReadPort;
public final class TelemetryQualityRestMapper {
    private TelemetryQualityRestMapper() { }
    public static TelemetryQualityResponses.Assessment assessment(TelemetryQualityAssessmentPort.Assessment v) {
        return new TelemetryQualityResponses.Assessment(v.id(), v.readingId(), v.pointId(),
                v.status().name(), v.inputQualityCodeId(), v.resolvedQualityCodeId(),
                v.trustLevel().name(), v.ruleId(), v.reasonCode(), v.assessedAt(), v.actorId(),
                v.replayed());
    }
    public static TelemetryQualityResponses.Trusted trusted(TelemetryTrustedReadPort.Published p) {
        var v = p.reading();
        return new TelemetryQualityResponses.Trusted(v.id(), v.readingId(), v.pointId(),
                v.qualityAssessmentId(), v.bindingId(), v.topologyAssetTypeCode(),
                v.topologyAssetId(), v.topologyAssetCode(), v.topologySnapshotId(),
                v.numericValue(), v.textValue(), v.booleanValue(), v.unitId(), v.qualityCodeId(),
                v.trustLevel().name(), v.sourceTimestamp(), p.receivedAt(), p.assessedAt(),
                v.trustedAt(), p.reasonCode(), p.replayed());
    }
}
