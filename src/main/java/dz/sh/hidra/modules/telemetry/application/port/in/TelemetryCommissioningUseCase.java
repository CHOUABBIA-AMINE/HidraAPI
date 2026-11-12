/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryCommissioningUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-12
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.port.in
 *
 * @Description : Scoped source/device/point commissioning with effective lineage.
 *
 */
package dz.sh.hidra.modules.telemetry.application.port.in;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface TelemetryCommissioningUseCase {
    record Enroll(UUID requestId, String companyPartyId, String assetType, String assetId,
                  String assetCode, String sourceCode, String sourceNameFr,
                  String sourceTypeId, String protocolId, String deviceCode, String deviceNameFr,
                  String deviceTypeId, String pointCode, String pointNameFr, String pointTypeId,
                  String signalTypeId, String unitId, String bindingRoleId,
                  String externalTagName, Instant validFrom, Instant validUntil) { }
    record Rebind(UUID requestId, String companyPartyId, String pointId,
                  String assetType, String assetId, String assetCode,
                  String bindingRoleId, String externalTagName,
                  Instant validFrom, Instant validUntil) { }
    record Snapshot(String sourceId, String sourceCode, String deviceId, String deviceCode,
                    String pointId, String pointCode, String pointTypeId, String signalTypeId,
                    String unitId, String assetType, String assetId, String assetCode,
                    String bindingId, String bindingRoleId, String mappingId,
                    String externalTagName, Instant validFrom, Instant validUntil,
                    boolean effective) { }
    record History(List<Snapshot> items, boolean hasMore) {
        public History { items = List.copyOf(items); }
    }
    Snapshot enroll(Enroll command);
    Snapshot rebind(Rebind command);
    Snapshot effective(String companyPartyId, String pointId, Instant asOf);
    History history(String companyPartyId, String pointId, int limit);
}
