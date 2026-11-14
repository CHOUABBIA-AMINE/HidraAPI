/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : LeakMonitoringEvidencePort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-14
 * @Type        : Class
 * @Layer       : Application
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.application.port.out
 * @Description : T13 governed source-linked suspected leak candidate delivery.
 */
package dz.sh.hidra.modules.leakdetection.application.port.out;

import java.math.BigDecimal;
import java.time.Instant;

/** Only current-authorized Monitoring owner projections cross this port. */
public interface LeakMonitoringEvidencePort {
    record Snapshot(String evaluationId, String ruleId, long ruleRevision, String companyPartyId,
            String assetType, String assetId, String unitId, String outcome, BigDecimal difference,
            String targetId, String approvalId, String trustedReadingId, Instant evaluatedAt) { }
    Snapshot authorized(String evaluationId);
}
