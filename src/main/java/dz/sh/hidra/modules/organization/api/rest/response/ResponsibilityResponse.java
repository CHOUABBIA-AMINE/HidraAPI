/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ResponsibilityResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-29
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.api.rest.response
 *
 * @Description : Responsibility response enriched with current canonical scope display data.
 *
 */
package dz.sh.hidra.modules.organization.api.rest.response;

import dz.sh.hidra.modules.organization.domain.value.AssignmentStatus;
import dz.sh.hidra.modules.organization.domain.value.ResponsibilityAssigneeType;
import dz.sh.hidra.modules.organization.domain.value.ResponsibilityType;
import java.time.Instant;

public record ResponsibilityResponse(
        String id,
        ResponsibilityType responsibilityType,
        ResponsibilityAssigneeType assigneeType,
        String assigneeId,
        Long scopeId,
        OperationalScopeResponse scope,
        String description,
        Instant validFrom,
        Instant validTo,
        AssignmentStatus status,
        Instant createdAt,
        Instant updatedAt
) { }
