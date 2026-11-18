/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : ScopedReportRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-18
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.application.port.out
 * @Description : Scoped, versioned T25 report delivery.
 */
package dz.sh.hidra.modules.reporting.application.port.out;
import dz.sh.hidra.modules.reporting.application.port.out.ScopedReportOwnerPort.Approval;
import dz.sh.hidra.modules.reporting.application.port.out.ScopedReportOwnerPort.Draft;
import dz.sh.hidra.modules.reporting.domain.model.ScopedReportRun.Phase;
import dz.sh.hidra.modules.reporting.domain.model.ScopedReportRun.Scope;
import java.util.Optional;

public interface ScopedReportRepositoryPort {
    record Template(String parameterId, String hash) {}
    record Row(Scope scope, String requestId, String requestKey, String actor, String definitionId,
            String templateVersionId, String templateHash, String metricRunId, String sourceHash,
            String runId, String renderKey, String workflowInstanceId, String workflowActionId,
            String reviewerId, Phase phase, Draft draft, String sealKey, String custodianId,
            String acceptanceId, String failureCode) {}
    void lockAdmission(Scope scope, String key);
    Optional<Row> submission(Scope scope, String key);
    Optional<Row> workflowTarget(String requestId);
    Optional<Row> request(Scope scope, String requestId, boolean lock);
    Optional<Row> run(Scope scope, String runId, boolean lock);
    Template template(String definitionId, String templateVersionId);
    void submit(Row row, String parameterId, String projectionJson);
    void queue(Row row, String runId, String renderKey, Approval approval);
    void start(String runId);
    void rendered(String runId, Draft draft);
    void failed(String runId, String failureCode);
    void complete(Row row, String artifactId, String sealKey, String custodian, String acceptanceId);
    boolean managedRequest(String id);
    boolean managedRun(String id);
}
