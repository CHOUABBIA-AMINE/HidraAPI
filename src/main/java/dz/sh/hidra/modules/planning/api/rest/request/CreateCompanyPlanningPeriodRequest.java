/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateCompanyPlanningPeriodRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-10
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.api.rest.request
 *
 * @Description : Versioned period creation with explicit company selection.
 *
 */
package dz.sh.hidra.modules.planning.api.rest.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.Instant;

public record CreateCompanyPlanningPeriodRequest(
        String code,
        String nameAr,
        String nameFr,
        String nameEn,
        String periodTypeId,
        Instant periodStart,
        Instant periodEnd,
        String timeZone,
        @NotBlank @Size(max = 80) String companyPartyId
) { }
