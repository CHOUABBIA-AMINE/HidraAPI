/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryCommissioningRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-12
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.api.rest.mapper
 *
 * @Description : Explicit REST-to-application commissioning mapping.
 *
 */
package dz.sh.hidra.modules.telemetry.api.rest.mapper;

import dz.sh.hidra.modules.telemetry.api.rest.request.TelemetryCommissioningRequests;
import dz.sh.hidra.modules.telemetry.api.rest.response.TelemetryCommissioningResponses;
import dz.sh.hidra.modules.telemetry.application.port.in.TelemetryCommissioningUseCase;
import java.util.List;
public final class TelemetryCommissioningRestMapper {
    private TelemetryCommissioningRestMapper() { }
    public static TelemetryCommissioningUseCase.Enroll toCommand(TelemetryCommissioningRequests.Enroll c) {
        return new TelemetryCommissioningUseCase.Enroll(c.requestId(),c.companyPartyId(),
            c.assetType(),c.assetId(),c.assetCode(),c.sourceCode(),c.sourceNameFr(),
            c.sourceTypeId(),c.protocolId(),c.deviceCode(),c.deviceNameFr(),
            c.deviceTypeId(),c.pointCode(),c.pointNameFr(),c.pointTypeId(),
            c.signalTypeId(),c.unitId(),c.bindingRoleId(),c.externalTagName(),
            c.validFrom(),c.validUntil());
    }
    public static TelemetryCommissioningUseCase.Rebind toCommand(
            String pointId,TelemetryCommissioningRequests.Rebind c) {
        return new TelemetryCommissioningUseCase.Rebind(c.requestId(),c.companyPartyId(),pointId,
            c.assetType(),c.assetId(),c.assetCode(),c.bindingRoleId(),c.externalTagName(),
            c.validFrom(),c.validUntil());
    }
    public static TelemetryCommissioningResponses.Snapshot toResponse(TelemetryCommissioningUseCase.Snapshot s) {
        return new TelemetryCommissioningResponses.Snapshot(
            s.sourceId(),s.sourceCode(),s.deviceId(),s.deviceCode(),
            s.pointId(),s.pointCode(),s.pointTypeId(),s.signalTypeId(),s.unitId(),
            s.assetType(),s.assetId(),s.assetCode(),s.bindingId(),s.bindingRoleId(),
            s.mappingId(),s.externalTagName(),s.validFrom(),s.validUntil(),s.effective());
    }
    public static TelemetryCommissioningResponses.History toResponse(TelemetryCommissioningUseCase.History h) {
        return new TelemetryCommissioningResponses.History(h.items().stream().map(
                TelemetryCommissioningRestMapper::toResponse).toList(),h.hasMore());
    }
}
