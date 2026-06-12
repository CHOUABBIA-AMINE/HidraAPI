/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportingController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
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

import dz.sh.hidra.modules.reporting.api.rest.request.CreateReportDefinitionRequest;
import dz.sh.hidra.modules.reporting.api.rest.request.RequestReportRequest;
import dz.sh.hidra.modules.reporting.api.rest.response.ReportDefinitionResponse;
import dz.sh.hidra.modules.reporting.api.rest.response.ReportRequestResponse;

/**
 * Framework-neutral reporting controller contract.
 */
public interface ReportingController {

    ReportDefinitionResponse createReportDefinition(CreateReportDefinitionRequest request);

    ReportRequestResponse requestReport(RequestReportRequest request);
}
