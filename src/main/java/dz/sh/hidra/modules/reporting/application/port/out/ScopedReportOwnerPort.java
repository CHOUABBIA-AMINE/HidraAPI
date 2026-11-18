/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : ScopedReportOwnerPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-18
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.application.port.out
 * @Description : Scoped, versioned T25 report delivery.
 */
package dz.sh.hidra.modules.reporting.application.port.out;
import dz.sh.hidra.modules.reporting.domain.model.ScopedReportRun.Scope;
import dz.sh.hidra.modules.reporting.domain.model.ScopedReportRun.Projection;
import java.time.Instant;

public interface ScopedReportOwnerPort {
    enum Operation { SUBMIT, RENDER, READ, SEAL }
    final class RenderFailure extends RuntimeException { public RenderFailure(Throwable cause) { super(cause); } }
    record Approval(String instanceId, String actionId, String reviewerId, Instant actedAt,
            String workflowDefinitionId, int workflowDefinitionVersion) {}
    record Draft(String documentId, String versionId, String checksum, long length) {}
    String require(Scope scope, Operation operation);
    Projection metric(Scope scope, String metricRunId);
    Approval approval(String requestId, String instanceId, String actionId);
    void requireDraftAuthority(Scope scope);
    Draft draft(Scope scope, String runId, String actor, byte[] bytes);
    String seal(Scope scope, String runId, Draft draft);
    byte[] content(Scope scope, String runId, Draft draft, String acceptance);
}
