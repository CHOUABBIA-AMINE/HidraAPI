/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationUnitSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Class
 * @Layer       : Organization Test
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.semantic
 *
 * @Description : Verifies HMR-032 hierarchy, type-selection, and effective-start semantics.
 *
 */
package dz.sh.hidra.modules.organization.semantic;

import dz.sh.hidra.modules.organization.domain.exception.InvalidOrganizationValueException;
import dz.sh.hidra.modules.organization.domain.model.OrganizationUnit;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitStatus;
import dz.sh.hidra.modules.organization.infrastructure.persistence.adapter.JpaOrganizationUnitRepositoryAdapter;
import dz.sh.hidra.modules.organization.infrastructure.persistence.entity.OrganizationUnitJpaEntity;
import dz.sh.hidra.modules.organization.infrastructure.persistence.repository.OrganizationUnitJpaRepository;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OrganizationUnitSemanticRemediationTest {

    private static final Instant NOW = Instant.parse("2026-10-04T00:00:00Z");

    @Test
    void adapterRejectsInactiveTypeForNewUnit() {
        OrganizationUnitJpaRepository repository = mock(OrganizationUnitJpaRepository.class);
        JpaOrganizationUnitRepositoryAdapter adapter =
                new JpaOrganizationUnitRepositoryAdapter(repository);

        when(repository.findById("unit-1")).thenReturn(Optional.empty());
        when(repository.existsActiveUnitType("type-1")).thenReturn(false);

        assertThatThrownBy(() -> adapter.save(unit("type-1", null)))
                .isInstanceOf(InvalidOrganizationValueException.class)
                .hasMessageContaining("inactive");
    }

    @Test
    void existingHistoricalInactiveTypeMayRemainWhenClassificationIsUnchanged() {
        OrganizationUnitJpaRepository repository = mock(OrganizationUnitJpaRepository.class);
        JpaOrganizationUnitRepositoryAdapter adapter =
                new JpaOrganizationUnitRepositoryAdapter(repository);

        OrganizationUnitJpaEntity existing = new OrganizationUnitJpaEntity(
                "unit-1",
                "UNIT-1",
                null,
                null,
                null,
                "type-legacy",
                null,
                OrganizationUnitStatus.INACTIVE,
                NOW,
                null,
                NOW,
                NOW
        );

        when(repository.findById("unit-1")).thenReturn(Optional.of(existing));
        when(repository.save(any(OrganizationUnitJpaEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        adapter.save(unit("type-legacy", null));
    }

    @Test
    void adapterRejectsHierarchyCycle() {
        OrganizationUnitJpaRepository repository = mock(OrganizationUnitJpaRepository.class);
        JpaOrganizationUnitRepositoryAdapter adapter =
                new JpaOrganizationUnitRepositoryAdapter(repository);

        when(repository.findById("unit-1")).thenReturn(Optional.empty());
        when(repository.existsActiveUnitType("type-1")).thenReturn(true);
        when(repository.wouldCreateHierarchyCycle("unit-1", "unit-3"))
                .thenReturn(true);

        assertThatThrownBy(() -> adapter.save(unit("type-1", "unit-3")))
                .isInstanceOf(InvalidOrganizationValueException.class)
                .hasMessageContaining("must not contain cycles");
    }

    @Test
    void migrationMakesValidFromAndHierarchyPoliciesAuthoritative() throws Exception {
        String sql = Files.readString(Path.of(
                "src/main/resources/db/migration/"
                        + "V20261004_032__hmr_032_organization_organization_unit.sql"
        ));

        org.assertj.core.api.Assertions.assertThat(sql)
                .contains("ALTER COLUMN valid_from SET NOT NULL")
                .contains("unit_type.active = true")
                .contains("WITH RECURSIVE")
                .contains("trg_hmr032_guard_organization_unit");
    }

    private static OrganizationUnit unit(String unitTypeId, String parentUnitId) {
        return new OrganizationUnit(
                "unit-1",
                "UNIT-1",
                null,
                null,
                null,
                unitTypeId,
                parentUnitId,
                OrganizationUnitStatus.ACTIVE,
                NOW,
                null,
                NOW,
                NOW
        );
    }
}
