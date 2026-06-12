/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ApprovedPlanBaselineProjection
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Infrastructure
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.infrastructure.projection
 *
 * @Description : Approved plan baseline projection.
 *
 */
package dz.sh.hidra.modules.planning.infrastructure.projection;

import dz.sh.hidra.modules.planning.domain.value.OperationalPlanStatus;

import java.time.Instant;

/**
 * Approved plan baseline projection.
 */
public record ApprovedPlanBaselineProjection(
        String planId,
        String approvedRevisionId,
        String topologyScopeType,
        String topologyScopeId,
        OperationalPlanStatus status,
        Instant approvedAt
) {
}
