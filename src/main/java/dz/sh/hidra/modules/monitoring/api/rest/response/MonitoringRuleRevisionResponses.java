/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MonitoringRuleRevisionResponses
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-13
 *
 * @Type        : Class
 * @Layer       : Api
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.api.rest.response
 *
 * @Description : Immutable version and activation HTTP readback.
 *
 */
package dz.sh.hidra.modules.monitoring.api.rest.response;

import dz.sh.hidra.modules.monitoring.domain.model.MonitoringRuleRevision;
import java.time.Instant;

public final class MonitoringRuleRevisionResponses {
    private MonitoringRuleRevisionResponses(){ }
    public record View(String ruleId,long revision,String companyPartyId,String assetType,String assetId,
            String telemetryPointId,String ruleType,String unitId,String evidencePolicyCode,
            Instant validFrom,Instant validTo,String createdByActorId,Instant createdAt,
            Instant effectiveFrom,Instant effectiveUntil,Instant activatedAt,String activatedByActorId){
        public static View of(MonitoringRuleRevision r){
            return new View(r.ruleId(),r.revision(),r.companyPartyId(),r.assetType(),r.assetId(),
                    r.telemetryPointId(),r.ruleType().name(),r.unitId(),r.evidencePolicyCode(),
                    r.validFrom(),r.validTo(),r.createdByActorId(),r.createdAt(),
                    r.effectiveFrom(),r.effectiveUntil(),r.activatedAt(),r.activatedByActorId());
        }
    }
}
