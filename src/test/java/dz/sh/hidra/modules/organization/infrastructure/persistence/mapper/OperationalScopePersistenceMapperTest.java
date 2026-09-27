/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OperationalScopePersistenceMapperTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-27
 *
 * @Type        : Class
 * @Layer       : Organization Test
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence.mapper
 *
 * @Description : Tests persistence mapping for canonical operational-scope registry identities.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.persistence.mapper;

import dz.sh.hidra.modules.organization.domain.model.OperationalScope;
import dz.sh.hidra.modules.organization.domain.value.OperationalScopeType;
import dz.sh.hidra.modules.organization.infrastructure.persistence.entity.OperationalScopeJpaEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class OperationalScopePersistenceMapperTest {

    @Test
    void createsUnpersistedEntityWithoutInventingRegistryId() {
        OperationalScopeJpaEntity entity =
                OperationalScopePersistenceMapper.newEntity(
                        OperationalScopeType.PIPELINE,
                        "pipeline-009"
                );

        assertNull(entity.id());
        assertEquals(OperationalScopeType.PIPELINE, entity.scopeType());
        assertEquals("pipeline-009", entity.targetId());
    }

    @Test
    void mapsPersistedEntityToCanonicalDomainIdentity() {
        OperationalScopeJpaEntity entity =
                new OperationalScopeJpaEntity(
                        42L,
                        OperationalScopeType.FACILITY,
                        "facility-003"
                );

        OperationalScope scope = OperationalScopePersistenceMapper.toDomain(entity);

        assertEquals(42L, scope.id());
        assertEquals(OperationalScopeType.FACILITY, scope.type());
        assertEquals("facility-003", scope.targetId());
    }
}
