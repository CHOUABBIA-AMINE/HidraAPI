/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OperationalScopeResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-29
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.api.rest.response
 *
 * @Description : Operational-scope response with current owner-resolved display data.
 *
 */
package dz.sh.hidra.modules.organization.api.rest.response;

import dz.sh.hidra.modules.organization.domain.value.OperationalScopeType;

public record OperationalScopeResponse(
        Long id,
        OperationalScopeType type,
        String targetId,
        String code,
        String name,
        boolean assignable
) { }
