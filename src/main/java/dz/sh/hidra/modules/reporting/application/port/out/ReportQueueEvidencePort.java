/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportQueueEvidencePort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-07
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.application.port.out
 *
 * @Description : Enforces Reporting execution and output integrity through explicit owner boundaries.
 *
 */
package dz.sh.hidra.modules.reporting.application.port.out;
/** Reporting-owned concrete evidence for a new queue operation. */
public interface ReportQueueEvidencePort {
    boolean eligibleTemplate(String versionId, String definitionId);
    boolean requiredParametersPresent(String requestId, String definitionId);
}
