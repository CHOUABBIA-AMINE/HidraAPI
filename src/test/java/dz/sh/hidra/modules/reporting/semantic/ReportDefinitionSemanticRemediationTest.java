/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportDefinitionSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Class
 * @Layer       : Reporting Test
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.semantic
 *
 * @Description : Verifies HMR-013 ReportDefinition semantic remediation.
 *
 */
package dz.sh.hidra.modules.reporting.semantic;

import dz.sh.hidra.modules.reporting.domain.exception.InvalidReportingValueException;
import dz.sh.hidra.modules.reporting.domain.model.ReportDefinition;
import dz.sh.hidra.modules.reporting.domain.value.ReportDefinitionStatus;
import dz.sh.hidra.modules.reporting.domain.value.ReportTemplateVersionStatus;
import dz.sh.hidra.modules.reporting.infrastructure.persistence.adapter.JpaReportDefinitionRepositoryAdapter;
import dz.sh.hidra.modules.reporting.infrastructure.persistence.entity.ReportCatalogEntryJpaEntity;
import dz.sh.hidra.modules.reporting.infrastructure.persistence.entity.ReportTemplateJpaEntity;
import dz.sh.hidra.modules.reporting.infrastructure.persistence.entity.ReportTemplateVersionJpaEntity;
import dz.sh.hidra.modules.reporting.infrastructure.persistence.repository.ReportAccessPolicyJpaRepository;
import dz.sh.hidra.modules.reporting.infrastructure.persistence.repository.ReportCatalogEntryJpaRepository;
import dz.sh.hidra.modules.reporting.infrastructure.persistence.repository.ReportDefinitionJpaRepository;
import dz.sh.hidra.modules.reporting.infrastructure.persistence.repository.ReportTemplateJpaRepository;
import dz.sh.hidra.modules.reporting.infrastructure.persistence.repository.ReportTemplateVersionJpaRepository;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ReportDefinitionSemanticRemediationTest {

    private static final Instant NOW = Instant.parse("2026-10-04T00:00:00Z");

    @Test
    void legacyCreationShapeNowCreatesDraftInsteadOfSkippingLifecycle() {
        ReportDefinition definition = new ReportDefinition(
                "definition-1",
                "DAILY_OPERATIONS",
                null,
                "Rapport quotidien",
                null,
                "category-1",
                "reporting",
                null,
                true,
                null,
                false,
                false,
                NOW,
                NOW
        );

        assertThat(definition.status()).isEqualTo(ReportDefinitionStatus.DRAFT);
        assertThat(definition.active()).isFalse();
        assertThat(definition.usableForNewRequests()).isFalse();
    }

    @Test
    void rejectsMissingFrenchNameUnknownOwnerModuleAndTimestamps() {
        assertThatThrownBy(() -> definition(" ", "reporting", ReportDefinitionStatus.DRAFT, null, NOW, NOW))
                .isInstanceOf(InvalidReportingValueException.class)
                .hasMessageContaining("French name");

        assertThatThrownBy(() -> definition("Rapport", "unknown-module", ReportDefinitionStatus.DRAFT, null, NOW, NOW))
                .isInstanceOf(InvalidReportingValueException.class)
                .hasMessageContaining("known Hidra business module");

        assertThatThrownBy(() -> definition("Rapport", "reporting", ReportDefinitionStatus.DRAFT, null, null, NOW))
                .isInstanceOf(InvalidReportingValueException.class)
                .hasMessageContaining("createdAt");
    }

    @Test
    void repositoryRejectsDuplicateCodeAndInvalidCategoryFamily() {
        var definitionRepository = mock(ReportDefinitionJpaRepository.class);
        var catalogRepository = mock(ReportCatalogEntryJpaRepository.class);
        var templateRepository = mock(ReportTemplateJpaRepository.class);
        var versionRepository = mock(ReportTemplateVersionJpaRepository.class);
        var accessPolicyRepository = mock(ReportAccessPolicyJpaRepository.class);
        var adapter = new JpaReportDefinitionRepositoryAdapter(
                definitionRepository,
                catalogRepository,
                templateRepository,
                versionRepository,
                accessPolicyRepository
        );
        var model = definition("Rapport", "reporting", ReportDefinitionStatus.DRAFT, null, NOW, NOW);

        when(definitionRepository.existsByCodeAndIdNot("DAILY_OPERATIONS", "definition-1")).thenReturn(true);
        assertThatThrownBy(() -> adapter.save(model))
                .isInstanceOf(InvalidReportingValueException.class)
                .hasMessageContaining("unique");
        verify(definitionRepository, never()).save(any());

        when(definitionRepository.existsByCodeAndIdNot("DAILY_OPERATIONS", "definition-1")).thenReturn(false);
        when(catalogRepository.findById("category-1")).thenReturn(Optional.of(
                new ReportCatalogEntryJpaEntity(
                        "category-1", "REPORT_FORMAT", "CATEGORY", true, 1, true, NOW, NOW
                )
        ));
        assertThatThrownBy(() -> adapter.save(model))
                .isInstanceOf(InvalidReportingValueException.class)
                .hasMessageContaining("ACTIVE REPORT_CATEGORY");
    }

    @Test
    void activeCurrentTemplateVersionMustBelongToDefinitionAndBeActive() {
        var definitionRepository = mock(ReportDefinitionJpaRepository.class);
        var catalogRepository = mock(ReportCatalogEntryJpaRepository.class);
        var templateRepository = mock(ReportTemplateJpaRepository.class);
        var versionRepository = mock(ReportTemplateVersionJpaRepository.class);
        var accessPolicyRepository = mock(ReportAccessPolicyJpaRepository.class);
        var adapter = new JpaReportDefinitionRepositoryAdapter(
                definitionRepository,
                catalogRepository,
                templateRepository,
                versionRepository,
                accessPolicyRepository
        );
        var model = definition(
                "Rapport",
                "reporting",
                ReportDefinitionStatus.ACTIVE,
                "version-1",
                NOW,
                NOW
        );

        when(catalogRepository.findById("category-1")).thenReturn(Optional.of(
                new ReportCatalogEntryJpaEntity(
                        "category-1", "REPORT_CATEGORY", "OPERATIONS", true, 1, true, NOW, NOW
                )
        ));
        when(versionRepository.findById("version-1")).thenReturn(Optional.of(
                new ReportTemplateVersionJpaEntity(
                        "version-1",
                        "template-1",
                        1,
                        ReportTemplateVersionStatus.ACTIVE,
                        "layout",
                        null,
                        "checksum",
                        null,
                        null,
                        NOW,
                        NOW,
                        null
                )
        ));
        when(templateRepository.findById("template-1")).thenReturn(Optional.of(
                new ReportTemplateJpaEntity(
                        "template-1",
                        "different-definition",
                        "TPL",
                        null,
                        "Modèle",
                        null,
                        "HTML",
                        true,
                        NOW,
                        NOW
                )
        ));

        assertThatThrownBy(() -> adapter.save(model))
                .isInstanceOf(InvalidReportingValueException.class)
                .hasMessageContaining("same report definition");
    }

    @Test
    void migrationPersistsLifecycleAndFailsClosedForRequestsRunsAndPolicies() throws Exception {
        String sql = Files.readString(Path.of(
                "src/main/resources/db/migration/V20261004_013__hmr_013_reporting_report_definition.sql"
        ));

        assertThat(sql).contains("ADD COLUMN status varchar(40)");
        assertThat(sql).contains("DROP COLUMN active");
        assertThat(sql).contains("uk_hmr013_reporting_report_definition_code");
        assertThat(sql).contains("catalog_name = 'REPORT_CATEGORY'");
        assertThat(sql).contains("'leakdetection'");
        assertThat(sql).contains("trg_hmr013_reporting_request_definition_gate");
        assertThat(sql).contains("Restricted report request requires an explicit matching Reporting access policy");
        assertThat(sql).contains("APPROVED request state and Workflow reference");
        assertThat(sql).contains("ReportRun template_version_id must be an ACTIVE version belonging to the selected definition");
    }

    private static ReportDefinition definition(
            String nameFr,
            String ownerModule,
            ReportDefinitionStatus status,
            String currentTemplateVersionId,
            Instant createdAt,
            Instant updatedAt
    ) {
        return new ReportDefinition(
                "definition-1",
                "DAILY_OPERATIONS",
                null,
                nameFr,
                null,
                "category-1",
                ownerModule,
                null,
                status,
                currentTemplateVersionId,
                false,
                false,
                createdAt,
                updatedAt
        );
    }
}
