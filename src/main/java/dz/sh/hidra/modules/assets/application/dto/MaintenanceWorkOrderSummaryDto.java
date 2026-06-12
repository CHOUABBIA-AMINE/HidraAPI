/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MaintenanceWorkOrderSummaryDto
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.application.dto
 *
 * @Description : Maintenance work order summary DTO.
 *
 */
package dz.sh.hidra.modules.assets.application.dto;

import dz.sh.hidra.modules.assets.domain.value.MaintenanceWorkOrderStatus;

import java.time.Instant;

/**
 * Maintenance work order summary DTO.
 */
public record MaintenanceWorkOrderSummaryDto(
        String id,
        String workOrderNumber,
        String maintainableAssetId,
        String sourceRecommendationId,
        String workOrderTypeId,
        MaintenanceWorkOrderStatus status,
        String title,
        Instant plannedStartAt,
        Instant completedAt
) {
}
