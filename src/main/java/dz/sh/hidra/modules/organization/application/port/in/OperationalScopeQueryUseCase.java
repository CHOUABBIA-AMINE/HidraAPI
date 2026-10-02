/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OperationalScopeQueryUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-29
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.port.in
 *
 * @Description : Read-only canonical operational-scope query with current owner-resolved display data.
 *
 */
package dz.sh.hidra.modules.organization.application.port.in;

import dz.sh.hidra.modules.organization.domain.value.OperationalScopeType;

public interface OperationalScopeQueryUseCase {

    ScopeView scope(Long scopeId);

    record ScopeView(
            Long id,
            OperationalScopeType type,
            String targetId,
            String code,
            String name,
            boolean assignable
    ) { }
}
