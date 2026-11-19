/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ScopedAuditEvidenceResponses
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-19
 *
 * @Type        : Interface
 * @Layer       : API
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.api.rest.response
 *
 * @Description : Bounded T28 governed Risk approval scoped Audit evidence.
 *
 */
package dz.sh.hidra.modules.audit.api.rest.response;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

/** Safe, minimized transport contracts; there is no raw Audit payload projection. */
public final class ScopedAuditEvidenceResponses {
    private ScopedAuditEvidenceResponses(){}
    public record T28AuditEvent(String eventId,String eventType,String operation,String targetId,
                                String workflowActionId,Instant occurredAt){}
    public record T28AuditEventPage(List<T28AuditEvent> items,String nextCursor){}
    public record T28AuditExportManifest(UUID exportId,String status,String format,String purpose,
            String companyId,String facilityId,String eventType,Instant from,Instant to,
            int eventCount,int byteLength,String sha256,Instant completedAt) {}
}
