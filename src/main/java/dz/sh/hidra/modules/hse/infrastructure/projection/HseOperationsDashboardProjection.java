/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HseOperationsDashboardProjection
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Infrastructure
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.infrastructure.projection
 *
 * @Description : HSE operations dashboard projection.
 *
 */
package dz.sh.hidra.modules.hse.infrastructure.projection;

import dz.sh.hidra.modules.hse.domain.value.HseCaseStatus;
import dz.sh.hidra.modules.hse.domain.value.HseImpactSeverity;

import java.time.Instant;

/**
 * HSE operations dashboard projection.
 */
public record HseOperationsDashboardProjection(
        String hseCaseId,
        String caseNumber,
        String title,
        String caseTypeId,
        HseImpactSeverity highestSeverity,
        HseCaseStatus status,
        String targetModule,
        String targetId,
        Instant reportedAt
) {
}
