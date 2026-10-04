/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportingLineSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Class
 * @Layer       : Organization Test
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.semantic
 *
 * @Description : Verifies HMR-028 catalog, typed-subject and generalized matrix policy semantics.
 *
 */
package dz.sh.hidra.modules.organization.semantic;

import dz.sh.hidra.modules.organization.domain.exception.InvalidOrganizationValueException;
import dz.sh.hidra.modules.organization.domain.model.ReportingLine;
import dz.sh.hidra.modules.organization.domain.value.ReportingLineType;
import dz.sh.hidra.modules.organization.domain.value.ReportingSubjectReference;
import dz.sh.hidra.modules.organization.domain.value.ReportingSubjectType;
import dz.sh.hidra.modules.organization.infrastructure.persistence.adapter.JpaReportingLineRepositoryAdapter;
import dz.sh.hidra.modules.organization.infrastructure.persistence.entity.ReportingLineTypeJpaEntity;
import dz.sh.hidra.modules.organization.infrastructure.persistence.repository.ReportingLineJpaRepository;
import dz.sh.hidra.modules.organization.infrastructure.persistence.repository.ReportingLineTypeJpaRepository;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ReportingLineSemanticRemediationTest {

    private static final Instant NOW = Instant.parse("2026-10-04T00:00:00Z");

    @Test
    void reportingLineTypeIsCatalogReferenceWithAuthoritativeCompatibilityConstants() {
        assertThat(ReportingLineType.class.isEnum()).isFalse();

        Set<String> codes = Set.of(
                ReportingLineType.LINE.code(),
                ReportingLineType.OPERATIONAL.code(),
                ReportingLineType.FUNCTIONAL.code(),
                ReportingLineType.ADMINISTRATIVE.code(),
                ReportingLineType.TECHNICAL.code(),
                ReportingLineType.DOTTED_LINE.code()
        );

        assertThat(codes).containsExactlyInAnyOrder(
                "LINE",
                "OPERATIONAL",
                "FUNCTIONAL",
                "ADMINISTRATIVE",
                "TECHNICAL",
                "DOTTED_LINE"
        );
        assertThat(ReportingLineType.LINE.lineHierarchy()).isTrue();
        assertThat(ReportingLineType.FUNCTIONAL.explicitlyAllowsMultipleActiveLines()).isTrue();
        assertThat(ReportingLineType.OPERATIONAL.explicitlyAllowsMultipleActiveLines()).isFalse();
        assertThat(ReportingLineType.LINE.nameAr()).isNull();
        assertThat(ReportingLineType.LINE.nameFr()).isNull();
        assertThat(ReportingLineType.LINE.nameEn()).isNull();
    }

    @Test
    void adapterRejectsMissingOrInactiveEmployeeSubjects() {
        ReportingLineJpaRepository repository = mock(ReportingLineJpaRepository.class);
        ReportingLineTypeJpaRepository typeRepository =
                mock(ReportingLineTypeJpaRepository.class);
        JpaReportingLineRepositoryAdapter adapter =
                new JpaReportingLineRepositoryAdapter(repository, typeRepository);

        when(typeRepository.findById("FUNCTIONAL"))
                .thenReturn(Optional.of(typeEntity("FUNCTIONAL")));

        ReportingLine line = line(
                "line-1",
                ReportingLineType.FUNCTIONAL,
                ReportingSubjectType.EMPLOYEE,
                "employee-inactive",
                ReportingSubjectType.POSITION,
                "position-1"
        );

        when(repository.existsActiveEmployee("employee-inactive")).thenReturn(false);

        assertThatThrownBy(() -> adapter.save(line))
                .isInstanceOf(InvalidOrganizationValueException.class)
                .hasMessageContaining("must exist and be ACTIVE");
    }

    @Test
    void employeeSourceMayHaveOnlyOneActiveLineHierarchy() {
        ReportingLineJpaRepository repository = mock(ReportingLineJpaRepository.class);
        ReportingLineTypeJpaRepository typeRepository =
                mock(ReportingLineTypeJpaRepository.class);
        JpaReportingLineRepositoryAdapter adapter =
                new JpaReportingLineRepositoryAdapter(repository, typeRepository);

        when(typeRepository.findById("LINE"))
                .thenReturn(Optional.of(typeEntity("LINE")));
        when(repository.existsActiveEmployee("employee-1")).thenReturn(true);
        when(repository.existsPosition("position-1")).thenReturn(true);
        when(repository.existsOtherActiveEmployeeLine("line-1", "employee-1"))
                .thenReturn(true);

        assertThatThrownBy(() -> adapter.save(line(
                "line-1",
                ReportingLineType.LINE,
                ReportingSubjectType.EMPLOYEE,
                "employee-1",
                ReportingSubjectType.POSITION,
                "position-1"
        )))
                .isInstanceOf(InvalidOrganizationValueException.class)
                .hasMessageContaining("only one active LINE");
    }

    @Test
    void lineCyclePolicyUsesGeneralizedTypedSubjects() {
        ReportingLineJpaRepository repository = mock(ReportingLineJpaRepository.class);
        ReportingLineTypeJpaRepository typeRepository =
                mock(ReportingLineTypeJpaRepository.class);
        JpaReportingLineRepositoryAdapter adapter =
                new JpaReportingLineRepositoryAdapter(repository, typeRepository);

        when(typeRepository.findById("LINE"))
                .thenReturn(Optional.of(typeEntity("LINE")));
        when(repository.existsPosition("position-1")).thenReturn(true);
        when(repository.existsOrganizationUnit("unit-1")).thenReturn(true);
        when(repository.wouldCreateLineCycle(
                "line-2",
                "POSITION",
                "position-1",
                "ORGANIZATION_UNIT",
                "unit-1"
        )).thenReturn(true);

        assertThatThrownBy(() -> adapter.save(line(
                "line-2",
                ReportingLineType.LINE,
                ReportingSubjectType.POSITION,
                "position-1",
                ReportingSubjectType.ORGANIZATION_UNIT,
                "unit-1"
        )))
                .isInstanceOf(InvalidOrganizationValueException.class)
                .hasMessageContaining("reporting cycle");
    }

    @Test
    void migrationCreatesCatalogAndFailClosedMatrixGuards() throws Exception {
        String sql = Files.readString(Path.of(
                "src/main/resources/db/migration/"
                        + "V20261004_028__hmr_028_organization_reporting_line.sql"
        ));

        assertThat(sql).contains("CREATE TABLE hidra_org_reporting_line_type");
        assertThat(sql).contains("'LINE'", "'OPERATIONAL'", "'FUNCTIONAL'");
        assertThat(sql).contains("'ADMINISTRATIVE'", "'TECHNICAL'", "'DOTTED_LINE'");
        assertThat(sql).contains("legacy TEMPORARY");
        assertThat(sql).contains("reporting_line_type_id");
        assertThat(sql).contains("fk_hmr028_reporting_line_type");
        assertThat(sql).contains("uq_hmr028_employee_active_line");
        assertThat(sql).contains("status = 'ACTIVE'");
        assertThat(sql).contains("WITH RECURSIVE reachable");
        assertThat(sql).contains("trg_hmr028_guard_reporting_line");
        assertThat(sql).doesNotContain(
                "INSERT INTO hidra_org_reporting_line_type_translation"
        );
    }

    private static ReportingLine line(
            String id,
            ReportingLineType type,
            ReportingSubjectType sourceType,
            String sourceId,
            ReportingSubjectType targetType,
            String targetId
    ) {
        return new ReportingLine(
                id,
                type,
                new ReportingSubjectReference(sourceType, sourceId),
                new ReportingSubjectReference(targetType, targetId),
                NOW,
                null,
                true,
                NOW,
                NOW
        );
    }

    private static ReportingLineTypeJpaEntity typeEntity(String code) {
        return new ReportingLineTypeJpaEntity(
                code,
                code,
                null,
                null,
                null,
                true,
                NOW,
                NOW
        );
    }
}
