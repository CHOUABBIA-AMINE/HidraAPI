/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : ScopedReportUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-18
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.application.port.in
 * @Description : Scoped, versioned T25 report delivery.
 */
package dz.sh.hidra.modules.reporting.application.port.in;
import dz.sh.hidra.modules.reporting.domain.model.ScopedReportRun.Scope;

public interface ScopedReportUseCase {
    record Submit(String requestKey, String definitionId, String templateVersionId, String metricRunId) {}
    record Render(String requestKey, String workflowInstanceId, String workflowActionId) {}
    record Seal(String requestKey) {}
    record Receipt(String requestId, String runId, String status, String definitionId,
            String templateVersionId, String templateHash, String metricRunId, String sourceHash,
            String workflowInstanceId, String workflowActionId, String reviewerId, String custodianId,
            String documentId, String documentVersionId, String acceptanceId,
            String checksumSha256, Long contentLength, String failureCode, boolean replayed) {}
    record Content(byte[] bytes, String checksumSha256) {
        public Content { bytes = bytes.clone(); }
        @Override public byte[] bytes() { return bytes.clone(); }
    }
    Receipt submit(Scope scope, Submit command);
    Receipt render(Scope scope, String requestId, Render command);
    Receipt read(Scope scope, String runId);
    Receipt seal(Scope scope, String runId, Seal command);
    Content content(Scope scope, String runId);
}
