/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportingController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Interface
 * @Layer       : API
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.api.rest.controller
 *
 * @Description : Framework-neutral reporting controller contract.
 *
 */
package dz.sh.hidra.modules.reporting.api.rest.controller;
import dz.sh.hidra.modules.reporting.api.rest.request.*;
import dz.sh.hidra.modules.reporting.api.rest.response.*;

/**
 * Framework-neutral reporting controller contract.
 */
public interface ReportingController {
    ReportDefinitionResponse createReportDefinition(CreateReportDefinitionRequest request);
    ReportOutputArtifactResponse generateReportArtifact(GenerateReportArtifactRequest request);
    ReportRunResponse queueReportRun(QueueReportRunRequest request);
    ReportRequestResponse requestReport(RequestReportRequest request);
}
