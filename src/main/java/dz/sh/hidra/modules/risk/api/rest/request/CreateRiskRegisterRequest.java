/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateRiskRegisterRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.api.rest.request
 *
 * @Description : REST request to create risk register.
 *
 */
package dz.sh.hidra.modules.risk.api.rest.request;

import java.time.Instant;

/**
 * REST request to create risk register.
 */
public record CreateRiskRegisterRequest(
        String code,
        String nameAr,
        String nameFr,
        String nameEn,
        String description,
        String registerTypeId,
        String ownerOrganizationUnitId,
        String ownerOrganizationUnitNameSnapshot,
        String scopeType,
        String scopeId,
        String scopeCodeSnapshot,
        String scopeLabelSnapshot,
        String reviewFrequencyId,
        Instant effectiveFrom,
        Instant effectiveTo,
        String createdByActorId,
        String createdByDisplayNameSnapshot
) {
}
