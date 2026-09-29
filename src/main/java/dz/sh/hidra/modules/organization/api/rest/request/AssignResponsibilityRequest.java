/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssignResponsibilityRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-29
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.api.rest.request
 *
 * @Description : Canonical API request for workflow-approved responsibility assignment.
 *
 */
package dz.sh.hidra.modules.organization.api.rest.request;

import dz.sh.hidra.modules.organization.domain.value.ResponsibilityAssigneeType;
import dz.sh.hidra.modules.organization.domain.value.ResponsibilityType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.Instant;

public record AssignResponsibilityRequest(
        @NotNull ResponsibilityType responsibilityType,
        @NotNull ResponsibilityAssigneeType assigneeType,
        @NotBlank String assigneeId,
        @NotNull @Positive Long scopeId,
        String description,
        Instant validFrom,
        Instant validTo,
        @NotBlank String workflowInstanceId,
        @NotBlank String operationReference
) { }
