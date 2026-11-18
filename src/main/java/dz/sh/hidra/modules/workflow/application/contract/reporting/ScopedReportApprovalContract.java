/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : ScopedReportApprovalContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-18
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.contract.reporting
 * @Description : Scoped, versioned T25 report delivery.
 */
package dz.sh.hidra.modules.workflow.application.contract.reporting;
import java.time.Instant;
import java.util.Optional;

public interface ScopedReportApprovalContract {
    record Decision(String instanceId, String actionId, String reviewerId, Instant actedAt,
            String definitionId, int definitionVersion) {}
    Optional<Decision> approved(String requestId, String instanceId, String actionId);
}
