/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MonitoringRuleRevision
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-13
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.domain.model
 *
 * @Description : Immutable rule revision with separate activation evidence.
 *
 */
package dz.sh.hidra.modules.monitoring.domain.model;

import dz.sh.hidra.modules.monitoring.domain.value.MonitoringRuleType;
import java.time.Instant;

/** Revision fields are immutable; activation fields are read-only joined evidence. */
public record MonitoringRuleRevision(
        String ruleId,long revision,String companyPartyId,String assetType,String assetId,
        String telemetryPointId,MonitoringRuleType ruleType,String unitId,String evidencePolicyCode,
        Instant validFrom,Instant validTo,String createdByActorId,Instant createdAt,
        Instant effectiveFrom,Instant effectiveUntil,Instant activatedAt,String activatedByActorId) {
    public MonitoringRuleRevision {
        if(!valid(ruleId)||revision<=0||!valid(companyPartyId)||!valid(assetType)
                ||!valid(assetId)||!valid(telemetryPointId)
                ||ruleType!=MonitoringRuleType.PLAN_COMPARISON
                ||!valid(unitId)||!valid(evidencePolicyCode)||!valid(createdByActorId)
                ||createdAt==null||validFrom==null||validTo==null||!validFrom.isBefore(validTo))
            throw new IllegalArgumentException("Invalid Monitoring revision.");
        if((effectiveFrom==null)!=(effectiveUntil==null)
                ||(effectiveFrom==null)!=(activatedAt==null)
                ||(effectiveFrom==null)!=(activatedByActorId==null)
                ||(effectiveFrom!=null&&(!effectiveFrom.isBefore(effectiveUntil)
                ||!valid(activatedByActorId))))
            throw new IllegalArgumentException("Invalid activation provenance or interval.");
    }
    private static boolean valid(String v){return v!=null&&!v.isBlank()&&v.equals(v.strip());}
}
