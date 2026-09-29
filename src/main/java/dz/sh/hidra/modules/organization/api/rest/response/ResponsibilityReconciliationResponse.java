/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ResponsibilityReconciliationResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-29
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.api.rest.response
 *
 * @Description : Read-only API response for governed responsibility integrity reconciliation.
 *
 */
package dz.sh.hidra.modules.organization.api.rest.response;

import dz.sh.hidra.modules.organization.application.dto.ResponsibilityReconciliationResult.IssueCode;
import java.util.List;

public record ResponsibilityReconciliationResponse(
        int scannedAssignments,
        List<Issue> issues
) {
    public record Issue(String assignmentId, IssueCode code, String referenceId) { }
}
