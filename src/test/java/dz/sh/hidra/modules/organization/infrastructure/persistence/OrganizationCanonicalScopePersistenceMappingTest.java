/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationCanonicalScopePersistenceMappingTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-29
 *
 * @Type        : Class
 * @Layer       : Organization Test
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence
 *
 * @Description : Verifies greenfield persistence maps only canonical scope state after ORG-031 bridge retirement.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import dz.sh.hidra.modules.organization.domain.model.EmployeeAssignment;
import dz.sh.hidra.modules.organization.domain.model.OrganizationUnit;
import dz.sh.hidra.modules.organization.domain.model.ResponsibilityAssignment;
import dz.sh.hidra.modules.organization.domain.value.AssignmentStatus;
import dz.sh.hidra.modules.organization.domain.value.AssignmentType;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitStatus;
import dz.sh.hidra.modules.organization.domain.value.ResponsibilityAssigneeType;
import dz.sh.hidra.modules.organization.domain.value.ResponsibilityType;
import dz.sh.hidra.modules.organization.infrastructure.persistence.entity.EmployeeAssignmentJpaEntity;
import dz.sh.hidra.modules.organization.infrastructure.persistence.entity.OrganizationUnitJpaEntity;
import dz.sh.hidra.modules.organization.infrastructure.persistence.entity.ResponsibilityAssignmentJpaEntity;
import dz.sh.hidra.modules.organization.infrastructure.persistence.mapper.OrganizationPersistenceMapper;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.time.Instant;
import org.junit.jupiter.api.Test;

class OrganizationCanonicalScopePersistenceMappingTest {

    private static final Instant NOW = Instant.parse("2026-09-29T11:00:00Z");

    @Test
    void legacyScopeJpaCompatibilityFieldsAreRetired() {
        assertLegacyScopeFieldsAbsent(OrganizationUnitJpaEntity.class);
        assertLegacyScopeFieldsAbsent(EmployeeAssignmentJpaEntity.class);
        assertLegacyScopeFieldsAbsent(ResponsibilityAssignmentJpaEntity.class);
    }

    @Test
    void genericMapperNoLongerDependsOnUnitOrEmployeeLegacyScopeState() {
        OrganizationUnit unit = new OrganizationUnit(
                "unit-1",
                "UNIT-1",
                null,
                null,
                "Unit",
                "type-1",
                null,
                OrganizationUnitStatus.ACTIVE,
                NOW,
                null,
                NOW,
                NOW
        );
        OrganizationUnitJpaEntity unitEntity = OrganizationPersistenceMapper.toEntity(unit);

        assertThat(OrganizationPersistenceMapper.toDomain(unitEntity)).isEqualTo(unit);

        EmployeeAssignment employeeAssignment = new EmployeeAssignment(
                "employee-assignment-1",
                "employee-1",
                "unit-1",
                "position-1",
                AssignmentType.PRIMARY,
                NOW,
                null,
                AssignmentStatus.ACTIVE,
                NOW,
                NOW
        );
        EmployeeAssignmentJpaEntity employeeEntity =
                OrganizationPersistenceMapper.toEntity(employeeAssignment);

        assertThat(OrganizationPersistenceMapper.toDomain(employeeEntity))
                .isEqualTo(employeeAssignment);
    }

    @Test
    void genericResponsibilityMapperUsesCanonicalRegistryId() {
        ResponsibilityAssignment assignment = new ResponsibilityAssignment(
                "responsibility-1",
                ResponsibilityType.RESPONSIBLE,
                ResponsibilityAssigneeType.ORGANIZATION_UNIT,
                "unit-1",
                42L,
                "canonical",
                NOW,
                null,
                AssignmentStatus.ACTIVE,
                NOW,
                NOW
        );

        ResponsibilityAssignmentJpaEntity entity =
                OrganizationPersistenceMapper.toEntity(assignment);

        assertThat(entity.scopeId()).isEqualTo(42L);
        assertThat(OrganizationPersistenceMapper.toDomain(entity)).isEqualTo(assignment);
    }

    private static void assertLegacyScopeFieldsAbsent(Class<?> entityType) {
        assertThat(Arrays.stream(entityType.getDeclaredFields())
                .map(Field::getName)
                .toList())
                .as(entityType.getSimpleName() + " must not map retired legacy scope columns")
                .doesNotContain(
                        "operationalScopeType",
                        "operationalScopeId",
                        "operationalScopeCode",
                        "operationalScopeName"
                );
    }

}
