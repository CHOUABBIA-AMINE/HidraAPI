/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : CustodyIntervalEvidenceContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-15
 * @Type        : Interface
 * @Layer       : Application Contract
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.contract.custody
 * @Description : Returns only qualified immutable interval total attestations to Custody.
 */
package dz.sh.hidra.modules.telemetry.application.contract.custody;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;
public interface CustodyIntervalEvidenceContract {
    record Evidence(String id,String trustedReadingId,String rawReadingId,String assessmentId,
        String pointId,String bindingId,String meterRunId,String meteringSystemId,
        String assetType,String assetId,BigDecimal quantity,String unitId,
        Instant start,Instant end) {}
    Optional<Evidence> qualified(String evidenceId,String expectedMeterRun,String expectedMeter,
                                  String assetType,String assetId);
}
