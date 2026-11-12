/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryCommissioningResponses
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-12
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.api.rest.response
 *
 * @Description : Explicit client contract with owned source point binding and mapping provenance.
 *
 */
package dz.sh.hidra.modules.telemetry.api.rest.response;

import java.time.Instant;
import java.util.List;
public final class TelemetryCommissioningResponses {
    private TelemetryCommissioningResponses() { }
    public record Snapshot(String sourceId,String sourceCode,String deviceId,String deviceCode,
         String pointId,String pointCode,String pointTypeId,String signalTypeId,
         String unitId,String assetType,String assetId,String assetCode,
         String bindingId,String bindingRoleId,String mappingId,String externalTagName,
         Instant validFrom,Instant validUntil,boolean effective) { }
    public record History(List<Snapshot> items,boolean hasMore) { }
}
