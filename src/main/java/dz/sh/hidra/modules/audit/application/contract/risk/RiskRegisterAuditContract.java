/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskRegisterAuditContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-05
 *
 * @Type        : Interface
 * @Layer       : Application Contract
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.application.contract.risk
 *
 * @Description : Audit-owned RiskRegister creation evidence contract exported to Risk.
 *
 */
package dz.sh.hidra.modules.audit.application.contract.risk;

import java.time.Instant;

public interface RiskRegisterAuditContract {

    String appendCreated(CreationEvidence evidence);

    record CreationEvidence(
            String registerId,
            String registerCode,
            String registerLabel,
            String actorId,
            String actorDisplayName,
            String organizationUnitId,
            String correlationId,
            Instant occurredAt
    ) { }
}
