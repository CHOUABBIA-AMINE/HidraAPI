/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateIntegrityProgramRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.api.rest.request
 *
 * @Description : REST request to create integrity program.
 *
 */
package dz.sh.hidra.modules.integrity.api.rest.request;

import java.time.Instant;

/**
 * REST request to create integrity program.
 */
public record CreateIntegrityProgramRequest(
        String code,
        String nameAr,
        String nameFr,
        String nameEn,
        String description,
        String programTypeId,
        String ownerOrganizationUnitId,
        String ownerOrganizationUnitNameSnapshot,
        Instant plannedStartAt,
        Instant plannedEndAt,
        String createdByActorId
) {
}
