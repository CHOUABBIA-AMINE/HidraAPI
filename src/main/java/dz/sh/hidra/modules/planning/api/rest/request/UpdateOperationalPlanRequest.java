/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : UpdateOperationalPlanRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-12
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.api.rest.request
 *
 * @Description : REST request for a concurrency-protected operational-plan metadata update.
 *
 */
package dz.sh.hidra.modules.planning.api.rest.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;

/**
 * Carries mutable plan metadata and the backend-issued optimistic-concurrency token.
 */
public record UpdateOperationalPlanRequest(
        @NotNull Instant expectedUpdatedAt,
        String nameAr,
        @NotBlank String nameFr,
        String nameEn,
        String responsibleOrganizationUnitId
) {
}
