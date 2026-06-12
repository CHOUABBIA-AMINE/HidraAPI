/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateHseCapaRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.api.rest.request
 *
 * @Description : REST request to create HSE CAPA.
 *
 */
package dz.sh.hidra.modules.hse.api.rest.request;

import java.time.Instant;

/**
 * REST request to create HSE CAPA.
 */
public record CreateHseCapaRequest(
        String hseCaseId,
        String actionNumber,
        String actionTypeId,
        String title,
        String description,
        String ownerActorId,
        String ownerDisplayNameSnapshot,
        String ownerOrganizationUnitId,
        String ownerOrganizationUnitNameSnapshot,
        Instant targetDate,
        boolean verificationRequired,
        String linkedWorkOrderId,
        String workflowTaskId
) {
}
