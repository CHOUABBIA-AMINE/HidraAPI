/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryReadingIngestionReferencePort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-10
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.port.out
 *
 * @Description : Resolves and locks current Telemetry source, point, mapping, units and quality.
 *
 */
package dz.sh.hidra.modules.telemetry.application.port.out;


import java.time.Instant;
public interface TelemetryReadingIngestionReferencePort {
    Scope current(String pointId, String claimedSourceId, Instant now);
    void validateValue(Scope scope, String qualityCodeId, String declaredUnitId, String valueShape);
    record Scope(String sourceId, String deviceId, String pointId, String unitId,
                 String assetType, String assetId, String mappingId, String signalShape,
                 boolean numericUnitExempt) { }
}
