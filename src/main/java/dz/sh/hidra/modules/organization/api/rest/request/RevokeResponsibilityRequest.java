/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RevokeResponsibilityRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-29
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.api.rest.request
 *
 * @Description : Canonical API request for workflow-approved responsibility revocation.
 *
 */
package dz.sh.hidra.modules.organization.api.rest.request;

import jakarta.validation.constraints.NotBlank;
import java.time.Instant;

public record RevokeResponsibilityRequest(
        Instant effectiveAt,
        @NotBlank String workflowInstanceId,
        @NotBlank String operationReference
) { }
