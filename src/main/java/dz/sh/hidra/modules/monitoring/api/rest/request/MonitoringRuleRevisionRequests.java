/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MonitoringRuleRevisionRequests
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-13
 *
 * @Type        : Class
 * @Layer       : Api
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.api.rest.request
 *
 * @Description : Explicit rule revision and activation HTTP input DTOs.
 *
 */
package dz.sh.hidra.modules.monitoring.api.rest.request;

import java.time.Instant;

public final class MonitoringRuleRevisionRequests {
    private MonitoringRuleRevisionRequests(){ }
    public record Draft(String unitId,String evidencePolicyCode,Instant validFrom,Instant validTo){ }
    public record Activate(long expectedHeadRevision,Instant effectiveFrom,Instant effectiveUntil){ }
}
