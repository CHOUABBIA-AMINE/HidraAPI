/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OperationalScopePersistenceMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-27
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence.mapper
 *
 * @Description : Maps operational-scope registry rows to canonical domain identities.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.persistence.mapper;

import dz.sh.hidra.modules.organization.domain.model.OperationalScope;
import dz.sh.hidra.modules.organization.domain.value.OperationalScopeType;
import dz.sh.hidra.modules.organization.infrastructure.persistence.entity.OperationalScopeJpaEntity;

/**
 * Persistence mapper for OperationalScope.
 */
public final class OperationalScopePersistenceMapper {

    private OperationalScopePersistenceMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static OperationalScopeJpaEntity newEntity(
            OperationalScopeType type,
            String targetId
    ) {
        return new OperationalScopeJpaEntity(null, type, targetId);
    }

    public static OperationalScope toDomain(OperationalScopeJpaEntity entity) {
        return new OperationalScope(
                entity.id(),
                entity.scopeType(),
                entity.targetId()
        );
    }
}
