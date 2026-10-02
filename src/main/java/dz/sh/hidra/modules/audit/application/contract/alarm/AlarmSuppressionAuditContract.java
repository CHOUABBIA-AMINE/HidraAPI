/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmSuppressionAuditContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-02
 *
 * @Type        : Interface
 * @Layer       : Application Contract
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.application.contract.alarm
 *
 * @Description : Narrow Audit evidence contract exported to Alarm suppression expiry orchestration.
 *
 */
package dz.sh.hidra.modules.audit.application.contract.alarm;

import java.time.Instant;

/**
 * Appends canonical Audit evidence for alarm suppression expiry.
 */
public interface AlarmSuppressionAuditContract {

    String appendExpiry(ExpiryEvidence evidence);

    record ExpiryEvidence(
            String suppressionId,
            String scopeType,
            String scopeReferenceId,
            String systemActorId,
            String correlationId,
            Instant expiredAt
    ) { }
}
