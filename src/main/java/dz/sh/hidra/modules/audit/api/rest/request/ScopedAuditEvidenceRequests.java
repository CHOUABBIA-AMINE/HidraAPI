/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ScopedAuditEvidenceRequests
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-19
 *
 * @Type        : Interface
 * @Layer       : API
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.api.rest.request
 *
 * @Description : Bounded T28 governed Risk approval scoped Audit evidence.
 *
 */
package dz.sh.hidra.modules.audit.api.rest.request;

import java.time.Instant;

/** Fixed purpose and event type; identity and scope are server-owned from the path. */
public final class ScopedAuditEvidenceRequests {
    private ScopedAuditEvidenceRequests(){}
    public record T28CreateAuditExport(String requestKey,String purpose,String eventType,Instant from,Instant to){}
}
