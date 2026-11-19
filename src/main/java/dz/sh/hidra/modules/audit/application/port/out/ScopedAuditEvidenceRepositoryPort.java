/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ScopedAuditEvidenceRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-19
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.application.port.out
 *
 * @Description : Defines exact T28 governed scoped Audit evidence.
 *
 */
package dz.sh.hidra.modules.audit.application.port.out;

import dz.sh.hidra.modules.audit.domain.model.ScopedAuditEvidence.*;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/** Audit-owned immutable SQL with scoped predicates and keyset ordering. */
public interface ScopedAuditEvidenceRepositoryPort {
    void bindApproved(String eventId,String workflowActionId,String assessmentId,
                      Scope scope,Instant occurredAt);
    List<Event> events(Scope scope,Window window,Boundary after,int max);
    void lockRequest(Criteria criteria,String requester);
    Optional<Manifest> existing(Criteria criteria,String requester);
    Optional<Manifest> manifest(Scope scope,String requester,UUID id);
    Optional<Artifact> artifact(Scope scope,String requester,UUID id);
    Manifest store(Criteria criteria,String requester,byte[] bytes,int count,String sha256,UUID receiptId);
    void receipt(Scope scope,String actor,String action,String outcome,UUID exportId,String locatorDigest,UUID receiptId);
}
